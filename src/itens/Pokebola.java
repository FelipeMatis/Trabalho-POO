package itens;

import personagens.*;

import java.util.ArrayList;

public class Pokebola {
    private String tipo;
    private Pokemon pokemonCapturado;
    private double taxaCaptura;

    public Pokebola(String tipo, double taxaCaptura) {
        this.tipo = tipo;
        this.taxaCaptura = taxaCaptura;
        this.pokemonCapturado = null;
    }

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

    public void liberar(Jogador jogador) {
        if (pokemonCapturado != null) {
            System.out.println(pokemonCapturado.getNome() + " foi liberado!");
            jogador.getPokemonsJogador().remove(pokemonCapturado);
            pokemonCapturado = null;
        }
    }

    public boolean estaVazia() {
        return pokemonCapturado == null;
    }

    public String getTipo() {
        return tipo;
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
