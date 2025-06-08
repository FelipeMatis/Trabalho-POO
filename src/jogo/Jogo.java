package jogo;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import exceptions.*;
import personagens.*;
import itens.*;

public class Jogo {

    private ArrayList<Pokemon> opcoes;
    private ArrayList<Pokemon> pokemons;
    private ArrayList<Pokemon> pokemonsJogador;
    private ArrayList<Pokebola> pokebolasJogador;
    private Scanner scanner;

    public Jogo() {
        scanner = new Scanner(System.in);
        opcoes = new ArrayList<>();
        pokemons = new ArrayList<>();
        pokemonsJogador = new ArrayList<>();
        pokebolasJogador = new ArrayList<>();
        inicializarPokemons();
    }

    public void iniciar() {
        String nomeJogador = obterNomeJogador();
        Jogador jogador = new Jogador(nomeJogador, pokemonsJogador, pokebolasJogador);

        System.out.println("Escolha seu Pokémon:");

        for (int i = 0; i < opcoes.size(); i++) {
            Pokemon p = opcoes.get(i);
            System.out.println((i + 1) + " - " + p.getNome());
            System.out.println("  Nível: " + p.getNivel());
            System.out.println("  Vida: " + p.getVida());
            System.out.println("  Ataque: " + p.getAtaque());
            System.out.println("  Defesa: " + p.getDefesa());
            System.out.println("  Tipos: " + p.getTipos());
            System.out.println();
        }

        int escolha = escolherPokemon(); // já está correto
        pokemonsJogador.add(opcoes.get(escolha - 1));
        int indicePJogador = 0;

        System.out.println("\nVocê escolheu: " + pokemonsJogador.get(indicePJogador).getNome() + "!\n");

        int fase = 1;
        int indicePInimigo = 0;

        while (!jogador.verificaSePerdeu()) {
            if (indicePInimigo >= pokemons.size()) {
                System.out.println("Parabéns! Você derrotou todos os Pokémons inimigos!");
                break;
            }

            System.out.println("Fase " + fase);

            if (pokemonsJogador.size() > 1) {
                System.out.println("Escolha um Pokémon para iniciar a batalha:");

                for (int i = 0; i < pokemonsJogador.size(); i++) {
                    Pokemon p = pokemonsJogador.get(i);
                    System.out.println((i + 1) + " - " + p.getNome() + " (Vida: " + p.getVida() + ")");
                }

                boolean escolhaValida = false;

                while (!escolhaValida) {
                    try {
                        System.out.print("Digite o número do Pokémon: ");

                        if (!scanner.hasNextInt()) {
                            scanner.nextLine(); // limpar linha inválida
                            throw new EscolhaInvalidaException("Entrada inválida. Digite um número.");
                        }

                        int escolhaPokemon = scanner.nextInt();
                        scanner.nextLine(); // ← ESSENCIAL: limpa a quebra de linha

                        if (escolhaPokemon < 1 || escolhaPokemon > pokemonsJogador.size()) {
                            throw new EscolhaInvalidaException("Escolha fora do intervalo.");
                        }

                        if (pokemonsJogador.get(escolhaPokemon - 1).getVida() <= 0) {
                            System.out.println("Este Pokémon está sem vida! Escolha outro.");
                        } else {
                            indicePJogador = escolhaPokemon - 1;
                            escolhaValida = true;
                        }

                    } catch (EscolhaInvalidaException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
            }

            Pokemon inimigo = pokemons.get(indicePInimigo);
            Pokemon meuPokemon = pokemonsJogador.get(indicePJogador);

            Batalha.batalhar(meuPokemon, inimigo, jogador);

            indicePInimigo++;
            fase++;
        }

        System.out.println("Fim do jogo!");
    }

    private void inicializarPokemons() {
        // opcoes para escolha inicio
        opcoes.add(new Pokemon("Pikachu", 1, 0, 35, 35, 55, 40, Set.of(Tipo.ELETRICO)));
        opcoes.add(new Pokemon("Charmander", 1, 0, 39, 39,52, 43, Set.of(Tipo.FOGO)));
        opcoes.add(new Pokemon("Squirtle", 1, 0, 44, 44,48, 65, Set.of(Tipo.AGUA)));

        // inimigos
        pokemons.add(new Pokemon("Sunkern", 1, 0, 30, 30, 30, 30, Set.of(Tipo.GRAMA)));
        pokemons.add(new Pokemon("Magikarp", 1, 0, 20, 20,10, 80, Set.of(Tipo.AGUA)));
        pokemons.add(new Pokemon("Happiny", 1, 0, 100, 100, 5, 30, Set.of(Tipo.NORMAL)));
        pokemons.add(new Pokemon("Growlithe", 1, 0, 55, 55, 70, 60, Set.of(Tipo.FOGO)));
        pokemons.add(new Pokemon("Murkrow", 1, 0, 60, 60,85, 91, Set.of(Tipo.NOTURNO, Tipo.VOADOR)));
        pokemons.add(new Pokemon("Floatzel", 1, 0, 85, 85,105, 115, Set.of(Tipo.AGUA)));
        pokemons.add(new Pokemon("Lucario", 1, 0, 70, 70,110, 90, Set.of(Tipo.LUTADOR, Tipo.METAL)));
        pokemons.add(new Pokemon("Garchomp", 1, 0, 108, 108,130, 102, Set.of(Tipo.DRAGAO, Tipo.TERRA)));
        pokemons.add(new Pokemon("Salamence", 1, 0, 95, 95,135, 100, Set.of(Tipo.DRAGAO, Tipo.VOADOR)));
        pokemons.add(new Pokemon("Mewtwo", 1, 0, 106, 106,110, 130, Set.of(Tipo.PSIQUICO)));

        // pokebola de inicio
        pokebolasJogador.add(new Pokebola("Master Ball", 1.0));
        pokebolasJogador.add(new Pokebola("Pokebola", 0.3));
    }

    private String obterNomeJogador() {
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

    private int escolherPokemon() {
        int escolha = 0;

        while (escolha < 1 || escolha > opcoes.size()) {
            try {
                System.out.print("Digite o número do Pokémon que deseja escolher: ");

                if (!scanner.hasNextInt()) {
                    throw new EscolhaInvalidaException("Entrada inválida. Digite um número.");
                }

                escolha = scanner.nextInt();
                scanner.nextLine();

                if (escolha < 1 || escolha > opcoes.size()) {
                    throw new EscolhaInvalidaException("Número fora do intervalo. Escolha de 1 a " + opcoes.size());
                }

            } catch (EscolhaInvalidaException e) {
                System.out.println("Erro: " + e.getMessage());
                scanner.nextLine();
            }
        }

        return escolha;
    }
}
