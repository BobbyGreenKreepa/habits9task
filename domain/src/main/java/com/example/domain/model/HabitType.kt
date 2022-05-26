package com.example.domain.model

enum class HabitType(val value: Int) {

    USEFUL(0),
    HARMFUL(1);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}