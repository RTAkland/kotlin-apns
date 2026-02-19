/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/19
 */


package cn.rtast.apns.util

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

internal val httpClient: HttpClient = HttpClient(Js)

internal actual suspend fun post(
    url: String,
    payload: String,
    headers: Map<String, String>,
): String {
    val response = httpClient.post(url) {
        headers {
            headers.forEach { append(it.key, it.value) }
            append(HttpHeaders.ContentType, "application/json")
        }
        setBody(payload)
    }
    return response.bodyAsText()
}