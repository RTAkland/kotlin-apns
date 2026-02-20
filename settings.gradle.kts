rootProject.name = "kotlin-apns"

listOf(
//    ":kapns-common",
    ":kapns-core",
//    ":kapns-nodejs",
//    ":kapns-browser",
).map { include(it) }