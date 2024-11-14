import java.io.FileInputStream
import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.devtoolsKsp) apply false
    alias(libs.plugins.androidHilt) apply false
    alias(libs.plugins.googleServices) apply false
}

val keystorePropertiesFile = rootProject.file("keys.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

ext["apiKey"] = keystoreProperties["API_KEY"]
ext["mapsKey"] = keystoreProperties["MAPS_KEY"]
