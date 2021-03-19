package models.entities

import models.entities.PlayerEntity

class Enemy(name: String, maxHealth: Double,
            private var spriteFileLoc: String,
            private var spriteWidth: Int,
            private var spriteHeight: Int,
            private var spriteMapCols: Int,
            private var spriteMapRows: Int ): PlayerEntity(name, maxHealth) {

    fun setSpriteFileLoc(newSpriteFileLoc: String) {
        spriteFileLoc = newSpriteFileLoc
    }

    fun getSpriteFileLoc(): String {
        return spriteFileLoc
    }

    fun setSpriteWidth(newSpriteWidth: Int) {
        spriteWidth = newSpriteWidth
    }

    fun getSpriteWidth(): Int {
        return spriteWidth
    }

    fun setSpriteHeight(newSpriteHeight: Int) {
        spriteHeight = newSpriteHeight
    }

    fun getSpriteHeight(): Int {
        return spriteHeight
    }

    fun setSpriteMapCols(newSpriteMapCols: Int) {
        spriteMapCols = newSpriteMapCols
    }

    fun getSpriteMapCols(): Int {
        return spriteMapCols
    }

    fun setSpriteMapRows(newSpriteMapRows: Int) {
        spriteMapRows = newSpriteMapRows
    }

    fun getSpriteMapRows(): Int {
        return spriteMapRows
    }
}