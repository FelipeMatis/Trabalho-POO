package itens;

import personagens.Jogador;
import personagens.Pokemon;

import java.util.ArrayList;
import java.util.Scanner;

public class Loja {
    public  int mostrarMenu(Scanner scanner) {
        int escolha;

        while (true) {
            try {
                System.out.println("1 - Pokebolas\n2 - Poções\n3 - Pokemons\n4 - Sair");
                System.out.print("Digite sua escolha: ");

                escolha = scanner.nextInt();
                scanner.nextLine();

                if (escolha >= 1 && escolha <= 4) {
                    break;
                } else {
                    System.out.println("Opção inválida! Digite um número entre 1 e 3.");
                }
            } catch (Exception  e) {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
                scanner.nextLine();
            }
        }

        return escolha;
    }

    public void usaMenu(int escolha, Jogador jogador, Scanner scanner, ArrayList<Pokemon> pokemonCompra, ArrayList<Pokebola> pokebolaCompra, ArrayList<Pocao> pocoesCompra) {
        System.out.println("Saldo: " + jogador.getMoedas());
        switch (escolha) {
            case 1:
                System.out.println("Pokebolas:");
                menuPokebolas(scanner, jogador, pokebolaCompra);
                break;
            case 2:
                System.out.println("Poções:");
                menuPocoes(scanner, jogador, pocoesCompra);
                break;
            case 3:
                menuPokemons(scanner, jogador, pokemonCompra);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    public static void menuPokebolas(Scanner scanner, Jogador jogador, ArrayList<Pokebola> pokebolaCompra) {
        int indice = 0;
        while (indice >= 0 && indice < pokebolaCompra.size()) {
            Pokebola p = pokebolaCompra.get(indice);

            System.out.println("---------------------------------------------------------------");
            System.out.println("Exibindo Pokebola " + (indice + 1) + " de " + pokebolaCompra.size());
            System.out.println("Tipo: " + p.getTipo() + " Taxa de Captura: " + p.getTaxaCaptura() + "Preço: " + p.getPreco());
            System.out.println("---------------------------------------------------------------");

            System.out.println("[P] Próximo | [A] Anterior | [E] Escolher | [S] Sair");
            String opcao = scanner.nextLine().trim().toUpperCase();

            switch (opcao) {
                case "P":
                    if (indice < pokebolaCompra.size() - 1) {
                        indice++;
                    } else {
                        System.out.println("Você já está na última Pokebola.");
                    }
                    break;
                case "A":
                    if (indice > 0) {
                        indice--;
                    } else {
                        System.out.println("Você já está na primeira Pokebola.");
                    }
                    break;
                case "S":
                    System.out.println("Saindo do menu de Pokebolas.");
                    return;
                case "E":
                    if (jogador.getMoedas() < p.getPreco()) {
                        System.out.println("Moedas insuficientes!");
                        continue;
                    }
                    else {
                        System.out.println("Você escolheu a Pokebola: " + p.getTipo());
                        jogador.adicionarPokebola(p);
                        jogador.removerDinheiro(p.getPreco());
                        System.out.println("Pokebola adicionada ao seu inventário!");
                        continue;
                    }
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuPokemons(Scanner scanner, Jogador jogador, ArrayList<Pokemon> pokemonCompra) {
        int indice = 0;

        if (pokemonCompra.isEmpty()) {
            System.out.println("Você já comprou todos os pokémons disponíveis!");
        }
        while (indice >= 0 && indice < pokemonCompra.size()) {
            Pokemon p = pokemonCompra.get(indice);

            System.out.println("Exibindo Pokémon " + (indice + 1) + " de " + pokemonCompra.size());
            System.out.printf("%-10s: %-15d%n", "Nível", p.getNivel());
            System.out.printf("%-10s: %-15.2f%n", "Vida", p.getVida());
            System.out.printf("%-10s: %-15.2f%n", "Ataque", p.getAtaque());
            System.out.printf("%-10s: %-15.2f%n", "Defesa", p.getDefesa());
            System.out.printf("%-10s: %-15s%n", "Tipos", p.getTipos().toString());
            System.out.printf("%-10s: %-15d%n", "Preço", p.calcularPreco());
            System.out.println("---------------------------------------------------------------");

            System.out.println("[P] Próximo | [A] Anterior | [E] Escolher | [S] Sair");
            String opcao = scanner.nextLine().trim().toUpperCase();

            switch (opcao) {
                case "P":
                    if (indice < pokemonCompra.size() - 1) {
                        indice++;
                    } else {
                        System.out.println("Você já está no último Pokémon.");
                    }
                    break;
                case "A":
                    if (indice > 0) {
                        indice--;
                    } else {
                        System.out.println("Você já está no primeiro Pokémon.");
                    }
                    break;
                case "S":
                    System.out.println("Saindo do menu de Pokémons.");
                    return;
                case "E":
                    if (jogador.getMoedas() < p.calcularPreco()) {
                        System.out.println("Moedas insuficientes!");
                        continue;
                    }
                    else {
                        System.out.println("Você escolheu o Pokémon: " + p.getNome());
                        jogador.adicionarPokemon(p);
                        pokemonCompra.remove(p);
                        jogador.removerDinheiro(p.calcularPreco());
                        System.out.println("Pokémon adicionado ao seu time!");
                        continue;
                    }
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuPocoes(Scanner scanner, Jogador jogador, ArrayList<Pocao> pocoesCompra) {
        int indice = 0;
        while (indice >= 0 && indice < pocoesCompra.size()) {
            Pocao p = pocoesCompra.get(indice);

            System.out.println("---------------------------------------------------------------");
            System.out.println("Exibindo Poção " + (indice + 1) + " de " + pocoesCompra.size());
            System.out.println((indice+1) + " - Nome: " + p.getNome() + "\nDescrição: " + p.getDescricao());
            System.out.println("Preço: " + p.getPreco());
            System.out.println("---------------------------------------------------------------");

            System.out.println("[P] Próximo | [A] Anterior | [E] Escolher | [S] Sair");
            String opcao = scanner.nextLine().trim().toUpperCase();

            switch (opcao) {
                case "P":
                    if (indice < pocoesCompra.size() - 1) {
                        indice++;
                    } else {
                        System.out.println("Você já está na última Poção.");
                    }
                    break;
                case "A":
                    if (indice > 0) {
                        indice--;
                    } else {
                        System.out.println("Você já está na primeira Poção.");
                    }
                    break;
                case "S":
                    System.out.println("Saindo do menu de Poções.");
                    return;
                case "E":
                    if (jogador.getMoedas() < p.getPreco()) {
                        System.out.println("Moedas insuficientes!");
                        continue;
                    }
                    else {
                        System.out.println("Você escolheu a Poção: " + p.getNome());
                        jogador.adicionarPocao(p);
                        jogador.removerDinheiro(p.getPreco());
                        System.out.println("Poção adicionada ao seu inventário!");
                        continue;
                    }
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

}