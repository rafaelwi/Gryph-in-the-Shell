package models.entities

open class PlayerEntity(private val name: String,
                        maxHealth: Double) {

    private var currentHealth = maxHealth;

    fun getName(): String {
        return name
    }

    fun getHealth(): Double {
        return currentHealth
    }

    fun reduceHealth(damage: Int) {
        currentHealth -= damage
    }
}