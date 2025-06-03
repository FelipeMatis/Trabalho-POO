import java.util.Set;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Criatura pokemon1 = new Criatura("Teste 1", 2, 0,40, 8, 5, Set.of(Tipo.GRAMA));
        Criatura pokemon2 = new Criatura("Teste 2", 2, 0,40, 8, 5, Set.of(Tipo.FOGO, Tipo.GRAMA, Tipo.VOADOR));
        Criatura pokemon3 = new Criatura("Teste 3", 2, 0,40, 8, 5, Set.of(Tipo.ELETRICO));

        ArrayList<Criatura> pokemonsJogador1 = new ArrayList<>();
        pokemonsJogador1.add(pokemon1);

        ArrayList<Criatura> pokemonsJogador2 = new ArrayList<>();
        pokemonsJogador2.add(pokemon2);
        pokemonsJogador2.add(pokemon3);

        Jogador jogador1 = new Jogador("Jogador 1", pokemonsJogador1);
        Jogador jogador2 = new Jogador("Jogador 2", pokemonsJogador2);

        int indicePokemonJ1 = 0;
        int indicePokemonJ2 = 0;

        Criatura pokemonAtualJ1 = jogador1.getPokemonJogador().get(indicePokemonJ1);
        Criatura pokemonAtualJ2 = jogador2.getPokemonJogador().get(indicePokemonJ2);

        pokemon1.atacar(pokemonAtualJ2, pokemonAtualJ1.getAtaque());
        System.out.println(pokemonAtualJ2.getVida());
        System.out.println();

        
    }
}
