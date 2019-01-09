package io.github.jangalinski.axon.giftcard.projection

data class CardSummary(
    val id: String,
    val initialAmount: Int,
    val amount: Int = initialAmount
) {

  fun redeem(amount: Int) = CardSummary(
      id,
      initialAmount,
      this.amount - amount
  )

}
