package scenes

import MainModule
import com.soywiz.korge.input.mouse
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korma.geom.Anchor

class TitleScene : Scene() {

    // Entrypoint
    override suspend fun Container.sceneInit() {
        text("Gryph in the Shell", textSize = 100.0, alignment = TextAlignment.TOP_CENTER) {
            position(MainModule.size.width / 2.0, 0.0)
        }

        // Go to map scene button
        solidRect(100, 100, Colors.RED) {
            anchor(Anchor.MIDDLE_CENTER)
            position(MainModule.size.width / 2, MainModule.size.height / 2)
            mouse {
                // The over and out events are useless for Android, but they're good indicators for the runJvm config
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