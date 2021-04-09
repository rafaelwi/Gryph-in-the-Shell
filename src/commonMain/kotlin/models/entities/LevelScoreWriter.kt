package models.entities

import com.soywiz.klogger.Console
import com.soywiz.korio.file.std.localVfs
import com.soywiz.korio.lang.FileNotFoundException
import com.soywiz.korio.lang.IOException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.LevelManager

class LevelScoreWriter(private val levelManager: LevelManager?, worldInt: Int, levelInt: Int) {
    private var scoreToRecord: LevelScore = LevelScore(worldInt, levelInt, levelManager!!.getScore())
    private val filename = "save.json"
    private val filepath : String = "data\\data\\cis4030.gis.gis"

    suspend fun writeScoreToFile() {
        if (!levelManager!!.getIsOngoing()) {
            writeLevelScore()
        }
    }

    private suspend fun writeLevelScore() {
        // Check if save file exists, if so then check if score is better or worse. If score is worse, overwrite
        if (localVfs(filepath)[filename].exists()) {
            // Read old save data file
            val rawSaveData = localVfs(filepath)[filename].readString()
            val saveData = Json.decodeFromString<LevelScoreRecord>(rawSaveData)

            // Get the current level's save data
            val world = scoreToRecord.getWorld()
            val level = scoreToRecord.getLevel()
            val oldRecord = saveData.getScore(world, level)

            // Compare scores
            if (oldRecord.getScore() > scoreToRecord.getScore()) {
                saveData.replaceScore(scoreToRecord)
                writeData(Json.encodeToString(saveData))
            }
        } else {
            // If file doesn't exist, write to new file
            val newSave = LevelScoreRecord(arrayOf(scoreToRecord))
            writeData(Json.encodeToString(newSave))
        }
    }

    private suspend fun writeData(data : String) {
        try {
            localVfs(filepath)[filename].writeString(data)
        } catch (fnfException: FileNotFoundException) {
            Console.log("Can't access $filename")
            fnfException.printStackTrace()
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}
