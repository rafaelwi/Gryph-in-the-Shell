package models.entities

open class PlayerEntity(name: String,
                        maxHealth: Double) {

    private var name = name
    private var maxHealth = maxHealth
    private var currentHealth = maxHealth;

    fun setName(newName: String) {
        name = newName
    }

    fun getName(): String {
        return name
    }

    fun setMaxHealth(newMaxHealth: Double) {
        maxHealth = newMaxHealth
    }

    fun getMaxHealth(): Double {
        return maxHealth
    }

    fun reduceHealth(damage: Int) {
        if (currentHealth > 0) currentHealth -= damage
    }

    fun getHealth(): Double {
        return currentHealth
    }

    override fun toString(): String {
        return "PlayerEntity name: ${this.name}\n\tmaxHealth: ${this.maxHealth}\n\tcurrentHealth: ${this.currentHealth}"
    }
}