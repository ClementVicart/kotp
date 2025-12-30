package dev.vicart.kotp.totp

import dev.vicart.kotp.hotp.HmacOneTimePasswordGenerator
import kotlin.time.Clock
import kotlin.time.Instant

/**
 * Generator for Time-based One-Time Password (TOTP) code
 * This follows the [RFC 6238](https://datatracker.ietf.org/doc/html/rfc6238)
 * @param config The configuration used to generate the OTP code
 * @constructor Creates an instance of TimeOneTimePasswordGenerator
 * @see TimeOneTimePasswordConfig
 */
class TimeOneTimePasswordGenerator(private val config: TimeOneTimePasswordConfig) : HmacOneTimePasswordGenerator(config) {

    /**
     * Generates an OTP code based on the current time
     * @return an OTP code of [TimeOneTimePasswordConfig.digits] long digits
     */
    fun generateCode() : String {
        return generateCode(Clock.System.now())
    }

    /**
     * Generates an OTP code based on the provided time
     * @param time The time for which to generate a code
     * @return an OTP code of [TimeOneTimePasswordConfig.digits] long digits
     */
    fun generateCode(time: Instant) : String {
        val durationMilli = config.timeStep.inWholeMilliseconds
        val currentTimeMilli = time.toEpochMilliseconds()
        val startTimeMilli = config.startTime.toEpochMilliseconds()

        val counter = (currentTimeMilli - startTimeMilli) / durationMilli

        return generateCode(counter)
    }
}