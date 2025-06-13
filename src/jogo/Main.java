package jogo;

import personagens.Pokemon;
import personagens.Tipo;
import exceptions.PokemonDesmaiadoException;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Criando tipos para os Pokémons
        Set<Tipo> tiposFogo = new HashSet<>();
        tiposFogo.add(Tipo.FOGO);

        Set<Tipo> tiposAgua = new HashSet<>();
        tiposAgua.add(Tipo.AGUA);

        // Criando dois Pokémons
        Pokemon charmander = new Pokemon("Charmander", 5, 0, 39, 39, 52, 43, tiposFogo);
        Pokemon squirtle = new Pokemon("Squirtle", 5, 0, 44, 44, 48, 65, tiposAgua);

        // Exibindo status iniciais
        System.out.println("\nStatus iniciais:");
        System.out.println(charmander);
        System.out.println(squirtle);

        // Charmander ataca Squirtle
        Acao atacante = charmander;
        try {
            atacante.executar(squirtle);
        } catch (PokemonDesmaiadoException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // Squirtle ataca Charmander
        Acao defensor = squirtle;
        try {
            defensor.executar(charmander);
        } catch (PokemonDesmaiadoException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // Exibindo status finais
        System.out.println("\nApós a batalha:");
        System.out.println(charmander);
        System.out.println(squirtle);
    }
}
