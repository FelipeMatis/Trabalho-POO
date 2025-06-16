package jogo;

import exceptions.PokemonDesmaiadoException;
import itens.Pokebola;
import personagens.Jogador;
import personagens.Pokemon;
import java.util.Scanner;
import util.Relatorio;

/**
 * Classe que trata a batalha entre pokémons
 * @author .
 * @version 1.0
 * */
public class Batalha {
    /**
     * Funcionamento da batalha
     * @param pokemonJogador pokemon atual do jogador
     * @param inimigo pokemon inimigo
     * @param jogador jogador para métodos do Jogador
     * */
    public static void batalhar(Pokemon pokemonJogador, Pokemon inimigo, Jogador jogador) {
        Relatorio.registrar("Início da batalha: " + pokemonJogador.getNome() + " VS " + inimigo.getNome());

        boolean resultadoCaptura = false;
        Scanner scanner = new Scanner(System.in);

        mostrarStats(pokemonJogador, inimigo);

        boolean verificaCapturaDisponivel = false;
        while (!jogador.verificaSePerdeu() && !inimigo.verificaSePerdeu() && !resultadoCaptura) {
            try {
                pokemonJogador.executar(inimigo);
                // AQUI: Formatando a vida do inimigo
                Relatorio.registrar(pokemonJogador.getNome() + " atacou " + inimigo.getNome() + ". Vida do inimigo: " + String.format("%.2f", inimigo.getVida()));
            } catch (PokemonDesmaiadoException e) {
                System.out.println("Erro: " + e.getMessage());
                Relatorio.registrar("Erro durante ataque do jogador: " + e.getMessage());
            }

            if (inimigo.verificaSePerdeu()) {
                Relatorio.registrar(inimigo.getNome() + " foi derrotado antes do contra-ataque.");
                break;
            }

            if (inimigo.getVida() < (inimigo.getVidaTotal() * 0.30) && !verificaCapturaDisponivel) {
                boolean temPokebolaVazia = false;

                for (Pokebola p : jogador.getPokebolaJogador()) {
                    if (p.estaVazia()) {
                        temPokebolaVazia = true;
                        break;
                    }
                }

                if (!temPokebolaVazia) {
                    System.out.println("Você não tem nenhuma Pokébola vazia disponível para capturar o Pokémon!");
                    Relatorio.registrar("Tentativa de captura falhou: Sem Pokébolas vazias.");
                } else {
                    verificaCapturaDisponivel = true;
                    System.out.print(inimigo.getNome() + " está enfraquecido! Deseja tentar capturá-lo? (S/N): ");
                    String input = scanner.nextLine().trim().toUpperCase();

                    if (input.equals("S")) {
                        Relatorio.registrar("Jogador tentou capturar " + inimigo.getNome() + ".");
                        resultadoCaptura = jogador.usarPokebola(inimigo);

                        if (resultadoCaptura) {
                            System.out.println("Captura bem-sucedida! A batalha terminou.");
                            Relatorio.registrar("Captura bem-sucedida de " + inimigo.getNome() + ".");
                        } else {
                            System.out.println("A captura falhou!");
                            Relatorio.registrar("Tentativa de captura de " + inimigo.getNome() + " falhou.");
                        }
                    } else {
                        Relatorio.registrar("Jogador recusou tentar capturar " + inimigo.getNome() + ".");
                    }
                }
            }

            if (resultadoCaptura) {
                break;
            }

            try {
                inimigo.executar(pokemonJogador);
                // AQUI: Formatando a vida do jogador
                Relatorio.registrar(inimigo.getNome() + " atacou " + pokemonJogador.getNome() + ". Vida do jogador: " + String.format("%.2f", pokemonJogador.getVida()));
            } catch (PokemonDesmaiadoException e) {
                System.out.println("Erro: " + e.getMessage());
                Relatorio.registrar("Erro durante ataque do inimigo: " + e.getMessage());
            }

            if (pokemonJogador.getVida() <= 0) {
                System.out.println(pokemonJogador.getNome() + " foi derrotado!");
                Relatorio.registrar(pokemonJogador.getNome() + " foi derrotado.");

                if (jogador.trocarParaProximoPokemonVivo()) {
                    pokemonJogador = jogador.getPokemonAtivo();
                    System.out.println("Você trocou para " + pokemonJogador.getNome() + "!");
                    Relatorio.registrar("Jogador trocou para " + pokemonJogador.getNome() + ".");
                } else {
                    System.out.println("Você não tem mais Pokémons vivos!");
                    break;
                }
            }
        }

        System.out.println();

        if (jogador.verificaSePerdeu()) {
            System.out.println("Você perdeu a batalha!");
            Relatorio.registrar("Fim da batalha: Jogador perdeu.");
            Relatorio.registrar(jogador.getNome() + " perdeu a batalha, todos os Pokémons foram desmaiados.");
            Relatorio.registrar("===== FIM DO JOGO =====");
        } else if (resultadoCaptura) {
            Relatorio.registrar("----- FIM DA RODADA DE BATALHA (Pokémon capturado) -----");
        } else if (inimigo.verificaSePerdeu()) {
            System.out.println("Você derrotou " + inimigo.getNome() + "!");
            Relatorio.registrar("Fim da batalha: Jogador derrotou " + inimigo.getNome() + ".");
            pokemonJogador.ganharXp(inimigo);
            Relatorio.registrar(pokemonJogador.getNome() + " ganhou XP após derrotar " + inimigo.getNome() + ".");
            Relatorio.registrar("----- FIM DA RODADA DE BATALHA (Inimigo derrotado) -----");
        }
    }

    /**
     * Mostra os status dos pokemons que vão batalhar
     * @param p pokemon do jogador
     * @param inimigo pokemon inimigo
     * */
    public static void mostrarStats(Pokemon p, Pokemon inimigo) {
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-20s VS %-20s%n", p.getNome(), inimigo.getNome());
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-10s: %-15d VS %-15d%n", "Nível", p.getNivel(), inimigo.getNivel());
        // AQUI: Formatação da vida no mostrarStats também
        System.out.printf("%-10s: %-15.2f VS %-15.2f%n", "Vida", p.getVida(), inimigo.getVida());
        System.out.printf("%-10s: %-15.2f VS %-15.2f%n", "Ataque", p.getAtaque(), inimigo.getAtaque());
        System.out.printf("%-10s: %-15.2f VS %-15.2f%n", "Defesa", p.getDefesa(), inimigo.getDefesa());
        System.out.printf("%-10s: %-15s VS %-15s%n", "Tipos", p.getTipos().toString(), inimigo.getTipos().toString());
        System.out.println("---------------------------------------------------------------\n");
    }
}