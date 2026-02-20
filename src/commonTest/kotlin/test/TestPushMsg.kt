/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/14
 */


package test

import cn.rtast.apns.apns
import cn.rtast.apns.PushType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestPushMsg {

    @Test
    fun `test push message to iphone`() = runTest {
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