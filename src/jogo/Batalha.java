package jogo;

import itens.Pokebola;
import personagens.Jogador;
import personagens.Pokemon;
import java.util.Scanner;

public class Batalha {

    public static void batalhar(Pokemon pokemonJogador, Pokemon inimigo, Jogador jogador) {
        boolean resultado = false;
        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.println(pokemonJogador.getNome() + " vs " + inimigo.getNome());
        System.out.println();

        mostrarStats(pokemonJogador);
        mostrarStats(inimigo);

        boolean verifica = false; // só pode tentar 1 vez
        while (!jogador.verificaSePerdeu() && !inimigo.verificaSePerdeu()) {
            pokemonJogador.atacar(inimigo);

            if (inimigo.verificaSePerdeu()) break;

            if (inimigo.getVida() < (inimigo.getVidaTotal() * 0.30) && !verifica) {
                boolean temPokebolaVazia = false;

                for (Pokebola p : jogador.getPokebolaJogador()) {
                    if (p.estaVazia()) {
                        temPokebolaVazia = true;
                        break;
                    }
                }

                if (!temPokebolaVazia) {
                    System.out.println("Você não tem nenhuma Pokébola vazia disponível para capturar o Pokémon!");
                } else {
                    verifica = true;
                    System.out.print(inimigo.getNome() + " está enfraquecido! Deseja tentar capturá-lo? (S/N): ");
                    String input = scanner.nextLine().trim().toUpperCase();

                    if (input.equals("S")) {
                        resultado = jogador.usarPokebola(inimigo);

                        if (resultado) {
                            System.out.println("Captura bem-sucedida! A batalha terminou.");
                            break;
                        }
                    }
                }

            }

            inimigo.atacar(pokemonJogador);

            if (pokemonJogador.getVida() <= 0) {
                System.out.println(pokemonJogador.getNome() + " foi derrotado!");

                if (jogador.trocarParaProximoPokemonVivo()) {
                    pokemonJogador = jogador.getPokemonAtivo();
                    System.out.println("Você trocou para " + pokemonJogador.getNome() + "!");
                    mostrarStats(pokemonJogador);
                } else {
                    System.out.println("Você não tem mais Pokémons vivos!");
                    break;
                }
            }
        }

        if (jogador.verificaSePerdeu()) {
            System.out.println("Você perdeu a batalha!");
        } else if (!resultado && inimigo.verificaSePerdeu()) {
            System.out.println("Você derrotou " + inimigo.getNome() + "!");
            pokemonJogador.ganharXp(inimigo);
        }

        System.out.println();
    }

    public static void mostrarStats(Pokemon p) {
        System.out.println("Nome: " + p.getNome());
        System.out.println("Nível: " + p.getNivel());
        System.out.println("Vida: " + p.getVida());
        System.out.println("Ataque: " + p.getAtaque());
        System.out.println("Defesa: " + p.getDefesa());
        System.out.println("Tipos: " + p.getTipos());
        System.out.println();
    }
}
