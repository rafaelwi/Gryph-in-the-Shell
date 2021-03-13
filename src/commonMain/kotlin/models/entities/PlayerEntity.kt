package models.entities

open class PlayerEntity(private val name: String,
                        private val maxHealth: Double) {

    private var currentHealth = maxHealth;

    fun getName(): String {
        return name
    }

    fun getMaxHealth(): Double {
        return maxHealth
    }

    fun getHealth(): Double {
        return currentHealth
    }

    fun reduceHealth(damage: Int) {
        if (currentHealth > 0) currentHealth -= damage
    }
}