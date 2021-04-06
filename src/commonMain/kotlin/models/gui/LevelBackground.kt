package models.gui

import kotlinx.serialization.Serializable

@Serializable
class LevelBackground(private val name: String, private var pngFileLoc: String) {

    fun setPngFileLoc(newSpriteFileLoc: String) {
        pngFileLoc = newSpriteFileLoc
    }

    fun getPngFileLoc(): String {
        return pngFileLoc
    }

    override fun toString(): String {
        return "name: ${this.name}\n\tbackgroundFile: ${this.pngFileLoc}\n";
    }
}