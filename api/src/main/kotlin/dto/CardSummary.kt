package io.github.jangalinski.axon.giftcard.api.dto

data class CardSummary(
    val id: String,
    val initialAmount: Int,
    val amount: Int = initialAmount
)
