package map

import MainModule
import com.soywiz.korge.input.mouse
import com.soywiz.korge.scene.SceneContainer
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import com.soywiz.korma.math.roundDecimalPlaces
import models.LevelData
import models.LevelManager
import models.entities.LevelScore
import models.entities.LevelScoreIO
import models.entities.Player
import scenes.LevelScene

object PlacemarkerFactory {
    private val deviceWidth : Int = MainModule.size.width
    private val deviceHeight : Int = MainModule.size.height

    /** Creates a placemarker for testing purposes **/
    fun createTestPlacemarker(levelData: LevelData?, x: Int, y: Int) : Placemarker {
        return Placemarker(1, x, y, levelData = levelData)
    }

    /** Creates a placemarker, based on the data from json file **/
    suspend fun createPlacemarker (c : Container, sc : SceneContainer, p: Placemarker, w: Int) {
        // Hardcoded flag to center placemarker
        if (p.xLocation == -1 && p.yLocation == -1) {
            p.xLocation = deviceWidth / 2
            p.yLocation = deviceHeight / 2
        }

        // Determine which placemarker to use
        val icon : String = if (p.complete) "map\\placemark_complete.png" else "map\\placemark_incomplete.png"

        // Add level managers
        p.levelData?.setLevelManager(LevelManager(sc))
        p.levelData?.setCurrentPlayer(Player("Hero", 100.0))

        // Draw the visual components of the placemarker
        addBestScore(c, w, p)
        drawPlacemarker(c, sc, p, icon)
    }

    /** Writes the best score above the placemarker **/
    private suspend fun addBestScore(c : Container, world : Int, p: Placemarker) {
        val levelScore: LevelScore? = LevelScoreIO(world, p.level).readLevelScore()

        if (levelScore == null) {
            c.text("Incomplete", color = Colors.WHITE, textSize = 70.0, alignment = TextAlignment.MIDDLE_CENTER) {
                position(p.xLocation, p.yLocation - 120)
            }
        } else {
            c.text("Current Best: ${levelScore.getScore().roundDecimalPlaces(1)}s", color = Colors.WHITE, textSize = 70.0, alignment = TextAlignment.MIDDLE_CENTER) {
                position(p.xLocation, p.yLocation - 120)
            }
        }
    }

    /** Draws placemarker onto screen **/
    private suspend fun drawPlacemarker(c : Container, sc : SceneContainer, p : Placemarker, icon : String) {
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
