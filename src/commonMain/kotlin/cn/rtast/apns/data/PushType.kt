/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/19
 */


package cn.rtast.apns.data

import kotlinx.serialization.SerialName

public enum class PushType(public val typeName: String) {
    @SerialName("alert")
    Alert("alert"),

    @SerialName("background")
    Background("background")
}