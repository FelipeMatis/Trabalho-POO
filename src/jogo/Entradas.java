package jogo;

import exceptions.*;
import java.util.Scanner;

/**
 * Classe que trata as entradas solicitadas ao usuário
 * @author .
 * @version 1.0
 * */
public class Entradas {

    private final Scanner scanner;

    /**
     * Construtor da classe Entradas
     * */
    public Entradas(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Método para ler o nome do Jogador
     * @return nome do Jogador
     * @throws NomeInvalidoException caso nome vazio ou inválido
     * */
    public String obterNomeJogador() {
        String nomeJogador = null;

        while (nomeJogador == null || nomeJogador.isBlank()) {
            try {
                System.out.print("Digite seu nome: ");
                nomeJogador = scanner.nextLine().trim();

                if (nomeJogador.isEmpty()) {
                    throw new NomeInvalidoException("O nome não pode estar vazio.");
                }

                if (!nomeJogador.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                    throw new NomeInvalidoException("O nome deve conter apenas letras e espaços.");
                }

            } catch (NomeInvalidoException e) {
                System.out.println("Erro: " + e.getMessage());
                nomeJogador = null;
            }
        }
        return nomeJogador;
    }

    /**
     * Método para ler a escolha do pokémon inicial
     * @return número do pokémon escolhido
     * @throws EscolhaInvalidaException caso entrada inválida
     * */
    public int escolherPokemon(int limite) {
        int escolha = 0;

        while (escolha < 1 || escolha > limite) {
            try {
                System.out.print("Digite o número do Pokémon que deseja escolher: ");

                if (!scanner.hasNextInt()) {
                    scanner.nextLine();
                    throw new EscolhaInvalidaException("Entrada inválida. Digite um número.");
                }

                escolha = scanner.nextInt();
                scanner.nextLine();

                if (escolha < 1 || escolha > limite) {
                    throw new EscolhaInvalidaException("Número fora do intervalo. Escolha de 1 a " + limite);
                }

            } catch (EscolhaInvalidaException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        return escolha;
    }
}
