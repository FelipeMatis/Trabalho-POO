package jogo;

import java.util.*;
import personagens.*;
import itens.*;
import exceptions.*;

/**
 * Classe principal do código
 * Controla o funcionamento do jogo
 * escolha de pokémons, batalhas, loja e inventário
 * @author pedro
 * @version 1.0
 * */
public class Jogo {

    private final ArrayList<Pokemon> opcoes;
    private final ArrayList<Pokemon> pokemons;
    private final ArrayList<Pokemon> pokemonsJogador;
    private final ArrayList<Pokebola> pokebolasJogador;
    private final ArrayList<Pocao> pocoesJogador;
    private final ArrayList<Pokebola> pokebolasCompra;
    private final ArrayList<Pocao> pocoesCompra;
    private final ArrayList<Pokemon> pokemonsCompra;
    private final Scanner scanner;

    /**
     * Construtor da classe jogo, inicializa as listas e o scanner
     * */
    public Jogo() {
        scanner = new Scanner(System.in);
        opcoes = new ArrayList<>();
        pokemons = new ArrayList<>();
        pokemonsJogador = new ArrayList<>();
        pokebolasJogador = new ArrayList<>();
        pocoesJogador = new ArrayList<>();
        pokebolasCompra = new ArrayList<>();
        pocoesCompra = new ArrayList<>();
        pokemonsCompra = new ArrayList<>();
        inicializarPokemons();
    }

    /**
     * Inicia o jogo controlando todo o funcionamento do código
     * escolha de pokémon
     * loja
     * */
    public void iniciar() {
        Entradas entrada = new Entradas(scanner);
        String nomeJogador = entrada.obterNomeJogador();
        Jogador jogador = new Jogador(nomeJogador, pokemonsJogador, pokebolasJogador, pocoesJogador);

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

        int escolha = entrada.escolherPokemon(opcoes.size());
        pokemonsJogador.add(opcoes.get(escolha - 1));
        int indicePJogador = 0;

        System.out.println("\nVocê escolheu: " + pokemonsJogador.get(indicePJogador).getNome() + "!\n");

        int fase = 1;
        int indicePInimigo = 0;

        while (!jogador.verificaSePerdeu() || ((indicePInimigo+1) == pokemons.size())) {
            if (indicePInimigo >= pokemons.size()) {
                System.out.println("Parabéns! Você derrotou todos os Pokémons inimigos!");
                break;
            }

            Pokemon inimigo = pokemons.get(indicePInimigo);
            Pokemon meuPokemon = pokemonsJogador.get(indicePJogador);

            int escolhaMenu = 0;
            while (escolhaMenu != 1) {
                escolhaMenu = Menu.mostrarMenu(scanner);
                Menu.usarMenu(escolhaMenu, meuPokemon, inimigo, jogador, scanner, pokemonsCompra, pokebolasCompra, pocoesCompra);
            }

            if (jogador.verificaSePerdeu()) break;

            jogador.adicionarDinheiro(fase * 30);

            System.out.println("Fase " + fase);
            if (pokemonsJogador.size() > 1) {
                System.out.println("Escolha um Pokémon para iniciar a batalha:");
                for (int i = 0; i < pokemonsJogador.size(); i++) {
                    Pokemon p = pokemonsJogador.get(i);
                    System.out.printf("%d - %s (Vida: %.2f/%.2f)%n",
                            i + 1, p.getNome(), p.getVida(), p.getVidaTotal());

                }

                boolean escolhaValida = false;
                while (!escolhaValida) {
                    try {
                        System.out.print("Digite o número do Pokémon: ");
                        int escolhaPokemon = scanner.nextInt();
                        scanner.nextLine();

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
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida.");
                        scanner.nextLine();
                    }
                }
            }

            indicePInimigo++;
            fase++;
        }

        System.out.println("Fim do jogo!");
        scanner.close();
    }

    /**
     * Cria pokémons para escolha de inicio
     * Cria pokémons inimigos
     * Cria Itens e pokémons da loja
     * */
    private void inicializarPokemons() {
        // opcoes para escolha inicio
        opcoes.add(new Pokemon("Pikachu", 1, 0, 35, 35, 55, 40, Set.of(Tipo.ELETRICO)));
        opcoes.add(new Pokemon("Charmander", 1, 0, 39, 39,52, 43, Set.of(Tipo.FOGO)));
        opcoes.add(new Pokemon("Squirtle", 1, 0, 44, 44,48, 65, Set.of(Tipo.AGUA)));

        // inimigos
        pokemons.add(new Pokemon("Sunkern", 1, 0, 30, 30, 30, 30, Set.of(Tipo.GRAMA)));
        pokemons.add(new Pokemon("Magikarp", 1, 0, 20, 20, 10, 80, Set.of(Tipo.AGUA)));
        pokemons.add(new Pokemon("Happiny", 1, 0, 100, 100, 5, 30, Set.of(Tipo.NORMAL)));

        pokemons.add(new Pokemon("Growlithe", 1, 0, 55, 55, 70, 60, Set.of(Tipo.FOGO)));
        pokemons.add(new Pokemon("Baltoy", 1, 0, 40, 40, 40, 55, Set.of(Tipo.TERRA, Tipo.PSIQUICO)));
        pokemons.add(new Pokemon("Swablu", 1, 0, 45, 45, 40, 60, Set.of(Tipo.NORMAL, Tipo.VOADOR)));
        pokemons.add(new Pokemon("Snubbull", 1, 0, 60, 60, 80, 50, Set.of(Tipo.FADA)));
        pokemons.add(new Pokemon("Latios", 1, 0, 80, 80, 90, 110, Set.of(Tipo.DRAGAO, Tipo.PSIQUICO)));
        pokemons.add(new Pokemon("Zapdos", 1, 0, 90, 90, 85, 100, Set.of(Tipo.ELETRICO, Tipo.VOADOR)));
        pokemons.add(new Pokemon("Mewtwo", 1, 0, 106, 106, 110, 130, Set.of(Tipo.PSIQUICO)));

        // pokebola de inicio
        pokebolasJogador.add(new Pokebola("Master Ball", 1.0, 200));
        pokebolasJogador.add(new Pokebola("Pokebola", 0.5, 50));

        // poções de inicio
        pocoesJogador.add(new Pocao("Subir nível", "Aumenta o nível do pokemon\n" +
                "Essa poção enche a vida do pokémon*", 125));

        // pokebolas para comprar
        pokebolasCompra.add(new Pokebola("Pokebola comum", 0.2, 30));
        pokebolasCompra.add(new Pokebola("Ultra ball", 0.5, 50));
        pokebolasCompra.add(new Pokebola("Master ball", 1, 150));

        // pocoes para comprar
        // pocoes só podem ser usadas antes de batalhas!
        pocoesCompra.add(new Pocao("Vida", "Recupera 50% do HP", 35));
        pocoesCompra.add(new Pocao("Subir nível", "Aumenta o nível do pokemon\n" +
                "Essa poção enche a vida do pokémon*", 125));

        // pokemons para comprar
        pokemonsCompra.add(new Pokemon("Persian", 1, 0, 65, 65,70, 60, Set.of(Tipo.NORMAL)));
        pokemonsCompra.add(new Pokemon("Galarian Moltres", 3, 0, 90, 90,85, 90, Set.of(Tipo.NOTURNO, Tipo.VOADOR)));
        pokemonsCompra.add(new Pokemon("Rayquaza", 5, 0, 105, 105,150, 90, Set.of(Tipo.DRAGAO, Tipo.VOADOR)));
    }
}
