/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/20
 */


package cn.rtast.apns.util

import kotlinx.coroutines.await
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.fetch.Request
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.js.Promise

internal external fun fetch(
    input: String,
    init: RequestInit = definedExternally,
): Promise<Response>

internal suspend fun Request.rawBody(): ByteArray =
    this.arrayBuffer().await().toByteArray()


internal fun ArrayBuffer.toByteArray(): ByteArray =
    Int8Array(this).unsafeCast<ByteArray>()

internal fun ByteArray.toArrayBuffer(): ArrayBuffer =
    Int8Array(this.toTypedArray()).unsafeCast<ArrayBuffer>()