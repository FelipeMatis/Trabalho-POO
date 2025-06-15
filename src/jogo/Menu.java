package jogo;

import personagens.*;
import itens.*;
import exceptions.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    /**
     * Método que exibe o menu interativo
     * @param scanner usado para entrada do usuário
     * @return escolha do usuário via menu
     * */
    public static int mostrarMenu(Scanner scanner) {
        int escolha;
        while (true) {
            try {
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Batalhar");
                System.out.println("2 - Loja");
                System.out.println("3 - Meus Pokémons");
                System.out.println("4 - Minhas Pokébolas");
                System.out.println("5 - Minhas Poções");
                System.out.println("6 - Desistir");
                System.out.print("Digite sua escolha: ");

                String entrada = scanner.nextLine();
                escolha = Integer.parseInt(entrada.trim());

                if (escolha >= 1 && escolha <= 6) {
                    break;
                } else {
                    System.out.println("Opção inválida! Digite um número entre 1 e 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
            }
        }
        return escolha;
    }

    /**
     * Executa a ação do menu
     * @param escolha opção escolhida
     * @param meuPokemon pokémon atual do usuário
     * @param inimigo pokémon inimigo do usuário
     * @param scanner para entrada de dados
     * @param pokemonsCompra lista de pokémons para compra
     * @param pokebolasCompra lista de pokébolas para compra
     * @param pocoesCompra lista de poções para compra
     * */
    public static void usarMenu(int escolha, Pokemon meuPokemon, Pokemon inimigo, Jogador jogador, Scanner scanner,
                                ArrayList<Pokemon> pokemonsCompra, ArrayList<Pokebola> pokebolasCompra, ArrayList<Pocao> pocoesCompra) {

        switch (escolha) {
            case 1:
                System.out.println("Você escolheu: Batalhar\n");
                Batalha.batalhar(meuPokemon, inimigo, jogador);
                break;
            case 2:
                System.out.println("Você escolheu: Loja\n");
                Loja loja = new Loja();
                int escolhaLoja = -1;
                while (escolhaLoja != 4) {
                    escolhaLoja = loja.mostrarMenu(scanner);
                    loja.usaMenu(escolhaLoja, jogador, scanner, pokemonsCompra, pokebolasCompra, pocoesCompra);
                }
                break;
            case 3:
                System.out.println("Você escolheu: Meus Pokémons");
                jogador.mostrarPokemonsJogador();
                System.out.println();
                break;
            case 4:
                if (jogador.getPokebolaJogador().isEmpty()) {
                    System.out.println("Você não possui pokebolas\n");
                } else {
                    System.out.println(jogador.getPokebolaJogador());
                    System.out.println();
                }
                break;
            case 5:
                System.out.println("Você escolheu: Minhas Poções\n");
                jogador.mostrarPocoesJogador();
                System.out.println();
                int escolhaPocao = -1;

                while (true) {
                    try {
                        System.out.print("Digite o número da poção que deseja escolher (0 para sair): ");

                        if (!scanner.hasNextInt()) {
                            scanner.nextLine();
                            throw new EscolhaInvalidaException("Entrada inválida. Digite um número.");
                        }

                        escolhaPocao = scanner.nextInt();
                        scanner.nextLine();

                        if (escolhaPocao == 0) {
                            System.out.println("Saindo da escolha de poções.");
                            break;
                        }

                        if (escolhaPocao < 1 || escolhaPocao > jogador.getPocaoJogador().size()) {
                            throw new EscolhaInvalidaException("Número fora do intervalo. Escolha de 1 a " + jogador.getPocaoJogador().size());
                        }

                        jogador.usarPocao(escolhaPocao - 1, scanner);
                        break;

                    } catch (EscolhaInvalidaException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                break;
            case 6:
                System.out.println("Que pena! Fechando o programa...");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
}
