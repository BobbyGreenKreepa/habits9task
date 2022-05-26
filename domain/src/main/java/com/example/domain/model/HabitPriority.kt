package com.example.domain.model

enum class HabitPriority(val value: Int) {

    HIGH(2),
    MEDIUM(1),
    LOW(0);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}