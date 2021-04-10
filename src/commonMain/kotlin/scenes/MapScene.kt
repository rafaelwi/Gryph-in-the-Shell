package scenes

import MainModule
import com.soywiz.klock.seconds
import com.soywiz.korge.input.mouse
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import map.GameMap
import map.GameMapFactory

class MapScene : Scene() {
    val deviceWidth : Int = MainModule.size.width
    val deviceHeight : Int = MainModule.size.height

    lateinit var transitionShade : SolidRect
    val SETTINGS_ICON = resourcesVfs["map\\settings_menu.png"]

    /** Initialize scene **/
    override suspend fun Container.sceneInit() {
        GameMapFactory.createGameMap(this, sceneContainer, "leveldata\\world1.json")
        transitionShade = solidRect(deviceWidth, deviceHeight, Colors.BLACK) {
            anchor(Anchor.TOP_LEFT)
            position(0, 0)
        }
    }

    /** Fade from black on entering scene **/
    override suspend fun sceneAfterInit() {
        transitionShade.tween(transitionShade::alpha[0.0], time = 2.seconds)
        transitionShade.size(0, 0)
    }

    /** Test function for drawing game map **/
    @Deprecated("Test function: Don't use this")
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
                    onClick { sceneContainer.changeTo(LevelScene::class) }
                }
            }
        }
    }
}
