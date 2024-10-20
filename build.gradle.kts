plugins {
    id("java")
    application
}

val flatlafVersion = "3.5.1"
val logbackVersion = "1.4.12"

application {
    mainClass.set("org.example.Main")
}

dependencies {
    implementation("com.formdev:flatlaf:$flatlafVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}