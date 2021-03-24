plugins {
	id("com.soywiz.korge") version "2.0.8.1"
	kotlin("plugin.serialization") version "1.4.30"
}

repositories {
	mavenCentral()
	jcenter()
	google()
}

korge {
	id = "cis4030.gis.gis"
	name = "Gryph in the Shell"

	// Add project dependencies below and to `build/platforms/android/build.extra.gradle`
	dependencyMulti("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")

	// To enable all targets at once: targetAll()
	// To enable targets based on properties/environment variables: targetDefault()
	targetJvm()
	targetAndroidDirect()
}

/* IMPORTANT: Old buildscript
import com.soywiz.korge.gradle.*
buildscript {
	val korgePluginVersion: String by project

	repositories {
		mavenLocal()
		maven { url = uri("https://dl.bintray.com/korlibs/korlibs") }
		maven { url = uri("https://plugins.gradle.org/m2/") }
		mavenCentral()
		google()
		jcenter()
	}
	dependencies {
		classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
	}
}
apply<KorgeGradlePlugin>()
*/
