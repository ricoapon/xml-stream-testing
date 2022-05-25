plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("one.util:streamex:0.8.1")

    implementation("com.sun.xml.bind:jaxb-core:2.3.0.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.1")
    implementation("org.javassist:javassist:3.25.0-GA")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.hamcrest:hamcrest:2.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<Test> {
    // We have a large XML file that surely doesn't fit in this heap size. This way we can actually test everything works!
    maxHeapSize = "10m"
}
