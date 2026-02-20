kotlin {
    withSourcesJar()
    explicitApi()

    js(IR) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":kapns-common"))
        }

        commonTest.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.8.2")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
            implementation(kotlin("test"))
        }
    }
}