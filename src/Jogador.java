import java.util.ArrayList;

public class Jogador {
    private String nome;
    private ArrayList<Criatura> pokemonJogador;

    public Jogador(String nome, ArrayList<Criatura> pokemonJogador) {
        this.nome = nome;
        this.pokemonJogador = pokemonJogador;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Criatura> getPokemonJogador() {
        return pokemonJogador;
    }
}
