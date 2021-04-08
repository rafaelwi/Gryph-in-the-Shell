package scenes

import com.soywiz.klogger.Console
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.text.TextAlignment
import constants.DebugStatus
import models.ScreenManager
import models.LevelData

var DEBUG_STAT = DebugStatus.OFF

class LevelScene (levelData: LevelData) : Scene() {
    val deviceWidth : Int = MainModule.size.width
    val deviceHeight : Int = MainModule.size.height
    val levelData : LevelData = levelData

    // Entrypoint
    override suspend fun Container.sceneInit() {
        //val level = LevelDataFactory.createTestLevel(game)
        levelData.setScreenManager(ScreenManager(sceneView, levelData.getLevelManager()))
        addChild(levelData)
        levelData.init()

        if (DEBUG_STAT == DebugStatus.ON) {
            text("Battle Scene", textSize = 100.0, alignment = TextAlignment.TOP_CENTER) {
                position(deviceWidth * 0.5, 0.0)
            }
        }
    }
}