/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/14
 */


package cn.rtast.apns


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
    public suspend fun push(deviceToken: String, pushType: PushType, payload: APSPayloadBuilder.() -> Unit): String =
        this.push(deviceToken, pushType, APSPayloadBuilder().apply(payload).build())

    /**
     * push message pass apns payload object
     */
    public suspend fun push(deviceToken: String, pushType: PushType, payload: ApnsPayload): String =
        this.push(deviceToken, pushType, payload.toJson())

    /**
     * push message pass raw JSON payload
     */
    public suspend fun push(deviceToken: String, pushType: PushType, payload: String): String {
        requireNotNull(jwtToken) { "jwt token is not initialized, use .refreshToken() to initialize/refresh it" }
        val host = if (sandBox) APNS_HOST_SANDBOX else APNS_HOST_PRODUCTION
        return post(
            "$host/3/device/$deviceToken", payload,
            mapOf(
                "authorization" to "bearer $jwtToken",
                "apns-topic" to topic,
                "apns-push-type" to pushType.typeName
            ),
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