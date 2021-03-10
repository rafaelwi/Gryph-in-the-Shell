package scenes

import com.soywiz.korge.input.mouse
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korma.geom.Anchor

class MapScene : Scene() {
    // Entrypoint
    override suspend fun Container.sceneInit() {
        text("Map Scene", textSize = 100.0, alignment = TextAlignment.TOP_CENTER) {
            position(MainModule.size.width / 2.0, 0.0)
        }

        // Settings button
        solidRect(100, 100, Colors.SILVER) {
            anchor(Anchor.TOP_RIGHT)
            position(MainModule.size.width, 0)
            mouse {
                over {
                    tint = Colors.SLATEGRAY
                }

                out {
                    tint = Colors.SILVER
                }

                onClick {
                    sceneContainer.changeTo(SettingsScene::class)
                }
            }
        }

        // Battle button
        solidRect(100, 100, Colors.ORANGE) {
            anchor(Anchor.MIDDLE_CENTER)
            position(MainModule.size.width / 2, MainModule.size.height / 2)
            mouse {
                over {
                    tint = Colors.ORANGERED
                }

                out {
                    tint = Colors.ORANGE
                }

                onClick {
                    sceneContainer.changeTo(BattleScene::class)
                }
            }
        }
    }
}