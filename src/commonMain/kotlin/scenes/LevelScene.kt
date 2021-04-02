package scenes

import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.text.TextAlignment
import models.LevelManager
import factories.LevelDataFactory
import models.ScreenManager

class LevelScene (/* TODO: Add injector deps here */) : Scene() {
    val deviceWidth : Int = MainModule.size.width
    val deviceHeight : Int = MainModule.size.height
    // Entrypoint
    override suspend fun Container.sceneInit() {
        val gameManager = LevelManager(this, sceneContainer)
        val screenManager = ScreenManager(sceneView, gameManager)
        val level = LevelDataFactory.createTestLevel(gameManager, screenManager)
        addChild(level)
        level.init()

        text("Battle Scene", textSize = 100.0, alignment = TextAlignment.TOP_CENTER) {
            position(deviceWidth * 0.5, 0.0)
        }
    }
}