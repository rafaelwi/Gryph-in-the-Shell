package models.entities

import models.entities.PlayerEntity

class Enemy(name: String, maxHealth: Double, spriteFileLoc: String): PlayerEntity(name, maxHealth) {

    private var spriteFileLoc = spriteFileLoc

    fun setSpriteFileLoc (newSpriteFileLoc: String) {
        spriteFileLoc = newSpriteFileLoc
    }

    fun getSpriteFileLoc (): String {
        return spriteFileLoc
    }


}