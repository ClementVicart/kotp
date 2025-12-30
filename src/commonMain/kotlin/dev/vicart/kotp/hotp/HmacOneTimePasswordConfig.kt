package dev.vicart.kotp.hotp

import dev.vicart.kotp.HmacAlgorithm

/**
 * Configuration class for generating a Hmac-based OTP code
 * @property secret The encoded secret
 * @property digits The number of digits for the final code
 * @property hmacAlgorithm The HMAC algorithm to use
 * @constructor Creates an instance of HmacOneTimePasswordConfig
 */
data class HmacOneTimePasswordConfig(
    val secret: ByteArray,
    val digits: Int = 6,
    val hmacAlgorithm: HmacAlgorithm = HmacAlgorithm.SHA1
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as HmacOneTimePasswordConfig

        if (digits != other.digits) return false
        if (!secret.contentEquals(other.secret)) return false
        if (hmacAlgorithm != other.hmacAlgorithm) return false

        return true
    }

    override fun hashCode(): Int {
        var result = digits
        result = 31 * result + secret.contentHashCode()
        result = 31 * result + hmacAlgorithm.hashCode()
        return result
    }
}