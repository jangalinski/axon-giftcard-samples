package io.github.jangalinski.axon.giftcard.query

sealed class Query

object FindAllQuery : Query()

data class FindOneQuery(val id: String) : Query()
