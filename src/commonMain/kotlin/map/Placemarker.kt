package map

import kotlinx.serialization.Serializable
import models.LevelData

/** Marker on the map that indicates a level **/
@Serializable
class Placemarker(
        val level: Int,
        var xLocation: Int,
        var yLocation: Int,
        var complete: Boolean = false,
        var levelData: LevelData? = null
) {
    override fun toString(): String {
        return "Placemarker Level: ${this.level}\n\txLocation: ${this.xLocation}\n\tyLocation: ${this.yLocation}\n\tComplete?: ${this.complete}"
    }
}
