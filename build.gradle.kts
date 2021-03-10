import com.soywiz.korge.gradle.*

buildscript {
	val korgePluginVersion: String by project

	repositories {
		mavenLocal()
		maven { url = uri("https://dl.bintray.com/korlibs/korlibs") }
		maven { url = uri("https://plugins.gradle.org/m2/") }
		mavenCentral()
		google()
	}
	dependencies {
		classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
	}
}

apply<KorgeGradlePlugin>()

korge {
	id = "cis4030.gis.gis"
	name = "Gryph in the Shell"
	supportSwf()
// To enable all targets at once: targetAll()

// To enable targets based on properties/environment variables: targetDefault()
	targetJvm()
	targetJs()
	targetDesktop()
	targetIos()
	targetAndroidIndirect()
	targetAndroidDirect()
}