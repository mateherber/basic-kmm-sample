import Foundation
import shared

extension PokemonScreen {
    @MainActor class PokemonViewModel: ObservableObject {
        @Published private(set) var state: PokemonState = .loading
        private var pokemonRepository = DI().pokemonRepository
        
        init() {
            pokemonRepository.listPokemons(completionHandler: { pokemonResult, error in
                if (pokemonResult is PokemonListSuccess) {
                    let list = pokemonResult as! PokemonListSuccess
                    DispatchQueue.main.async {
                        self.state = PokemonState.success(pokemons: list.pokemons)
                    }
                }
                if (pokemonResult is PokemonListError) {
                    let error = pokemonResult as! PokemonListError
                    DispatchQueue.main.async {
                        self.state = PokemonState.error(message: error.message)
                    }
                }
                
            })
        }
    }
}

enum PokemonState {
    case loading
    case success(pokemons: [Pokemon])
    case error(message: String)
}
