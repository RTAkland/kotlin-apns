/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/19
 */

package cn.rtast.apns.util

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration


internal actual suspend fun post(
    url: String,
    payload: String,
    headers: Map<String, String>,
): String {
    val client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(10))
        .build()
    val requestBuilder = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(Duration.ofSeconds(10))
        .POST(HttpRequest.BodyPublishers.ofString(payload))
        .header("Content-Type", "application/json")
    headers.forEach { (key, value) -> requestBuilder.header(key, value) }
    val request = requestBuilder.build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    if (response.statusCode() !in 200..299) throw RuntimeException("HTTP ${response.statusCode()}: ${response.body()}")
    return response.body()
}