package models.gui

import com.soywiz.korge.input.mouse
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Anchor
import factories.LevelManager
import scenes.MapScene

class GameOverMenu(private val levelManager: LevelManager): Container() {

    private var backGround: SolidRect = solidRect(200, 200, Colors.WHITE) {
        anchor(Anchor.MIDDLE_CENTER)
    }
    private var exitButton: SolidRect = solidRect(50, 50, Colors.RED) {
        anchor(Anchor.MIDDLE_CENTER)
        mouse {
            over {
                tint = Colors.MAROON
            }

            out {
                tint = Colors.RED
            }

            onClick {
                levelManager.sceneContainer?.changeTo(MapScene::class)
            }
        }
    }

    init {
        addChild(backGround)
        addChild(exitButton)
    }

}