package scenes

import MainModule
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.text
import com.soywiz.korim.text.TextAlignment
import constants.DebugStatus
import models.LevelData
import models.ScreenManager

class LevelScene (val levelData: LevelData) : Scene() {
    var DEBUG_STAT = DebugStatus.OFF
    val deviceWidth : Int = MainModule.size.width
    val deviceHeight : Int = MainModule.size.height

    // Entrypoint
    override suspend fun Container.sceneInit() {
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
