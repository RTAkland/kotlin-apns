/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/14
 */


package cn.rtast.apns

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.EC
import dev.whyoleg.cryptography.algorithms.ECDSA
import dev.whyoleg.cryptography.algorithms.SHA256
import kotlin.io.encoding.Base64
import kotlin.time.Clock

internal expect val cryptoProvider: CryptographyProvider

internal val ecdsa = cryptoProvider.get(ECDSA)

internal fun ByteArray.base64UrlEncode(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
    val sb = StringBuilder()
    var i = 0
    while (i < this.size) {
        val b0 = this[i].toInt() and 0xFF
        val b1 = if (i + 1 < this.size) this[i + 1].toInt() and 0xFF else 0
        val b2 = if (i + 2 < this.size) this[i + 2].toInt() and 0xFF else 0
        sb.append(chars[b0 shr 2])
        sb.append(chars[((b0 and 0x03) shl 4) or (b1 shr 4)])
        sb.append(if (i + 1 < this.size) chars[((b1 and 0x0F) shl 2) or (b2 shr 6)] else "")
        sb.append(if (i + 2 < this.size) chars[b2 and 0x3F] else "")
        i += 3
    }
    return sb.toString()
}

internal fun ByteArray.derToConcatenatedRS(): ByteArray {
    require(this[0].toInt() == 0x30) { "Not a DER sequence" }
    val rLen = this[3].toInt()
    val r = this.copyOfRange(4 + if (this[4] == 0.toByte()) 1 else 0, 4 + rLen)
    val sLen = this[4 + rLen + 1].toInt()
    val s = this.copyOfRange(4 + rLen + 2 + if (this[4 + rLen + 2] == 0.toByte()) 1 else 0, this.size)
    return r.padStart(32) + s.padStart(32)
}

internal fun ByteArray.padStart(length: Int): ByteArray =
    if (this.size < length) ByteArray(length - this.size) + this else this

internal suspend fun signJwt(
    keyId: String,
    teamId: String,
    p8Cert: ByteArray,
): String {
    val header = mapOf("alg" to "ES256", "kid" to keyId).toJson()
    val payload = mapOf("iss" to teamId, "iat" to Clock.System.now().epochSeconds.toString()).toJson()
    val headerB64 = header.encodeToByteArray().base64UrlEncode()
    val payloadB64 = payload.encodeToByteArray().base64UrlEncode()
    val unsigned = "$headerB64.$payloadB64".encodeToByteArray()
    val privateKey = ecdsa.privateKeyDecoder(EC.Curve.P256)
        .decodeFromByteArray(EC.PrivateKey.Format.PEM, p8Cert)
    val signature = privateKey.signatureGenerator(SHA256, ECDSA.SignatureFormat.DER)
        .generateSignature(unsigned)
        .derToConcatenatedRS()
        .base64UrlEncode()
    return "$headerB64.$payloadB64.$signature"
}