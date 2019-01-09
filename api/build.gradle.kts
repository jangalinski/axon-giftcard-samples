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


//    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
//    id("org.jetbrains.kotlin.jvm").version("1.3.11")
//
//    // Apply the application plugin to add support for building a CLI application.
//    application
//}
//
//repositories {
//    // Use jcenter for resolving your dependencies.
//    // You can declare any Maven/Ivy/file repository here.
//    jcenter()
//}
//
//dependencies {
//    // Use the Kotlin JDK 8 standard library.
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//
//    // Use the Kotlin test library.
//    testImplementation("org.jetbrains.kotlin:kotlin-test")
//
//    // Use the Kotlin JUnit integration.
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
//}
//
//application {
//    // Define the main class for the application.
//    mainClassName = "io.github.jangalinski.axon.AppKt"
//}
