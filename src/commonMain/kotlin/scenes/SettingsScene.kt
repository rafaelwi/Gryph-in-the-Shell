package scenes

import com.soywiz.korge.input.mouse
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korma.geom.Anchor

class SettingsScene : Scene() {
    // Entrypoint
    override suspend fun Container.sceneInit() {
        text("Settings Menu", textSize = 100.0, alignment = TextAlignment.TOP_CENTER) {
            position(MainModule.size.width / 2.0, 0.0)
        }

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