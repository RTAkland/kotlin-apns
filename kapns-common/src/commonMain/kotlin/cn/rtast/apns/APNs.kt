/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/14
 */


package cn.rtast.apns


/**
 * topic -> bundleID
 */
public class APNs(
    public val topic: String,
    public val teamId: String,
    public val keyId: String,
    public val p8Cert: ByteArray,
    public val sandBox: Boolean = false,
) {
    public var jwtToken: String? = null

    /**
     * refresh signed jwt token
     */
    public suspend fun refreshToken(): String = signJwt(keyId, teamId, p8Cert).apply { jwtToken = this }

    public companion object {
        public const val APNS_HOST_SANDBOX: String = "https://api.sandbox.push.apple.com"
        public const val APNS_HOST_PRODUCTION: String = "https://api.push.apple.com"
    }
}

public fun apns(
    topic: String,
    teamId: String,
    keyId: String,
    p8Cert: ByteArray,
    sandBox: Boolean,
): APNs = APNs(topic, teamId, keyId, p8Cert, sandBox)