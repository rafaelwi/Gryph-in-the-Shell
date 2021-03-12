package models

open class PlayerEntity(private val name: String,
                        maxHealth: Int) {

    private var currentHealth = maxHealth;

    fun getName(): String {
        return name
    }

    fun getHealth(): Int {
        return currentHealth
    }

    fun reduceHealth(damage: Int) {
        currentHealth -= damage
    }
}