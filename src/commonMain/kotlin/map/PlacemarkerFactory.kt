package map

import com.soywiz.klogger.Console
import com.soywiz.korge.input.mouse
import com.soywiz.korge.scene.SceneContainer
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import com.soywiz.korma.math.roundDecimalPlaces
import models.LevelData
import models.LevelManager
import models.ScreenManager
import models.entities.LevelScore
import models.entities.LevelScoreIO
import models.entities.Player
import scenes.LevelScene

object PlacemarkerFactory {
    private val deviceWidth : Int = MainModule.size.width
    private val deviceHeight : Int = MainModule.size.height

    fun createTestPlacemarker(levelData: LevelData?, x: Int, y: Int) : Placemarker {
        return Placemarker(1, x, y, levelData = levelData)
    }

    //Create the text and place it appropriately. If gets null, then no score and display default: (Incomplete), else display score
    suspend fun createPlacemarker (c : Container, sc : SceneContainer, p: Placemarker, w: Int) {
        val icon : String
        val levelScore: LevelScore? = LevelScoreIO(w, p.level).readLevelScore()

        // Hardcoded flag to center placemarker
        if (p.xLocation == -1 && p.yLocation == -1) {
            p.xLocation = deviceWidth / 2
            p.yLocation = deviceHeight / 2
        }

        icon = if (p.complete) "map\\placemark_complete.png" else "map\\placemark_incomplete.png"

        p.levelData?.setLevelManager(LevelManager(c, sc))
        p.levelData?.setCurrentPlayer(Player("Hero", 100.0))

        if (levelScore == null) {
            c.text("Incomplete", color = Colors.WHITE, textSize = 70.0, alignment = TextAlignment.MIDDLE_CENTER) {
                position(p.xLocation, p.yLocation - 120)
            }
        } else {
            c.text("Current Best: ${levelScore.getScore().roundDecimalPlaces(1)}s", color = Colors.WHITE, textSize = 70.0, alignment = TextAlignment.MIDDLE_CENTER) {
                position(p.xLocation, p.yLocation - 120)
            }
        }

        c.image(resourcesVfs[icon].readBitmap()){
            setSizeScaled(256.0, 142.0)
            anchor(Anchor.MIDDLE_CENTER)
            position(p.xLocation, p.yLocation)
            mouse {
                over { tint = Colors.ORANGERED }
                out { tint = Colors.ORANGE }
                onClick { sc.changeTo<LevelScene>(p.levelData!!) }
            }
        }
    }
}
