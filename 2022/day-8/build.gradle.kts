plugins {
    kotlin("jvm")
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":common"))
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(8)
}