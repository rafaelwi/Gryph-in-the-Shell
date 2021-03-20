package models.gui

import kotlinx.serialization.Serializable

@Serializable
class LevelBackground(val name: String, val spriteFile: String) {


    override fun toString(): String {
        return "name: ${this.name}\n\tspriteFile: ${this.spriteFile}\n";
    }
}