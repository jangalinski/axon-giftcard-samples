package io.github.jangalinski.axon.giftcard.app01

import io.github.jangalinski.axon.giftcard.api.command.IssueCmd
import io.github.jangalinski.axon.giftcard.api.command.RedeemCmd
//import io.github.jangalinski.axon.giftcard.eve
import io.github.jangalinski.axon.giftcard.api.dto.CardSummary
import io.github.jangalinski.axon.giftcard.domain.GiftCardAggregate
import io.github.jangalinski.axon.giftcard.projection.CardSummaryProjection
import io.github.jangalinski.axon.giftcard.api.query.FindOneQuery
import org.axonframework.config.AggregateConfigurer
import org.axonframework.config.DefaultConfigurer
import org.axonframework.eventhandling.GlobalSequenceTrackingToken
import org.axonframework.eventhandling.TrackedEventMessage
import org.axonframework.eventhandling.TrackingToken
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import java.util.*
import kotlin.streams.asSequence


val projection = CardSummaryProjection()
val eventStore = EmbeddedInMemoryEventStore()

//    .registerModule(new EventHandlingConfiguration()
//        .registerEventHandler(c -> projection))
//.registerQueryHandler(c -> projection)

val configuration = DefaultConfigurer.defaultConfiguration()
    .eventProcessing { epc -> epc.registerEventHandler { _ -> projection } }
    .configureAggregate(
        AggregateConfigurer.defaultConfiguration(GiftCardAggregate::class.java)
    )
    .registerQueryHandler { _ -> projection }
    .configureEventStore { eventStore }
    .buildConfiguration()

val commandGateway by lazy {
  configuration.commandGateway()
}

val queryGateway by lazy {
  configuration.queryGateway()
}


fun main() {
  println("""

    Running App01

  """.trimIndent())

  configuration.start()

  with(IssueCmd("1", 100)) {
    println("send: $this")
    commandGateway.send<IssueCmd>(this)
  }


  println("events: ${eventStore.getEvents()}")


  with(RedeemCmd("1", 50)) {
    println("send: $this")
    commandGateway.send<RedeemCmd>(this)
  }

  println("events: ${eventStore.getEvents()}")


  val result = queryGateway.query(FindOneQuery("1"), CardSummary::class.java)

  println("query: ${result.join()}")
  configuration.shutdown()
}

class EmbeddedInMemoryEventStore(
    storageEngine: InMemoryEventStorageEngine = InMemoryEventStorageEngine()
) : EmbeddedEventStore(EmbeddedEventStore
    .builder()
    .storageEngine(storageEngine)
) {

  private val events: NavigableMap<TrackingToken, TrackedEventMessage<*>> by lazy {
    val eventsField = InMemoryEventStorageEngine::class.java.getDeclaredField("events")
    eventsField.isAccessible = true
    eventsField.get(storageEngine()) as NavigableMap<TrackingToken, TrackedEventMessage<*>>
  }

  fun getEvents(): String = events.let {
    it.navigableKeySet().stream()
        .map { events[it] }
        .map { m ->
          String.format("\n\t[%03d] - %s",
              (m!!.trackingToken()!! as GlobalSequenceTrackingToken).globalIndex,
              m.payload
          )
        }.asSequence().joinToString()
  }
}