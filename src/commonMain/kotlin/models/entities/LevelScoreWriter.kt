package models.entities

import com.soywiz.klogger.Console
import com.soywiz.korge.service.storage.NativeStorage
import com.soywiz.korio.file.Vfs
import com.soywiz.korio.file.VfsFile
import com.soywiz.korio.file.VfsOpenMode
import com.soywiz.korio.file.std.applicationDataVfs
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.file.useVfs
import com.soywiz.korio.lang.FileNotFoundException
import com.soywiz.korio.lang.IOException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.LevelManager

class LevelScoreWriter(private val levelManager: LevelManager?,
                       worldInt: Int,
                       levelInt: Int) {

    private var scoreToRecord: LevelScore
    private var fileLoc: String = "savedata\\save.json"

    init {
        scoreToRecord = LevelScore(worldInt, levelInt, levelManager!!.getScore())
    }

    fun setFileLoc(newFileLoc: String) {
        fileLoc = newFileLoc
    }

    suspend fun writeScoreToFile() {
        if (!levelManager!!.getIsOngoing()) {
            writeLevelScore()
        }
    }

    suspend fun readLevelScore(): String? {
        val saveContents: String?
        val file: VfsFile = applicationDataVfs[fileLoc]

        saveContents = try {
            file.readString()
        } catch (fnfException: FileNotFoundException) {
            null
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
        return saveContents
    }

    suspend fun writeLevelScore() {
        val levelScoreContents: String = Json{isLenient = true}.encodeToString(LevelScore.serializer(), scoreToRecord)
        val originalFileArray: LevelScoreRecord
        val originalFileContent: String? = readLevelScore()
        var newFileArray: LevelScoreRecord
        var oldScoreIndex: Int? = null

        if (originalFileContent != null) {
            Console.log("found")
            originalFileArray = Json{isLenient = true}.decodeFromString<LevelScoreRecord>(originalFileContent)
            newFileArray = originalFileArray
            oldScoreIndex = newFileArray.findScoreIndex(scoreToRecord.getWorld(), scoreToRecord.getLevel())
            Console.log(levelScoreContents)
            Console.log(readLevelScore())
        } else {
            Console.log("notFound")
            newFileArray = LevelScoreRecord(arrayOf<LevelScore>())
        }

        if (oldScoreIndex != null) newFileArray!!.removeScore(oldScoreIndex)
        newFileArray.addScore(scoreToRecord)

        try {
            Console.log("writing")
            //Create/write file
            applicationDataVfs.mkdir()
            Console.log("mkdir")
            applicationDataVfs[fileLoc].openInputStream()
            Console.log("opened")
            applicationDataVfs[fileLoc].writeString(Json{isLenient = true}.encodeToString<LevelScoreRecord>(newFileArray))
            Console.log("write success")
        } catch (fnfException: FileNotFoundException) {
            Console.log("can't access fileloc")
            fnfException.printStackTrace()
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

}