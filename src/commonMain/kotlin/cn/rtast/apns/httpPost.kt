/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/19
 */


package cn.rtast.apns

internal expect suspend fun post(url: String, payload: String, headers: Map<String, String>): String