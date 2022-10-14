package org.asdf.pokemonapp

import com.apollographql.apollo3.ApolloClient

class PokemonRepository(private val apolloClient: ApolloClient) {
    suspend fun listPokemons(): ListPokemonResult {
        return try {
            val data = apolloClient.query(ListPokemonsQuery()).execute().dataAssertNoErrors
            PokemonListSuccess(data.pokemons?.resultsFilterNotNull()?.map {
                Pokemon(it.name ?: "", it.artwork ?: "")
            } ?: emptyList())
        } catch (e: Throwable) {
            PokemonListError(e.message ?: "")
        }
    }
}

sealed interface ListPokemonResult
data class PokemonListSuccess(val pokemons: List<Pokemon>) : ListPokemonResult
data class PokemonListError(val message: String) : ListPokemonResult

data class Pokemon(val name: String, val artwork: String)