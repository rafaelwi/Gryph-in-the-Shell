package map

import com.soywiz.klock.TimeSpan
import models.BattleStage

class Placemarker(
        val level: Int,
        var xLocation: Int,
        var yLocation: Int,
        var bestTime: TimeSpan = TimeSpan(60000.0), // 60000.0ms = 60s; https://korlibs.soywiz.com/klock/#timespan
        var complete: Boolean = false,
        var levelData: BattleStage?, // Holds the information about the level its attached to
    // var monsterOnTile : Sprite, // Sprite of the monster on the tile
) {
    override fun toString(): String {
        return "Placemarker Level: ${this.level}\n\txLocation: ${this.xLocation}\n\tyLocation: ${this.yLocation}\n\tBestTime: ${this.bestTime.toString()}\n\tComplete?: ${this.complete}"
    }


}