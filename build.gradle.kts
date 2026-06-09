plugins {
    kotlin("jvm") version "1.9.24"
    application
    id("org.jetbrains.dokka") version "1.9.20"
}

group = "org.iesra"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.h2database:h2:2.2.224")
    implementation("org.mongodb:mongodb-driver-sync:5.1.2")
    implementation("ch.qos.logback:logback-classic:1.5.6")
}

application {
    mainClass.set("org.iesra.MainKt")
}

kotlin {
    jvmToolchain(21)
}

tasks.run {
    standardInput = System.`in`
}

tasks.dokkaHtml {
    outputDirectory.set(layout.buildDirectory.dir("dokka"))
    dokkaSourceSets.configureEach {
        suppressInheritedMembers.set(true)
    }
}
