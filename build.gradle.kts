import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.maven.publish)
}

group = "dev.vicart.kotp"
version = "0.0.1-SNAPSHOT"

kotlin {
    val host = System.getProperty("os.name").lowercase()
    val arch = System.getProperty("os.arch")

    val nativeTarget = when {
        host.startsWith("mac") -> when(arch) {
            "aarch64" -> macosArm64()
            "amd64" -> macosX64()
            else -> throw GradleException("Unsupported architecture: $arch")
        }
        host.startsWith("linux") -> when(arch) {
            "aarch64" -> linuxArm64()
            "amd64" -> linuxX64()
            else -> throw GradleException("Unsupported architecture: $arch")
        }
        host.startsWith("windows") -> if(arch == "amd64") mingwX64() else throw GradleException("Unsupported architecture: $arch")
        else -> throw GradleException("Unsupported target: $host")
    }

    nativeTarget.apply {
        binaries.sharedLib()
    }

    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }

    wasmJs {
        outputModuleName = "kotp"

        browser()
        nodejs()
        d8()

        binaries.library()
    }

    js {
        outputModuleName = "kotp"

        browser()
        nodejs()

        binaries.library()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlincrypto.hmac.sha1)
            implementation(libs.kotlinx.io.core)
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), project.name, version.toString())

    pom {
        name = "kotp library"
        description = "A Kotlin Multiplatform library for HOTP and TOTP code generation"
        url = "https://github.com/ClementVicart/kotp"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://raw.githubusercontent.com/ClementVicart/kotp/refs/heads/main/LICENSE"
                distribution = "https://raw.githubusercontent.com/ClementVicart/kotp/refs/heads/main/LICENSE"
            }
        }
        developers {
            developer {
                id = "clement-vicart"
                name = "Clément Vicart"
                url = "https://github.com/ClementVicart"
            }
        }
        scm {
            url = "https://github.com/ClementVicart/kotp"
            connection = "scm:git:git://github.com/ClementVicart/kotp.git"
            developerConnection = "scm:git:git://github.com/ClementVicart/kotp.git"
        }
    }
}