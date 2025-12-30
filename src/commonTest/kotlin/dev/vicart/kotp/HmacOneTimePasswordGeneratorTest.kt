package dev.vicart.kotp

import dev.vicart.kotp.hotp.HmacOneTimePasswordConfig
import dev.vicart.kotp.hotp.HmacOneTimePasswordGenerator
import kotlin.io.encoding.Base64
import kotlin.test.Test
import kotlin.test.assertEquals

class HmacOneTimePasswordGeneratorTest {

    @Test
    fun testHotpGeneration() {
        val config = HmacOneTimePasswordConfig(
            secret = Base64.decode("4Sa2TgiRObcefAUxaumamRsyXtY=")
        )
        val generator = HmacOneTimePasswordGenerator(config)

        assertEquals("482298", generator.generateCode(0))
    }
}