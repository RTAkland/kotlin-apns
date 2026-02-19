/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/19
 */


package test.js

import cn.rtast.apns.apns
import cn.rtast.apns.data.PushType
import kotlinx.coroutines.test.runTest
import test.deviceToken
import test.keyId
import test.p8Cert
import test.teamId
import test.topic
import kotlin.test.Test

class TestPushMessage {

    @Test
    fun `test push message on js platform`() = runTest {
        apns(topic, teamId, keyId, p8Cert, sandBox = false).push(deviceToken, PushType.Alert) {
            aps {
                alert {
                    title = "Test"
                }
            }
        }
    }
}