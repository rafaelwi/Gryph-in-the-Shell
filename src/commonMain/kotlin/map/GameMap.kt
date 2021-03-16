package map

import com.soywiz.korge.input.mouse
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.scene.SceneContainer
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import scenes.BattleScene

class GameMap (
        val world: Int,
        var backgroundPath: String?,
        var levels: List<Placemarker>
) {
    override fun toString(): String {
        var levelStrings = ""
        for (pm in this.levels) levelStrings += pm.toString() + "\n"
        return "World ${this.world}\nLevels:\n$levelStrings"
    }
}
