package models.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/** General class for "alive" game objects (players + enemies) **/
@Serializable
open class PlayerEntity(private var name: String,
                        private var maxHealth: Double) {

    @Transient
    private var currentHealth = maxHealth

    fun setName(newName: String) {
        name = newName
    }

    fun getName(): String {
        return name
    }

    fun getMaxHealth(): Double {
        return maxHealth
    }

    fun setHealth(newHealth: Double) {
        currentHealth = newHealth
    }

    fun reduceHealth(damage: Double) {
        if (currentHealth > 0) currentHealth -= damage
    }

    fun getHealth(): Double {
        return currentHealth
    }

    override fun toString(): String {
        return "PlayerEntity name: ${this.name}\n\tmaxHealth: ${this.maxHealth}\n\tcurrentHealth: ${this.currentHealth}"
    }
}
