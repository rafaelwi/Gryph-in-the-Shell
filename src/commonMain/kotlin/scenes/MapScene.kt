package scenes

import com.soywiz.korge.input.mouse
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import map.GameMapFactory

class MapScene : Scene() {
    val deviceWidth : Int = MainModule.size.width
    val deviceHeight : Int = MainModule.size.height

    // Entrypoint
    override suspend fun Container.sceneInit() {
        // Background
        // For the atlas/tiling background: https://github.com/korlibs/korge-samples/tree/master/samples/atlas


        val world = GameMapFactory.createTestGameMap(null)
        addChild(world)
        world.drawGameMap(sceneContainer)

        text("Map Scene", textSize = 100.0, alignment = TextAlignment.TOP_CENTER, color = Colors.BLACK) {
            position(deviceWidth / 2.0, 0.0)
        }


        // Settings button
        solidRect(100, 100, Colors.SILVER) {
            anchor(Anchor.TOP_RIGHT)
            position(deviceWidth, 0)
            mouse {
                over {
                    tint = Colors.SLATEGRAY
                }

                out {
                    tint = Colors.SILVER
                }

                onClick {
                    sceneContainer.changeTo(SettingsScene::class)
                }
            }
        }

        // Battle button
        /*
        image(resourcesVfs["map\\placemark_incomplete.png"].readBitmap()){
            setSizeScaled(256.0, 142.0)
            anchor(Anchor.MIDDLE_CENTER)
            position(deviceWidth / 2, deviceHeight / 2)
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
         */

    }


}