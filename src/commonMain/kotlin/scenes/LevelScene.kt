package scenes

import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.text.TextAlignment
import models.LevelManager
import factories.LevelDataFactory
import models.LevelData

class LevelScene (levelData: LevelData) : Scene() {
    val deviceWidth : Int = MainModule.size.width
    val deviceHeight : Int = MainModule.size.height
    val levelData : LevelData = levelData

    // Entrypoint
    override suspend fun Container.sceneInit() {
        val game = LevelManager(this, sceneContainer)
        //val level = LevelDataFactory.createTestLevel(game)
        addChild(levelData)
        levelData.init()

        text("Battle Scene", textSize = 100.0, alignment = TextAlignment.TOP_CENTER) {
            position(deviceWidth / 2.0, 0.0)
        }
    }
}