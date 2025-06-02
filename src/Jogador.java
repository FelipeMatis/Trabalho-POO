import java.util.ArrayList;

public class Jogador {
    private String nome;
    private ArrayList<Pokemon> pokemonJogador;

    public Jogador(String nome, ArrayList<Pokemon> pokemonJogador) {
        this.nome = nome;
        this.pokemonJogador = pokemonJogador;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Pokemon> getPokemonJogador() {
        return pokemonJogador;
    }
}
