package com.louisgautier.baseui

enum class AdaptiveLayoutOrder {
    NATURAL, REVERSED;


    companion object {
        fun <T> List<T>.sort(order: AdaptiveLayoutOrder) = when (order) {
            REVERSED -> reversed()
            NATURAL -> this
        }
    }
}