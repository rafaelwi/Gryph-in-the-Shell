package scenes

import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.text.TextAlignment
import factories.LevelManager
import factories.LevelDataFactory

class BattleScene : Scene() {
    // Entrypoint
    override suspend fun Container.sceneInit() {

        val game = LevelManager(this, sceneContainer)
        val battleStage = LevelDataFactory.createTestLevel(game)
        addChild(battleStage)
        battleStage.init()

        text("Battle Scene", textSize = 100.0, alignment = TextAlignment.TOP_CENTER) {
            position(MainModule.size.width / 2.0, 0.0)
        }
    }
}