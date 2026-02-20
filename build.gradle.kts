plugins {
    alias(libs.plugins.publishing)
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
}

allprojects {
    group = "cn.rtast.apns"
    version = "1.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.multiplatform")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    apply(plugin = "maven-publish")

    publishing {
        repositories {
            maven("https://repo.maven.rtast.cn/releases") {
                credentials {
                    username = "RTAkland"
                    password = System.getenv("PUBLISH_TOKEN")
                }
            }
        }
    }
}