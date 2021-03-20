import com.soywiz.korge.Korge
import com.soywiz.korge.scene.Module
import com.soywiz.korge.scene.Scene
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.ScaleMode
import com.soywiz.korma.geom.SizeInt
import scenes.LevelScene
import scenes.MapScene
import scenes.SettingsScene
import scenes.TitleScene
import kotlin.reflect.KClass

suspend fun main() = Korge(Korge.Config(module = MainModule))

object MainModule : Module() {
    override val mainScene: KClass<out Scene> = TitleScene::class // Sets the first scene
    override val title: String = "Gryph in the Shell"
    override val scaleMode: ScaleMode = ScaleMode.COVER
    override val bgcolor: RGBA = Colors.BLACK

    // Note! This is the resolution of the Pixel 3a that's being used in the emulator.
    // Work is needed to get things to properly scale for different screen sizes and aspect ratios
    // See https://korlibs.soywiz.com/korge/reference/resolutions/#aspect-ratio
    override val size = SizeInt(1080, 2220) // Virtual size
    override val windowSize = SizeInt(360, 740)

    override suspend fun AsyncInjector.configure() {
        mapPrototype { LevelScene() }
        mapPrototype { MapScene() }
        mapPrototype { SettingsScene() }
        mapPrototype { TitleScene() }
    }
}
