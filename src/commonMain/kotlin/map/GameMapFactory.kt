package map

import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.lang.IOException
import kotlinx.serialization.json.Json
import models.LevelData

object GameMapFactory {
    fun createTestGameMap(levelData: LevelData?) : GameMap {
        val list = listOf(
                PlacemarkerFactory.createTestPlacemarker(levelData, MainModule.size.width / 2, MainModule.size.height / 2))
        return GameMap(1, "map\\grass.png", list)
    }

    // https://bezkoder.com/kotlin-android-read-json-file-assets-gson/
    suspend fun readLevelData(filename : String) : String? {
        var levelDataContents : String

        try {
            levelDataContents = resourcesVfs[filename].readString()
        }
        catch (ioException: IOException) {
            ioException.printStackTrace()
            levelDataContents = null
        }
        return levelDataContents
    }

    suspend fun createGameMap(filename : String) : GameMap {
        this.readLevelData(filename)

        // Process JSON here for world information
        // Loop through the levels array and use PlacemakerFactory to create Placemakers into a lit
        //return GameMap(// parsed data/)
    }
}
