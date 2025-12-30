import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dokka)
}

group = "dev.vicart.kotp"
version = "0.0.1"

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
        withSourcesJar()
    }

    wasmJs {
        outputModuleName = "kotp"
        withSourcesJar()

        browser()
        nodejs()
        d8()

        binaries.library()
    }

    js {
        outputModuleName = "kotp"
        withSourcesJar()

        browser()
        nodejs()

        binaries.library()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlincrypto.hmac.sha1)
            implementation(libs.kotlinx.io.core)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}