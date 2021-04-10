package models.components

import com.soywiz.korge.input.mouse
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korma.geom.Anchor
import models.LevelManager
import models.entities.LevelScoreIO
import scenes.GryphScene

/** Mini popup showing player when game is complete **/
class GameOverMenu(private val levelManager: LevelManager?): Container() {
    private lateinit var levelScoreIo: LevelScoreIO
    private var backGround: SolidRect = solidRect(300, 150, Colors.WHITE) {
        anchor(Anchor.MIDDLE_CENTER)
    }
    private var menuText: Text = text("Game Over") {
        textSize = 50.0
        color = Colors.BLACK
        alignment = TextAlignment.BOTTOM_CENTER
    }
    private var exitButton: SolidRect = solidRect(180, 40, Colors.RED) {
        anchor(Anchor.TOP_CENTER)
        mouse {
            over {
                tint = Colors.MAROON
            }

            out {
                tint = Colors.RED
            }

            onClick {
                levelScoreIo = LevelScoreIO(1, 1)
                levelScoreIo.initScoreToRecord(levelManager)
                levelScoreIo.writeScoreToFile(levelManager)
                levelManager?.sceneContainer?.changeTo(GryphScene::class)
            }
        }
    }

    /** Initialzies container and adds items to it **/
    init {
        addChild(backGround)
        addChild(menuText)
        addChild(exitButton)
    }
}
