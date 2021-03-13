package scenes

import com.soywiz.korge.input.mouse
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korma.geom.Anchor
import factories.BattleManager
import factories.BattleStageFactory
import models.BattleStage

class BattleScene : Scene() {
    // Entrypoint
    override suspend fun Container.sceneInit() {

        val game = BattleManager(this)
        val battleStage = BattleStageFactory.createTestStage(game)
        addChild(battleStage)
        battleStage.init()

        text("Battle Scene", textSize = 100.0, alignment = TextAlignment.TOP_CENTER) {
            position(MainModule.size.width / 2.0, 0.0)
        }

        // Return to map scene button
        solidRect(100, 100, Colors.RED) {
            anchor(Anchor.TOP_LEFT)
            position(0, 0)
            mouse {
                over {
                    tint = Colors.MAROON
                }

                out {
                    tint = Colors.RED
                }

                onClick {
                    sceneContainer.changeTo(MapScene::class)
                }
            }
        }
    }
}