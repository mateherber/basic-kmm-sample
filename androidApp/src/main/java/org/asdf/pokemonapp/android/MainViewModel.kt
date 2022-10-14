package org.asdf.pokemonapp.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.asdf.pokemonapp.Pokemon
import org.asdf.pokemonapp.PokemonListError
import org.asdf.pokemonapp.PokemonListSuccess
import org.asdf.pokemonapp.PokemonRepository

class MainViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _state = MutableStateFlow<MainState>(MainState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            when (val result = repository.listPokemons()) {
                is PokemonListError -> _state.value = MainState.Failure(result.message)
                is PokemonListSuccess -> _state.value = MainState.Success(result.pokemons)
            }
        }
    }
}

sealed interface MainState {
    object Loading : MainState
    data class Success(val pokemons: List<Pokemon>) : MainState
    data class Failure(val message: String) : MainState
}