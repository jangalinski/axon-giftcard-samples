package io.github.jangalinski.axon.giftcard.domain

import io.github.jangalinski.axon.giftcard.command.CancelCmd
import io.github.jangalinski.axon.giftcard.command.IssueCmd
import io.github.jangalinski.axon.giftcard.command.RedeemCmd
import io.github.jangalinski.axon.giftcard.event.CanceledEvt
import io.github.jangalinski.axon.giftcard.event.IssuedEvt
import io.github.jangalinski.axon.giftcard.event.RedeemedEvt
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.slf4j.LoggerFactory
import java.lang.invoke.MethodHandles


class GiftCard {

  @AggregateIdentifier
  private var id: String? = null
  private var remainingValue: Int = 0

  constructor() {
    log.debug("empty constructor invoked")
  }

  @CommandHandler
  constructor(cmd: IssueCmd) {
    log.debug("handling {}", cmd)
    if (cmd.amount <= 0) throw IllegalArgumentException("amount <= 0")
    apply(IssuedEvt(cmd.id, cmd.amount))
  }

  @CommandHandler
  fun handle(cmd: RedeemCmd) {
    log.debug("handling {}", cmd)
    if (cmd.amount <= 0) throw IllegalArgumentException("amount <= 0")
    if (cmd.amount > remainingValue) throw IllegalStateException("amount > remaining value")
    apply(RedeemedEvt(cmd.id, cmd.amount))
  }

  @CommandHandler
  fun handle(cmd: CancelCmd) {
    log.debug("handling {}", cmd)
    apply(CanceledEvt(cmd.id))
  }

  @EventSourcingHandler
  fun on(evt: IssuedEvt) {
    log.debug("applying {}", evt)
    id = evt.id
    remainingValue = evt.amount
    log.debug("new remaining value: {}", remainingValue)
  }

  @EventSourcingHandler
  fun on(evt: RedeemedEvt) {
    log.debug("applying {}", evt)
    remainingValue -= evt.amount
    log.debug("new remaining value: {}", remainingValue)
  }

  @EventSourcingHandler
  fun on(evt: CanceledEvt) {
    log.debug("applying {}", evt)
    remainingValue = 0
    log.debug("new remaining value: {}", remainingValue)
  }

  companion object {

    private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
  }
}