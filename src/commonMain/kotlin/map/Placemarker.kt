package map

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.view.Container
import factories.LevelDataFactory
import factories.LevelManager
import models.LevelData
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.Serializable

@Serializable
class Placemarker(
        val level: Int,
        var xLocation: Int,
        var yLocation: Int,
        @Transient
        var bestTime: TimeSpan = TimeSpan(60000.0), // 60000.0ms = 60s; https://korlibs.soywiz.com/klock/#timespan
        var complete: Boolean = false,
        @Transient
        var levelData: LevelData? = null, // Holds the information about the level its attached to
    // var monsterOnTile : Sprite, // Sprite of the monster on the tile
) {
    //constructor(level : Int?, xLocation: Int?, yLocation: Int?, complete: Boolean?) : this(level, xLocation, yLocation, complete){}

    override fun toString(): String {
        return "Placemarker Level: ${this.level}\n\txLocation: ${this.xLocation}\n\tyLocation: ${this.yLocation}\n\tBestTime: ${this.bestTime.toString()}\n\tComplete?: ${this.complete}"
    }
}
