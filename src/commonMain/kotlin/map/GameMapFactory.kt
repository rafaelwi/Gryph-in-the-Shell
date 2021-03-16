package map

import models.BattleStage


object GameMapFactory {
    fun createTestGameMap(battleStage: BattleStage?) : GameMap {
        val list = listOf(
                PlacemarkerFactory.createTestPlacemarker(battleStage, MainModule.size.width / 2, MainModule.size.height / 2))
        return GameMap(1, "map\\grass.png", list)
    }

    /*
    // This function will take in a large JSON object containing world information as well as level data
    fun createGameMap(jsonObj : SomeObjectHoldingJSON_Data) : Placemarker {
        // Process JSON here for world information
        // Loop through the levels array and use PlacemakerFactory to create Placemakers into a lit
        return GameMap(// parsed data)
    }
     */
}
