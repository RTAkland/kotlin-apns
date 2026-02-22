/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/22
 */


package cn.rtast.apns

public enum class PushPriority(public val priorityValue: Int) {
    Immediate(10), Background(5)
}