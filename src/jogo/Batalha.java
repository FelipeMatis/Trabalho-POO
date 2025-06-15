package jogo;

import exceptions.PokemonDesmaiadoException;
import itens.Pokebola;
import personagens.Jogador;
import personagens.Pokemon;
import java.util.Scanner;

public class Batalha {

    public static void batalhar(Pokemon pokemonJogador, Pokemon inimigo, Jogador jogador) {
        boolean resultado = false;
        Scanner scanner = new Scanner(System.in);

        mostrarStats(pokemonJogador, inimigo);

        boolean verifica = false; // só pode tentar 1 vez
        while (!jogador.verificaSePerdeu() && !inimigo.verificaSePerdeu()) {
            try {
                pokemonJogador.executar(inimigo);
            } catch (PokemonDesmaiadoException e) {
                System.out.println("Erro: " + e.getMessage());
            }

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

            try {
                inimigo.executar(pokemonJogador);
            } catch (PokemonDesmaiadoException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            if (pokemonJogador.getVida() <= 0) {
                System.out.println(pokemonJogador.getNome() + " foi derrotado!");

                if (jogador.trocarParaProximoPokemonVivo()) {
                    pokemonJogador = jogador.getPokemonAtivo();
                    System.out.println("Você trocou para " + pokemonJogador.getNome() + "!");
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

    public static void mostrarStats(Pokemon p, Pokemon inimigo) {
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-20s VS %-20s%n", p.getNome(), inimigo.getNome());
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-10s: %-15d VS %-15d%n", "Nível", p.getNivel(), inimigo.getNivel());
        System.out.printf("%-10s: %-15.2f VS %-15.2f%n", "Vida", p.getVida(), inimigo.getVida());
        System.out.printf("%-10s: %-15.2f VS %-15.2f%n", "Ataque", p.getAtaque(), inimigo.getAtaque());
        System.out.printf("%-10s: %-15.2f VS %-15.2f%n", "Defesa", p.getDefesa(), inimigo.getDefesa());
        System.out.printf("%-10s: %-15s VS %-15s%n", "Tipos", p.getTipos().toString(), inimigo.getTipos().toString());
        System.out.println("---------------------------------------------------------------\n");
    }
}
