/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/14
 */


package cn.rtast.apns

import com.appstractive.jwt.*
import com.appstractive.jwt.signatures.es256
import kotlinx.serialization.json.JsonPrimitive
import kotlin.time.Clock


internal suspend fun signJwt(
    keyId: String,
    teamId: String,
    p8Cert: ByteArray,
): String = UnsignedJWT(
    Header(Algorithm.ES256, "JWT", keyId),
    Claims(buildMap {
        put("iss", JsonPrimitive(teamId))
        put("iat", JsonPrimitive(Clock.System.now().epochSeconds))
    })
).sign(keyId) { es256 { pem(p8Cert) } }.toString()
