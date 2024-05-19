import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.9.20"
}

group = "app.xlei"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

val ktor_version = "2.3.10"

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("org.jetbrains.compose.material3:material3-desktop:1.5.3")
    implementation("br.com.devsrsouza.compose.icons:feather:1.1.0")
    implementation("br.com.devsrsouza.compose.icons:tabler-icons:1.1.0")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.3")
    implementation("com.darkrockstudios:mpfilepicker:3.1.0")
    implementation("com.google.code.gson:gson:2.10.1")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "edus"
            packageVersion = "1.0.0"
        }
    }
}
