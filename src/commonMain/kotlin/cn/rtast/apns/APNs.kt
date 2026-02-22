/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/14
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.apns

import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


/**
 * topic -> bundleID
 */
public class APNs internal constructor(
    private val topic: String,
    private val teamId: String,
    private val keyId: String,
    private val p8Cert: ByteArray,
    private val sandBox: Boolean = false,
) {
    private var jwtToken: String? = null

    /**
     * refresh signed jwt token
     */
    public suspend fun refreshToken(): String = signJwt(keyId, teamId, p8Cert).apply { jwtToken = this }

    /**
     * push message pass dsl builder
     */
    public suspend fun push(
        deviceToken: String,
        pushType: PushType,
        priority: PushPriority = PushPriority.Immediate,
        expiration: Long = Clock.System.now().epochSeconds + 86400L,
        pushId: Uuid? = null,
        collapseId: String? = null,
        payload: APSPayloadBuilder.() -> Unit,
    ): String = this.push(
        deviceToken = deviceToken,
        pushType = pushType,
        priority = priority,
        expiration = expiration,
        pushId = pushId,
        collapseId = collapseId,
        payload = APSPayloadBuilder().apply(payload).build()
    )


    /**
     * push message pass apns payload object
     */
    public suspend fun push(
        deviceToken: String,
        pushType: PushType,
        priority: PushPriority = PushPriority.Immediate,
        expiration: Long = Clock.System.now().epochSeconds + 86400L,
        pushId: Uuid? = null,
        collapseId: String? = null,
        payload: ApnsPayload,
    ): String = this.push(
        deviceToken, pushType, payload.toJson(),
        priority, expiration, pushId, collapseId
    )

    /**
     * push message pass raw JSON payload
     */
    public suspend fun push(
        deviceToken: String,
        pushType: PushType,
        payload: String,
        priority: PushPriority = PushPriority.Immediate,
        expiration: Long = Clock.System.now().epochSeconds + 86400L,
        pushId: Uuid? = null,
        collapseId: String? = null,
    ): String {
        requireNotNull(jwtToken) { "jwt token is not initialized, use .refreshToken() to initialize/refresh it" }
        val host = if (sandBox) APNS_HOST_SANDBOX else APNS_HOST_PRODUCTION
        return post(
            "$host/3/device/$deviceToken", payload,
            mutableMapOf(
                "authorization" to "bearer $jwtToken",
                "apns-topic" to topic,
                "apns-push-type" to pushType.typeName,
                "apns-expiration" to expiration.toString(),
                "apns-priority" to priority.priorityValue.toString()
            ).apply {
                pushId?.let { put("apns-id", it.toString()) }
                collapseId?.let { put("apns-collapse-id", it) }
            },
        )
    }

    public companion object {
        internal const val APNS_HOST_SANDBOX = "https://api.sandbox.push.apple.com"
        internal const val APNS_HOST_PRODUCTION = "https://api.push.apple.com"
    }
}

public fun apns(
    topic: String,
    teamId: String,
    keyId: String,
    p8Cert: ByteArray,
    sandBox: Boolean,
): APNs = APNs(topic, teamId, keyId, p8Cert, sandBox)