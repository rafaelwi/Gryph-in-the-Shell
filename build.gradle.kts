plugins {
	id("com.soywiz.korge") version "2.0.7.1"
	application
}

repositories {
	mavenCentral()
	jcenter()
	google()
}

dependencies {
	implementation("com.beust:klaxon:5.5") // doesn't work
}

korge {
	id = "cis4030.gis.gis"
	name = "Gryph in the Shell"

	// To enable all targets at once: targetAll()
	// To enable targets based on properties/environment variables: targetDefault()
	targetJvm()
	targetAndroidIndirect()
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
