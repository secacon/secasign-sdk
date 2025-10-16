plugins {
    id("java")
}

group = "com.secacon.secasign"
version = "5.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Serialization
    implementation("tools.jackson.core:jackson-databind:3.0.0")

    // Testing
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-launcher")
}

tasks.compileJava {
    sourceCompatibility = JavaVersion.VERSION_17.toString()
    targetCompatibility = JavaVersion.VERSION_17.toString()
    options.compilerArgs.addAll(arrayOf("-Xlint:deprecation", "-Xlint:-options"))
    options.encoding = "UTF-8"
}

tasks.compileTestJava {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}
