/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/14
 */


package test

import cn.rtast.apns.APNs
import cn.rtast.apns.apns
import cn.rtast.apns.PushType
import cn.rtast.apns.push
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestPushMsg {

    @Test
    fun `test push message to iphone`() = runTest {
        val apns = APNs(topic, teamId, keyId, p8Cert, sandBox = false)
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