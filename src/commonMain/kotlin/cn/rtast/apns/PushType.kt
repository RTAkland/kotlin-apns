package cn.rtast.apns

import kotlinx.serialization.SerialName

public enum class PushType(public val typeName: String) {
    @SerialName("alert")
    Alert("alert"),

    @SerialName("background")
    Background("background")
}