package models.entities

import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.lang.IOException
import kotlinx.serialization.Serializable

@Serializable
class LevelScore(private var world: Int,
                 private var level: Int,
                 private var time: Double) {

    fun setScore(newTime: Double) {
        time = newTime
    }

    fun getScore(): Double {
        return time
    }

    fun getLevel(): Int {
        return level
    }

    fun getWorld(): Int {
        return world
    }

    suspend fun writeLevelScore(fileloc : String) {
        val levelScoreContents : String = this.toString()

        try {
            resourcesVfs[fileloc].writeString(levelScoreContents)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null.toString()
        }
    }

}