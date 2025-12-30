package dev.vicart.kotp.totp

import dev.vicart.kotp.HmacAlgorithm
import dev.vicart.kotp.hotp.HmacOneTimePasswordConfig
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.Instant
import kotlin.time.toDuration

/**
 * Configuration class for generating a Time-based OTP code
 * @property secret The encoded secret
 * @property timeStep The time step between each OTP code
 * @property startTime The start time for the OTP code generation
 * @property digits The number of digits for the final code
 * @property hmacAlgorithm The HMAC algorithm to use
 * @constructor Creates an instance of HmacOneTimePasswordConfig
 */
class TimeOneTimePasswordConfig(
    override var secret: ByteArray,
    var timeStep: Duration = 30.seconds,
    var startTime: Instant = Instant.fromEpochSeconds(0),
    override var digits: Int = 6,
    override var hmacAlgorithm: HmacAlgorithm = HmacAlgorithm.SHA1
) : HmacOneTimePasswordConfig(
    secret = secret,
    digits = digits,
    hmacAlgorithm = hmacAlgorithm
) {

    /**
     * Creates an instance of HmacOneTimePasswordConfig
     * @param secret The encoded secret
     * @param timeStep The time step between each OTP code in [timeStepUnit] unit
     * @param timeStepUnit The time unit for [timeStep]
     * @param startTime The start time for the OTP code generation
     * @param digits The number of digits for the final code
     * @param hmacAlgorithm The HMAC algorithm to use
     */
    constructor(
        secret: ByteArray,
        timeStep: Long = 30,
        timeStepUnit: DurationUnit = DurationUnit.SECONDS,
        startTime: Instant = Instant.fromEpochSeconds(0),
        digits: Int = 6,
        hmacAlgorithm: HmacAlgorithm = HmacAlgorithm.SHA1
    ) : this(secret, timeStep.toDuration(timeStepUnit), startTime, digits, hmacAlgorithm)
}