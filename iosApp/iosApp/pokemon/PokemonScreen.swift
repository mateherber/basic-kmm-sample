import SwiftUI

struct PokemonScreen: View {
    @StateObject var viewModel = PokemonViewModel()
    
    var body: some View {
        VStack {
            switch viewModel.state {
            case .loading:
                ProgressView()
            case let .success(pokemons):
                ScrollView {
                    LazyVStack(alignment: .leading) {
                        ForEach(pokemons, id:\.name) { pokemon in
                            HStack {
                                AsyncImage(url: URL(string: pokemon.artwork)) { image in
                                    image.resizable()
                                } placeholder: {
                                    EmptyView()
                                }.frame(width: 100, height: 100)
                                    .padding(.leading, 20)
                                Text(pokemon.name).padding(.leading, 20)
                            }
                        }
                    }
                }
            case let .error(message):
                Text(message)
            }
        }
        .frame(maxWidth: .infinity)
    }
}

struct PokemonScreen_Previews: PreviewProvider {
    static var previews: some View {
        PokemonScreen()
    }
}
