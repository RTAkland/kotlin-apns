import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.publishing)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinx.serialization)
}

group = "cn.rtast.apns"
version = "1.2"

repositories {
    mavenCentral()
}

kotlin {
    withSourcesJar()
    explicitApi()

    macosArm64()
    macosX64()
    mingwX64()
    linuxX64()
    linuxArm64()
    jvm { compilerOptions.jvmTarget = JvmTarget.JVM_11 }
    js(IR) { nodejs() }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation("dev.whyoleg.cryptography:cryptography-core:0.5.0")
        }

        jvmMain.dependencies {
            implementation("dev.whyoleg.cryptography:cryptography-provider-jdk:0.5.0")
        }

        jsMain.dependencies {
            implementation("dev.whyoleg.cryptography:cryptography-provider-webcrypto:0.5.0")
        }

        nativeMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.curl)
            implementation("dev.whyoleg.cryptography:cryptography-provider-optimal:0.5.0")
        }

        commonTest.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.8.2")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
            implementation(kotlin("test"))
        }
    }
}

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