package org.asdf.pokemonapp

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache

object DI {
    val pokemonRepository = PokemonRepository(
        ApolloClient.Builder()
            .serverUrl("https://graphql-pokeapi.vercel.app/api/graphql")
            .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
            .build()
    )
}