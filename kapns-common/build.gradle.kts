import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    withSourcesJar()
    explicitApi()

    macosArm64()
    macosX64()
    mingwX64()
    linuxX64()
    linuxArm64()
    jvm { compilerOptions.jvmTarget = JvmTarget.JVM_11 }
    js(IR) {
        browser()
        nodejs()
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.serialization.json)
            api(libs.jwt.kt)
            api(libs.jwt.ecdsa.kt)
        }
    }
}