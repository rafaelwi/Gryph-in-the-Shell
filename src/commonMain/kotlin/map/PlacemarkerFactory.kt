package map

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
import scenes.LevelScene

object PlacemarkerFactory {
    private val deviceWidth : Int = MainModule.size.width
    private val deviceHeight : Int = MainModule.size.height

    fun createTestPlacemarker(levelData: LevelData?, x: Int, y: Int) : Placemarker {
        return Placemarker(1, x, y, levelData = levelData)
    }

    suspend fun createPlacemarker (c : Container, sc : SceneContainer, p: Placemarker) {
        val icon : String

        // Hardcoded flag to center placemarker
        if (p.xLocation == -1 && p.yLocation == -1) {
            p.xLocation = deviceWidth / 2
            p.yLocation = deviceHeight / 2
        }

        icon = if (p.complete) "map\\placemark_complete.png" else "map\\placemark_incomplete.png"

        println(p.levelData.toString())

        c.image(resourcesVfs[icon].readBitmap()){
            setSizeScaled(256.0, 142.0)
            anchor(Anchor.MIDDLE_CENTER)
            position(p.xLocation, p.yLocation)
            mouse {
                over { tint = Colors.ORANGERED }
                out { tint = Colors.ORANGE }
                // TODO: We need to pass the placemarker's BattleStage data into the onClick (see injectors?)
                onClick { sc.changeTo<LevelScene>(p.levelData!!) }
            }
        }
    }

    /*
    // This function will take in some JSON object that contains level data and make a placemarker to draw on the map
    fun createPlacemarker(battleStage: BattleStage) : Placemarker {
        // Process JSON here
        return Placemarker(// with the level data in here)
    }
     */
}
