import com.soywiz.korge.gradle.util.get

plugins {
	id("com.soywiz.korge") version "2.0.7.1"
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
	version = "1.0.0"
	gameCategory = com.soywiz.korge.gradle.GameCategory.ROLE_PLAYING
	icon = File(rootDir, "icon.png")

	// Add project dependencies below and to `build/platforms/android/build.extra.gradle`
	dependencyMulti("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")

	// To enable all targets at once: targetAll()
	// To enable targets based on properties/environment variables: targetDefault()
	targetJvm()
	targetAndroidDirect()
}
