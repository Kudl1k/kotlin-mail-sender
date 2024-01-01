plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("com.sun.mail:javax.mail:1.6.2")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}