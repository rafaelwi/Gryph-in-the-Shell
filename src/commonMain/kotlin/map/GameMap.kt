package map

import com.soywiz.korge.input.mouse
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.scene.SceneContainer
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import scenes.BattleScene

class GameMap (
        val world: Int,
        var backgroundPath: String?,
        var levels: List<Placemarker>
) : Container() {
    lateinit var background : Image

    override fun toString(): String {
        var levelStrings = ""
        for (pm in this.levels) levelStrings += pm.toString() + "\n"
        return "World ${this.world}\nLevels:\n$levelStrings"
    }

    // This function draws the game map to the screen
    suspend fun drawGameMap(sceneContainer : SceneContainer) {
        // Draw the game map
        background = image(resourcesVfs[this@GameMap.backgroundPath.toString()].readBitmap()) {
            setSizeScaled(MainModule.size.width * 1.0, MainModule.size.height * 1.0)
        }

        // Loop through the level placemarkers and draw them too
        for (pm in this@GameMap.levels) {
            image(resourcesVfs["map\\placemark_incomplete.png"].readBitmap()){
                setSizeScaled(256.0, 142.0)
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

        // Function here to draw "paths" between the placemakers (just two straight lines)

    }
}