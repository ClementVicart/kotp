package dev.vicart.kotp

import org.kotlincrypto.core.mac.Mac
import org.kotlincrypto.macs.hmac.sha1.HmacSHA1

/**
 * HMAC algorithms usable for OTP code generation
 */
enum class HmacAlgorithm(val instanciate: (ByteArray) -> Mac) {
    SHA1({ HmacSHA1(it) })
}