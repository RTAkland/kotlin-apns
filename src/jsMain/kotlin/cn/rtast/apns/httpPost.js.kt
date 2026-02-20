/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/20
 */

package cn.rtast.apns

import kotlinx.coroutines.await
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.js.Promise

internal external fun fetch(
    input: String,
    init: RequestInit = definedExternally,
): Promise<Response>

internal actual suspend fun post(
    url: String,
    payload: String,
    headers: Map<String, String>,
): String {
    val headers = Headers().apply {
        append("content-type", "application/json")
        headers.forEach { append(it.key, it.value) }
    }
    val init = RequestInit("POST", headers, payload)
    return fetch(url, init).await().text().await()
}