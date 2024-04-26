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
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.0")

    // Testing
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.compileJava {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
    options.compilerArgs.addAll(arrayOf("-Xlint:deprecation", "-Xlint:-options"))
    options.encoding = "UTF-8"
}

tasks.compileTestJava {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Zip>("package") {
    archiveFileName.set("secasign-sdk-${project.version}.zip")
    exclude(".git", ".gradle/", "build/", ".idea/", "'*.iml", "src/test/java/com/secacon/secasign/sdk/Values.java", "data/Signed_*.pdf", "secasign-sdk-*.zip")
    destinationDirectory.set(project.layout.buildDirectory)
    from(project.rootDir)
}
