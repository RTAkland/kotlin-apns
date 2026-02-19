# kotlin-apns

Apple Push Notification Service kotlin multiplatform sdk

Supported platforms:
- jvm11+
- mingw64
- linux64
- macos64
- macosarm64
- ~~js browser & nodejs~~


```kotlin

repositories {
    mavenCentral()
    maven("https://repo.maven.rtast.cn/releases")
}

dependencies {
    implementation("cn.rtast.apns:kotlin-apns:1.0")
}
```

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