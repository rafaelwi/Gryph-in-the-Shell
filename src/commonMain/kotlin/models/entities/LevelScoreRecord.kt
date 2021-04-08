package models.entities

import kotlinx.serialization.Serializable

@Serializable()
class LevelScoreRecord(private var saves: Array<LevelScore>) {

    var tempMuteList: MutableList<LevelScore>? = null

    fun findScoreIndex(world: Int, level: Int): Int {
        val foundScore = saves.find { it.getLevel() == level && it.getWorld() == world }
        return saves.indexOf(foundScore)
    }

    fun removeScore(givenScoreIndex: Int) {
        tempMuteList = saves.toMutableList()
        tempMuteList!!.removeAt(givenScoreIndex)
        saves = tempMuteList!!.toTypedArray()
    }

    fun addScore(givenLevelScore: LevelScore) {
        tempMuteList = saves.toMutableList()
        tempMuteList!!.add(givenLevelScore)
        saves = tempMuteList!!.toTypedArray()
    }

    fun getScores(): Array<LevelScore> {
        return saves
    }


}