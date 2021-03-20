package map

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.view.Container
import factories.LevelDataFactory
import models.LevelData
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.Serializable

@Serializable
class Placemarker(
        val level: Int,
        var xLocation: Int,
        var yLocation: Int,
        var complete: Boolean = false,
        var levelData: LevelData? = null, // Holds the information about the level its attached to
    // var monsterOnTile : Sprite, // Sprite of the monster on the tile
) {
    //constructor(level : Int?, xLocation: Int?, yLocation: Int?, complete: Boolean?) : this(level, xLocation, yLocation, complete){}

    override fun toString(): String {
        return "Placemarker Level: ${this.level}\n\txLocation: ${this.xLocation}\n\tyLocation: ${this.yLocation}\n\tComplete?: ${this.complete}"
    }
}
