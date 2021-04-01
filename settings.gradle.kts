enableFeaturePreview("GRADLE_METADATA")

pluginManagement {
    repositories {
        mavenLocal()
        maven { url = uri("https://dl.bintray.com/korlibs/korlibs") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("http://dl.bintray.com/kotlin/kotlin-eap") }
        mavenCentral()
        google()
        jcenter()
    }
}
