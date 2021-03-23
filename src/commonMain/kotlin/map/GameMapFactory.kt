package map

import com.soywiz.korge.scene.Scene
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

    fun createTestGameMap(levelData: LevelData?) : GameMap {
        val list = listOf(
                PlacemarkerFactory.createTestPlacemarker(levelData, MainModule.size.width / 2, MainModule.size.height / 2))
        return GameMap(1, "map\\grass.png", list)
    }

    suspend fun createGameMap(c : Container, sc : SceneContainer, filename : String){
        val fileContents = readLevelData(filename)
        var gameMap = Json{isLenient = true}.decodeFromString<GameMap>(fileContents)

        drawGameMap(c, sc, gameMap.backgroundPath.toString())

        for (p in gameMap.levels) {
            PlacemarkerFactory.createPlacemarker(c, sc, p)
        }
    }

    // https://bezkoder.com/kotlin-android-read-json-file-assets-gson/
    suspend fun readLevelData(filename : String) : String {
        var levelDataContents : String

        try {
            levelDataContents = resourcesVfs[filename].readString()
        }
        catch (ioException: IOException) {
            ioException.printStackTrace()
            levelDataContents = null.toString()
        }
        return levelDataContents
    }

    private suspend fun drawGameMap(c : Container, sc : SceneContainer, bgPath : String) {
        // For the atlas/tiling background: https://github.com/korlibs/korge-samples/tree/master/samples/atlas
        c.image(resourcesVfs[bgPath].readBitmap()) {
            setSizeScaled(deviceWidth * 1.0, deviceHeight * 1.0)
        }
    }
}
