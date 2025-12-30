package dev.vicart.kotp.hotp

import dev.vicart.kotp.HmacAlgorithm
import dev.vicart.kotp.hotp.HmacOneTimePasswordConfig
import kotlinx.io.Buffer
import kotlinx.io.readByteArray
import org.kotlincrypto.macs.hmac.sha1.HmacSHA1
import kotlin.experimental.and
import kotlin.math.pow

/**
 * Generator for Hmac-based One Time Password (OTP) code.
 * This follows the [RFC 4226](https://datatracker.ietf.org/doc/html/rfc4226)
 * @property config The configuration used to generate the OTP code
 * @constructor Creates an instance of HmacOneTimePasswordGenerator
 * @see HmacOneTimePasswordConfig
 */
class HmacOneTimePasswordGenerator(val config: HmacOneTimePasswordConfig) {

    /**
     * Generate a code with [HmacOneTimePasswordConfig.digits] long digits
     * @param counter The counter value
     * @return The OTP code
     */
    fun generateCode(counter: Long) : String {
        val hmacResult = hmacResult(counter)
        val sbits = dynamicTruncation(hmacResult)
        val code = sbits % (10.0.pow(config.digits).toInt())
        return code.toString().padStart(config.digits, '0')
    }

    private fun hmacResult(counter: Long) : ByteArray {
        val hmac = when(config.hmacAlgorithm) {
            HmacAlgorithm.SHA1 -> HmacSHA1(config.secret)
        }
        val counterBytes = Buffer().use {
            it.writeLong(counter)
            it.readByteArray()
        }
        return hmac.doFinal(counterBytes)
    }

    private fun dynamicTruncation(data: ByteArray) : Int {
        val offset = (data.last() and 0x0F).toInt()
        return Buffer().use { buff ->
            buff.writeByte(data[offset] and 0x7F)
            buff.writeByte(data[offset+1])
            buff.writeByte(data[offset+2])
            buff.writeByte(data[offset+3])
            buff.readInt()
        }
    }
}