/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/20
 */


package cn.rtast.apns

import cn.rtast.apns.APNs.Companion.APNS_HOST_PRODUCTION
import cn.rtast.apns.APNs.Companion.APNS_HOST_SANDBOX
import cn.rtast.apns.util.post
import kotlinx.serialization.json.Json

/**
 * push message passing dsl builder
 */
public suspend fun APNs.push(deviceToken: String, pushType: PushType, payload: APSPayloadBuilder.() -> Unit): String =
    this.push(deviceToken, pushType, APSPayloadBuilder().apply(payload).build())

/**
 * push message passing apns payload object
 */
public suspend fun APNs.push(deviceToken: String, pushType: PushType, payload: ApnsPayload): String =
    this.push(deviceToken, pushType, Json.encodeToString(payload))

/**
 * push message passing raw JSON payload
 */
public suspend fun APNs.push(deviceToken: String, pushType: PushType, payload: String): String {
    requireNotNull(jwtToken) { "jwt token is not initialized or expired, use .refreshToken() to initialize/refresh it first." }
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