/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/19
 */


package cn.rtast.apns

import kotlinx.serialization.Serializable

@DslMarker
internal annotation class ApnsDsl

@Serializable
public data class ApnsPayload(
    val aps: Aps,
    val customData: Map<String, String> = emptyMap(),
)

@Serializable
public data class Aps(
    val alert: Alert? = null,
    val badge: Int? = null,
    val sound: String? = null,
    val contentAvailable: Int? = null,
    val mutableContent: Int? = null,
    val category: String? = null,
    val threadId: String? = null,
)

@Serializable
public data class Alert(
    val title: String? = null,
    val subtitle: String? = null,
    val body: String? = null,
)

@ApnsDsl
public class AlertBuilder {
    public var title: String? = null
    public var subtitle: String? = null
    public var body: String? = null

    public fun build(): Alert = Alert(title, subtitle, body)
}

@ApnsDsl
public class ApsBuilder {
    public var badge: Int? = null
    public var sound: String? = null
    public var contentAvailable: Boolean = false
    public var mutableContent: Boolean = false
    public var category: String? = null
    public var threadId: String? = null
    public var alert: Alert? = null

    public fun alert(block: AlertBuilder.() -> Unit) {
        alert = AlertBuilder().apply(block).build()
    }

    public fun build(): Aps = Aps(
        alert = alert,
        badge = badge,
        sound = sound,
        contentAvailable = if (contentAvailable) 1 else null,
        mutableContent = if (mutableContent) 1 else null,
        category = category,
        threadId = threadId
    )
}

@ApnsDsl
public class APSPayloadBuilder {
    private val customData = mutableMapOf<String, String>()
    private var aps: Aps? = null

    public fun aps(block: ApsBuilder.() -> Unit) {
        aps = ApsBuilder().apply(block).build()
    }

    public fun custom(key: String, value: String) {
        customData[key] = value
    }

    public fun build(): ApnsPayload {
        requireNotNull(aps) { "aps block is required" }
        return ApnsPayload(aps!!, customData)
    }
}

public fun apnsPayload(block: APSPayloadBuilder.() -> Unit): ApnsPayload {
    return APSPayloadBuilder().apply(block).build()
}
