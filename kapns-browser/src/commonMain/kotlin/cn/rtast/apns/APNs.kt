/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/20
 */


package cn.rtast.apns

import cn.rtast.apns.APNs.Companion.APNS_HOST_PRODUCTION
import cn.rtast.apns.APNs.Companion.APNS_HOST_SANDBOX
import cn.rtast.apns.PushType
import cn.rtast.apns.util.fetch
import kotlinx.coroutines.await
import kotlinx.serialization.json.Json
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit

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
public suspend fun APNs.push(
    deviceToken: String,
    pushType: PushType,
    payload: String,
): String {
    requireNotNull(jwtToken) { "jwt token is not initialized or expired, use .refreshToken() to initialize/refresh it first." }
    val host = if (sandBox) APNS_HOST_SANDBOX else APNS_HOST_PRODUCTION
    val headers = Headers().apply {
        append("authorization", "bearer $jwtToken")
        append("apns-topic", topic)
        append("apns-push-type", pushType.typeName)
        append("content-type", "application/json")
    }
    val init = RequestInit(
        method = "POST",
        headers = headers,
        body = payload,
    )
    return fetch("$host/3/device/$deviceToken", init).await().text().await()
}