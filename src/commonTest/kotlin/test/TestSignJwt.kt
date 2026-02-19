/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/14
 */


package test

import cn.rtast.apns.util.signJwt
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestSignJwt {

    @Test
    fun `test sign jwt`() = runTest {
        val signed = signJwt(keyId, teamId, p8Cert)
        println(signed)
    }
}