tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.14" // Plugin JavaFX
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":app")) // Conexión con la lógica del ERP

    implementation("org.openjfx:javafx-controls:21")
    implementation("org.openjfx:javafx-fxml:21")

    // Si usas otras partes de JavaFX, agrégalas aquí
    implementation("org.openjfx:javafx-graphics:21")
    implementation("org.openjfx:javafx-media:21")
    implementation("org.openjfx:javafx-web:21")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("com.empresa.erp.MainApp")
}


