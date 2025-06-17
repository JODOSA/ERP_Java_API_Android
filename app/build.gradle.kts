
plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
	java
}


repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {

    implementation ("com.github.librepdf:openpdf:1.3.30")



    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final") // Hibernate (JPA)
	// implementation("org.xerial:sqlite-jdbc:3.42.0.0")

	implementation("jakarta.persistence:jakarta.persistence-api:3.1.0") // API de JPA

	//Dialecto de Hibernate para SQLite
	// implementation("org.hibernate.orm:hibernate-community-dialects:6.4.4.Final")

    // Conector para MariaDB
    implementation("org.mariadb.jdbc:mariadb-java-client:3.1.4")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")

}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    // Define the main class for the application.
	mainClass.set("empresa.Main")
}

tasks.test{
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}


