tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.hibernate.orm:hibernate-core")
}