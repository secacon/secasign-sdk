plugins {
    id("java")
}

group = "com.secacon.secasignbox"
version = "5.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Serialization
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.0")

    // Testing
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.compileJava {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
    options.encoding = "UTF-8"
}

tasks.compileTestJava {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Zip>("package") {
    archiveFileName.set("secasign-box-sdk-${project.version}.zip")
    exclude(".git", ".gradle/", "build/", ".idea/", "'*.iml", "src/test/java/com/secacon/secasignbox/sdk/Values*.java", "data/Signed_*.pdf", "secasign-box-sdk-*.zip")
    destinationDirectory.set(project.buildDir)
    from(project.rootDir)
}
