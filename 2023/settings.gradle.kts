pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "advent-of-code-2023"

val lastDay = 4
include("common", *(1..lastDay).map { "day-$it" }.toTypedArray())