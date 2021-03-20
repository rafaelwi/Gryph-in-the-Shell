package models.components

import com.soywiz.korge.input.mouse
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korma.geom.Anchor
import models.LevelManager
import scenes.MapScene

class GameOverMenu(private val levelManager: LevelManager?): Container() {

    private var backGround: SolidRect = solidRect(300, 150, Colors.WHITE) {
        anchor(Anchor.MIDDLE_CENTER)
    }
    private var menuText: Text = text("Game Over") {
        textSize = 50.0
        color = Colors.BLACK
        //I don't get why this puts the object to the top center
        alignment = TextAlignment.BOTTOM_CENTER
    }
    private var exitButton: SolidRect = solidRect(180, 40, Colors.RED) {
        //Why does this put it in the bottom center too?
        anchor(Anchor.TOP_CENTER)
        mouse {
            over {
                tint = Colors.MAROON
            }

            out {
                tint = Colors.RED
            }

            onClick {
                levelManager?.sceneContainer?.changeTo(MapScene::class)
            }
        }
    }

    init {
        addChild(backGround)
        addChild(menuText)
        addChild(exitButton)
    }

}