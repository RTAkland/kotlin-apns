/*
 * Copyright © 2026 RTAkland
 * Author: RTAkland
 * Date: 2026/2/20
 */

package cn.rtast.apns

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.providers.webcrypto.WebCrypto

internal actual val cryptoProvider: CryptographyProvider = CryptographyProvider.WebCrypto