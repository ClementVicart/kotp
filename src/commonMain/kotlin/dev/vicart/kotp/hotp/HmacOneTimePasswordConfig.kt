package dev.vicart.kotp.hotp

import dev.vicart.kotp.HmacAlgorithm

/**
 * Configuration class for generating a Hmac-based OTP code
 * @property secret The encoded secret
 * @property digits The number of digits for the final code
 * @property hmacAlgorithm The HMAC algorithm to use
 * @constructor Creates an instance of HmacOneTimePasswordConfig
 */
open class HmacOneTimePasswordConfig(
    open var secret: ByteArray,
    open var digits: Int = 6,
    open var hmacAlgorithm: HmacAlgorithm = HmacAlgorithm.SHA1
)