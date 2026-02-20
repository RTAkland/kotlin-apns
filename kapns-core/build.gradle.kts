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

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
//            api(project(":kapns-common"))
        }

        nativeMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.curl)
        }

        commonTest.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.8.2")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
            implementation(kotlin("test"))
        }
    }
}