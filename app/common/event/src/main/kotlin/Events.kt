package io.github.jangalinski.axon.giftcard.event

import org.axonframework.serialization.Revision

sealed class Event(open val id:String)

@Revision("1")
data class IssuedEvt(
    override val id: String,
    val amount: Int
) : Event(id)

@Revision("1")
data class RedeemedEvt(
    override val id: String,
    val amount: Int
): Event(id)

@Revision("1")
data class CanceledEvt(
    override val id: String
): Event(id)
