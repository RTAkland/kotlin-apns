# kotlin-apns

Apple Push Notification Service kotlin multiplatform sdk

Supported platforms:
- jvm11+
- mingw64
- linux64
- macox64
- macoxarm64
- ~~js browser & nodejs~~


```kotlin
fun main() {
    val apns = apns(
        topic,  // String
        teamId,  // String
        keyId,  // String
        p8Cert,  // ByteArray
        sandBox = false
    )
    apns.refreshToken()
    apns.push(deviceToken, PushType.Alert) {
        aps {
            alert {
                title = "Test"
            }
        }
    }
}
```