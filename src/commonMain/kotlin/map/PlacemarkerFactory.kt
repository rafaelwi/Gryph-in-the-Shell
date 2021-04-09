package map

import com.soywiz.klogger.Console
import com.soywiz.korge.input.mouse
import com.soywiz.korge.scene.SceneContainer
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.anchor
import com.soywiz.korge.view.image
import com.soywiz.korge.view.position
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import models.LevelData
import models.LevelManager
import models.ScreenManager
import models.entities.LevelScoreIO
import models.entities.Player
import scenes.LevelScene

object PlacemarkerFactory {
    private val deviceWidth : Int = MainModule.size.width
    private val deviceHeight : Int = MainModule.size.height

    fun createTestPlacemarker(levelData: LevelData?, x: Int, y: Int) : Placemarker {
        return Placemarker(1, x, y, levelData = levelData)
    }

    suspend fun createPlacemarker (c : Container, sc : SceneContainer, p: Placemarker, w: Int) {
        val icon : String

        // Hardcoded flag to center placemarker
        if (p.xLocation == -1 && p.yLocation == -1) {
            p.xLocation = deviceWidth / 2
            p.yLocation = deviceHeight / 2
        }

        icon = if (p.complete) "map\\placemark_complete.png" else "map\\placemark_incomplete.png"

        p.levelData?.setLevelManager(LevelManager(c, sc))
        p.levelData?.setCurrentPlayer(Player("Hero", 100.0)) // We can pass character obj here?

/**        Console.log("world ${w} level ${p.level}")
        Console.log(LevelScoreIO(null, w, p.level).readLevelScore(w, p.level)) */

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
