package models.entities

import models.entities.PlayerEntity

class Enemy(name: String, maxHealth: Double, val spriteFile: String): PlayerEntity(name, maxHealth) {

}