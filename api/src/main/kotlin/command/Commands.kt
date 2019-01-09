package io.github.jangalinski.axon.giftcard.api.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.axonframework.serialization.Revision

sealed class Command(@TargetAggregateIdentifier open val id: String)

@Revision("1")
data class IssueCmd(
    override val id: String,
    val amount: Int
) : Command(id)

@Revision("1")
data class RedeemCmd(
    override val id: String,
    val amount: Int
) : Command(id)

@Revision("1")
data class CancelCmd(
    override val id: String
) : Command(id)
