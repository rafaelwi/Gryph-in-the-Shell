package models.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/** Array of scores, one array per world **/
@Serializable
class LevelScoreRecord(private var saves: Array<LevelScore>) {
    @Transient
    var tempMuteList: MutableList<LevelScore>? = null

    /** Finds the array index of score **/
    private fun findScoreIndex(world: Int, level: Int): Int {
        val foundScore = saves.find { it.getLevel() == level && it.getWorld() == world }
        return saves.indexOf(foundScore)
    }

    fun getScore(world : Int, level : Int) : LevelScore {
        return saves[findScoreIndex(world, level)]
    }

    private fun removeScore(givenScoreIndex: Int) {
        tempMuteList = saves.toMutableList()
        tempMuteList!!.removeAt(givenScoreIndex)
        saves = tempMuteList!!.toTypedArray()
    }

    /** Replace old score with new one **/
    fun replaceScore(ls: LevelScore) {
        val i = findScoreIndex(ls.getWorld(), ls.getLevel())
        removeScore(i)
        addScore(ls)
    }

    private fun addScore(givenLevelScore: LevelScore) {
        tempMuteList = saves.toMutableList()
        tempMuteList!!.add(givenLevelScore)
        saves = tempMuteList!!.toTypedArray()
    }

    override fun toString(): String {
        var s = ""
        for (save in saves) s += save.toString() + "\n"
        return s
    }
}
