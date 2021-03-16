package map

import models.LevelData

object PlacemarkerFactory {
    fun createTestPlacemarker(levelData: LevelData?, x: Int, y: Int) : Placemarker {
        return Placemarker(1, x, y, levelData = levelData)
    }

    /*
    // This function will take in some JSON object that contains level data and make a placemarker to draw on the map
    fun createPlacemarker(battleStage: BattleStage) : Placemarker {
        // Process JSON here
        return Placemarker(// with the level data in here)
    }
     */
}
