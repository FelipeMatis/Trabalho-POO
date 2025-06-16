package itens;

import personagens.*;

import java.util.ArrayList;

/**
 * Classe Pokebola
 * @author .
 * @version 1.0
 * */
public class Pokebola {
    private String tipo;
    private Pokemon pokemonCapturado;
    private double taxaCaptura;
    private int preco;

    /**
     * Construtor da classe pokebola
     * @param tipo tipo da pokebola
     * @param taxaCaptura taxa de captura da pokebola
     * @param preco preço da pokebola
     * */
    public Pokebola(String tipo, double taxaCaptura, int preco) {
        this.tipo = tipo;
        this.taxaCaptura = taxaCaptura;
        this.pokemonCapturado = null;
        this.preco = preco;
    }

    /**
     * Método de captura do pokemon
     * @param pokemon pokemon a tentar capturar
     * @param jogadorLista lista de pokemons do jogador
     * @return true caso o pokemon seja capturado, false caso não seja capturado
     * */
    public boolean capturar(Pokemon pokemon, ArrayList<Pokemon> jogadorLista) {
        if (pokemonCapturado == null && Math.random() < taxaCaptura) {
            pokemonCapturado = pokemon;
            System.out.println(pokemon.getNome() + " foi capturado!");
            pokemon.preencherVida();
            jogadorLista.add(pokemon);
            return true;
        } else {
            System.out.println("A captura falhou!");
            return false;
        }
    }

    public boolean estaVazia() {
        return pokemonCapturado == null;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPreco() {
        return preco;
    }

    public double getTaxaCaptura() {
        return taxaCaptura;
    }

    @Override
    public String toString() {
        if (pokemonCapturado == null) {
            return tipo + " (vazia)";
        } else {
            return tipo + " (contém: " + pokemonCapturado.getNome() + ")";
        }
    }
}
