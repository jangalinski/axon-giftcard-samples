import org.jetbrains.kotlin.backend.common.bridges.findInterfaceImplementation

plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.axonframework:axon-modelling:4.0.3")
}
