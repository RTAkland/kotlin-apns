/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/14
 */


package cn.rtast.apns.util

import kotlinx.serialization.json.Json

internal val json: Json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
    classDiscriminator = "_json_type_"
    encodeDefaults = true
    coerceInputValues = true
    decodeEnumsCaseInsensitive = true
    isLenient = true
}

internal inline fun <reified T> T.toJson(): String {
    return json.encodeToString(this)
}

internal inline fun <reified T> String.fromJson(): T {
    return json.decodeFromString<T>(this)
}