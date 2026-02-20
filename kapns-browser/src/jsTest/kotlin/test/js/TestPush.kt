/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/20
 */


package test.js

import cn.rtast.apns.apns
import cn.rtast.apns.PushType
import cn.rtast.apns.push
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestPush {

    @Test
    fun `test push`() = runTest {
        val apns = apns(topic, teamId, keyId, p8Cert, sandBox = false)
        apns.refreshToken()
        apns.push(deviceToken, PushType.Alert) {
            aps {
                alert {
                    title = "Test"
                }
            }
        }
    }
}