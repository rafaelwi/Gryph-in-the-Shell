package scenes

import com.soywiz.korge.input.mouse
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import map.GameMap
import map.GameMapFactory
import models.entities.Enemy

class MapScene : Scene() {
    val deviceWidth : Int = MainModule.size.width
    val deviceHeight : Int = MainModule.size.height

    val SETTINGS_ICON = resourcesVfs["map\\settings_menu.png"]

    // Entrypoint
    override suspend fun Container.sceneInit() {


        GameMapFactory.createGameMap(this, sceneContainer, "leveldata\\world1.json")

        // Settings button, can be added to the GameMapFactory
        image(SETTINGS_ICON.readBitmap()) {
            anchor(Anchor.TOP_RIGHT)
            position(deviceWidth, 0)
            mouse {
                over { tint = Colors.SLATEGRAY }
                out { tint = Colors.WHITE } // removes tint
                onClick { sceneContainer.changeTo(SettingsScene::class) }
            }
        }
    }

    private suspend fun drawGameMap(gameMap : GameMap, c: Container){
        // Draw the game map
        // For the atlas/tiling background: https://github.com/korlibs/korge-samples/tree/master/samples/atlas
        c.image(resourcesVfs[gameMap.backgroundPath.toString()].readBitmap()) {
            setSizeScaled(deviceWidth * 1.0, deviceHeight * 1.0)
        }

        // Loop through the level placemarkers and draw them too
        for (pm in gameMap.levels) {
            c.image(resourcesVfs["map\\placemark_incomplete.png"].readBitmap()){
                setSizeScaled(256.0, 142.0)
                anchor(Anchor.MIDDLE_CENTER)
                position(deviceWidth / 2, deviceHeight / 2)
                mouse {
                    over { tint = Colors.ORANGERED }
                    out { tint = Colors.ORANGE }
                    // TODO: We need to pass the placemarker's BattleStage data into the onClick (see injectors?)
                    onClick { sceneContainer.changeTo(LevelScene::class) }
                }
            }
        }
        // TODO: Function to draw "paths" between the placemarkers (just two straight lines)
    }
}
