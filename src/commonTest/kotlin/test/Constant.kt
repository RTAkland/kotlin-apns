/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/14
 */


package test

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.io.readString

const val topic = "me.fin.bark"
const val keyId = "LH4T9V5U4R"
const val teamId = "5U8LBRXG3A"
val deviceToken = SystemFileSystem.source(Path("src/commonTest/resources/deviceToken.txt")).buffered().readString()
val p8Cert = SystemFileSystem.source(Path("src/commonTest/resources/test.p8")).buffered().readByteArray()
