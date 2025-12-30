# kotp - Kotlin OTP code generator

This project provides a Kotlin Multiplatform library for generating OTP codes.

It is an implementation
of the [RFC 4226](https://datatracker.ietf.org/doc/html/rfc4226) and the [RFC 6238](https://datatracker.ietf.org/doc/html/rfc6238)
and provides:

* HMAC-Based One-Time Password (HOTP)
* Time-Based One-Time Password (TOTP)

The multiplatform library currently supports:

* Linux (x64, ARM64)
* Window (x64 only)
* MacOS (Intel and Apple Silicon)
* Java 8+
* WasmJs (browser, nodejs, d8)
* Javascript (browser, nodejs)

Planned support:
* Android

## Table of Contents

* [Dependency](#dependency)
  * [Gradle](#gradle)
  * [Maven](#maven)
* [HOTP vs TOTP](#hotp-vs-totp)
  * [HOTP](#hotp)
  * [TOTP](#totp)
* [Usage](#usage)
  * [HMAC-Based One-Time Password generation](#hmac-based-one-time-password-generation)

### Dependency

This library is available on Maven Central:

#### Gradle

For multiplatform project:
```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("dev.vicart.kotp:kotp:0.0.1")
        }
    }
}
```

For other platforms:
```kotlin
dependencies {
    implementation("dev.vicart.kotp:kotp:0.0.1")
}
```

#### Maven
`pom.xml`
```xml
<dependency>
    <groupId>dev.vicart.kotp</groupId>
    <artifactId>kotp</artifactId>
    <version>0.0.1</version>
</dependency>
```

### HOTP vs TOTP

HMAC-Based One-Time Password (HOTP) and Time-Based One-Time Password (TOTP) are two methods of generating one-time passwords.
Their goal is to provide a 2FA solution for securing access to sensitive data. Both of them are based on a **secret key** and a **counter**
and use a [HMAC function](https://datatracker.ietf.org/doc/html/rfc2104) to generate a unique password.
The **secret key** is usually a Base32-encoded string provided by the server.

#### HOTP

HOTP uses a **64-bit number** as a value for the counter. This counter is aimed to be incremented after each use and must be
synchronized between the client and the server.

#### TOTP

TOTP extends HOTP by using **the time** as a shared counter between the client and the server.

It can be configured for a code to be valid for a specific time interval (usually 30 seconds). However, a server may
check the provided code against the previous and the next time interval for error correction.

### Usage

#### HMAC-Based One-Time Password generation

```kotlin
val configuration = HmacOneTimePasswordConfiguration(
    secret = <encoded secret>,
    digits = 6, //The number of digits in the code
    hmacAlgorithm = HmacAlgorithm.SHA1 //The HMAC algorithm to use
)

val generator = HmacOneTimePasswordGenerator(configuration)

val counter = 0
val code = generator.generateCode(counter) //Returns "123456" for example
```

#### Time-Based One-Time Password generation

```kotlin
```