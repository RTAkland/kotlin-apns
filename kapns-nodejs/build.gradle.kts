kotlin {
    withSourcesJar()
    explicitApi()

    js(IR) {
        nodejs()
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":kapns-common"))
        }
    }
}