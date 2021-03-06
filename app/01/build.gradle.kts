plugins {
    kotlin("jvm")
    application
}

dependencies {
  implementation(project(":api"))

  implementation(project(":app:common:domain"))
  implementation(project(":app:common:event"))
  implementation(project(":app:common:projection"))

  // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.axonframework:axon-configuration:4.0.3")
    implementation("org.axonframework:axon-eventsourcing:4.0.3")

    implementation("io.github.microutils:kotlin-logging:1.6.22")

    implementation("org.slf4j:slf4j-simple:1.7.25")


}


application {
    // Define the main class for the application.
    mainClassName = "io.github.jangalinski.axon.giftcard.app01.App01Kt"
}
