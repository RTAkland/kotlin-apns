/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/20
 */


package cn.rtast.apns.util

internal expect suspend fun post(url: String, payload: String, headers: Map<String, String>): String