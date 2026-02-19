import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.publishing)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinx.serialization)
}

group = "cn.rtast.apns"
version = "1.0"

repositories {
    mavenCentral()
}

kotlin {
    macosArm64()
    macosX64()
    mingwX64()
    linuxX64()
    linuxArm64()
    jvm { compilerOptions.jvmTarget = JvmTarget.JVM_11 }
//    js(IR) {
//        browser {
//            testTask {
//                useKarma {
//                    useChromeHeadless()
//                }
//            }
//        }
//        nodejs()
//    }

    explicitApi()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.jwt.kt)
            implementation(libs.jwt.ecdsa.kt)
        }

        jvmMain.dependencies {
        }

        nativeMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.curl)
        }

//        jsMain.dependencies {
//            implementation(libs.ktor.client.core)
//            implementation(libs.ktor.client.js)
//        }

        linuxMain.dependencies {
        }

        mingwMain.dependencies {
        }

        appleMain.dependencies {
        }

        commonTest.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.8.2")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
            implementation(kotlin("test"))
        }
    }
}