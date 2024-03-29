package map

import com.soywiz.korge.scene.SceneContainer
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.image
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.lang.IOException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.LevelData

object GameMapFactory {
    private val deviceWidth : Int = MainModule.size.width
    private val deviceHeight : Int = MainModule.size.height

    /** Creates a game map to test with **/
    fun createTestGameMap(levelData: LevelData?) : GameMap {
        val list = listOf(
                PlacemarkerFactory.createTestPlacemarker(levelData, MainModule.size.width / 2, MainModule.size.height / 2))
        return GameMap(1, "map\\grass.png", list)
    }

    /** Reads json file and creates a map fromm it */
    suspend fun createGameMap(c : Container, sc : SceneContainer, filename : String){
        val fileContents = readLevelData(filename)
        val gameMap = Json{isLenient = true}.decodeFromString<GameMap>(fileContents)
        val world = gameMap.world

        drawGameMap(c, gameMap.backgroundPath.toString())

        for (p in gameMap.levels) {
            PlacemarkerFactory.createPlacemarker(c, sc, p, world)
        }
    }

    /* Reads json from res/leveldata/ */
    private suspend fun readLevelData(filename : String) : String {
        val levelDataContents : String = try {
            resourcesVfs[filename].readString()
        }
        catch (ioException: IOException) {
            ioException.printStackTrace()
            null.toString()
        }
        return levelDataContents
    }

    private suspend fun drawGameMap(c: Container, bgPath: String) {
        // For the atlas/tiling background: https://github.com/korlibs/korge-samples/tree/master/samples/atlas
        c.image(resourcesVfs[bgPath].readBitmap()) {
            setSizeScaled(deviceWidth * 1.0, deviceHeight * 1.0)
        }
    }
}
