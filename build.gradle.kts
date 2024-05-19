plugins {
    id("java")
}

group = "com.skysongrp"
version = "1.1"

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/groups/public/")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    implementation(files("libs/OpenRP.jar"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "utf-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}