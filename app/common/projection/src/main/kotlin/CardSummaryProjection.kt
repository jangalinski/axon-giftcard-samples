package io.github.jangalinski.axon.giftcard.projection

import io.github.jangalinski.axon.giftcard.api.dto.CardSummary
import io.github.jangalinski.axon.giftcard.event.IssuedEvt
import io.github.jangalinski.axon.giftcard.event.RedeemedEvt
import io.github.jangalinski.axon.giftcard.api.query.FindAllQuery
import io.github.jangalinski.axon.giftcard.api.query.FindOneQuery
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler


class CardSummaryProjection(
    //private val queryUpdateEmitter: QueryUpdateEmitter,
    private val cards : MutableMap<String, CardSummary> = mutableMapOf()
) {

    @EventHandler
    fun on(event: IssuedEvt) {
        println("event: $event")

        cards[event.id] = CardSummary(event.id, event.amount)

        println("projection: $cards")
    }

    @EventHandler
    fun on(event: RedeemedEvt) {
        println("event: $event")

        cards.computeIfPresent(event.id) { _, cs -> CardSummary(event.id, cs.initialAmount, cs.amount - event.amount) }

        println("projection: $cards")
    }

    @QueryHandler
    fun findOne(query: FindOneQuery) = cards[query.id]!!
//        /*

//         * Serve the subscribed queries by emitting an update. This reads as follows:
//         * - to all current subscriptions of type CountCardSummariesQuery
//         * - for which is true that the id of the gift card having been issued starts with the idStartWith string
//         *   in the query's filter
//         * - send a message that the count of queries matching this query has been changed.
//         */
//        queryUpdateEmitter.emit(CountCardSummariesQuery.class,
//                query -> event.getId().startsWith(query.getFilter().getIdStartsWith()),
//                new CountChangedUpdate());
//    }
//
//    @EventHandler
//    public void on(RedeemedEvt event) {
//        log.trace("projecting {}", event);
//        /*
//         * Update our read model by updating the existing card. This is done so that upcoming regular
//         * (non-subscription) queries get correct data.
//         */
//        CardSummary summary = entityManager.find(CardSummary.class, event.getId());
//        summary.setRemainingValue(summary.getRemainingValue() - event.getAmount());
//        /*
//         * Serve the subscribed queries by emitting an update. This reads as follows:
//         * - to all current subscriptions of type FetchCardSummariesQuery
//         * - for which is true that the id of the gift card having been redeemed starts with the idStartWith string
//         *   in the query's filter
//         * - send a message containing the new state of this gift card summary
//         */
//        queryUpdateEmitter.emit(FetchCardSummariesQuery.class,
//                query -> event.getId().startsWith(query.getFilter().getIdStartsWith()),
//                summary);
//    }
//
//    @QueryHandler
//    public List<CardSummary> handle(FetchCardSummariesQuery query) {
//        log.trace("handling {}", query);
//        TypedQuery<CardSummary> jpaQuery = entityManager.createNamedQuery("CardSummary.fetch", CardSummary.class);
//        jpaQuery.setParameter("idStartsWith", query.getFilter().getIdStartsWith());
//        jpaQuery.setFirstResult(query.getOffset());
//        jpaQuery.setMaxResults(query.getLimit());
//        return log.exit(jpaQuery.getResultList());
//    }
//
//    @QueryHandler
//    public CountCardSummariesResponse handle(CountCardSummariesQuery query) {
//        log.trace("handling {}", query);
//        TypedQuery<Long> jpaQuery = entityManager.createNamedQuery("CardSummary.count", Long.class);
//        jpaQuery.setParameter("idStartsWith", query.getFilter().getIdStartsWith());
//        return log.exit(new CountCardSummariesResponse(jpaQuery.getSingleResult().intValue(), Instant.now().toEpochMilli()));
//    }

}