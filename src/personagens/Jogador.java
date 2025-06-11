package personagens;
import java.util.ArrayList;
import java.util.Scanner;
import itens.*;
import exceptions.*;

public class Jogador {
    private String nome;
    private ArrayList<Pokemon> pokemonsJogador;
    private ArrayList<Pokebola> pokebolaJogador;
    private ArrayList<Pocao> pocaoJogador;
    private int indicePokemonComVida;
    private int moedas;

    public Jogador(String nome, ArrayList<Pokemon> pokemonJogador, ArrayList<Pokebola> pokebolaJogador, ArrayList<Pocao> pocaoJogador) {
        this.nome = nome;
        this.pokemonsJogador = pokemonJogador;
        this.pokebolaJogador = pokebolaJogador;
        this.pocaoJogador = pocaoJogador;
        this.indicePokemonComVida = 0;
        this.moedas = 0;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Pokemon> getPokemonsJogador() {
        return pokemonsJogador;
    }

    public Pokemon getPokemonAtivo() {
        if (indicePokemonComVida >= 0 && indicePokemonComVida < pokemonsJogador.size()) {
            return pokemonsJogador.get(indicePokemonComVida);
        }
        return null;
    }

    public boolean usarPokebola(Pokemon pokemon) {
        int i = 0;
        ArrayList<Pokebola> pokebolasDisponiveis = new ArrayList<>();

        if (pokebolaJogador.size() > 1) {
            for (Pokebola p : pokebolaJogador) {
                if (p.estaVazia()) {
                    i++;
                    pokebolasDisponiveis.add(p);
                    System.out.println(i + " - Tipo: " + p.getTipo());
                    System.out.println("   Taxa de captura: " + p.getTaxaCaptura());
                }
            }

            if (pokebolasDisponiveis.isEmpty()) {
                System.out.println("Nenhuma Pokébola vazia disponível!");
                return false;
            }

            Scanner scanner = new Scanner(System.in);
            int escolha = 0;

            while (escolha < 1 || escolha > i) {
                try {
                    System.out.print("Digite o número da Pokébola que deseja usar: ");

                    if (!scanner.hasNextInt()) {
                        throw new EscolhaInvalidaException("Entrada inválida. Digite um número.");
                    }

                    escolha = scanner.nextInt();
                    scanner.nextLine();

                    if (escolha < 1 || escolha > i) {
                        throw new EscolhaInvalidaException("Número fora do intervalo. Escolha de 1 a " + i);
                    }

                } catch (EscolhaInvalidaException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }

            Pokebola escolhida = pokebolasDisponiveis.get(escolha - 1);
            boolean resultado = escolhida.capturar(pokemon, pokemonsJogador);

            if (!resultado) {
                pokebolaJogador.remove(escolhida);
            }
            return resultado;

        } else if (!pokebolaJogador.isEmpty()) {
            Pokebola unica = pokebolaJogador.get(0);
            if (unica.estaVazia()) {
                boolean resultado = unica.capturar(pokemon, pokemonsJogador);
                if (!resultado) {
                    pokebolaJogador.remove(0);
                }
                return resultado;
            } else {
                System.out.println("A única Pokébola disponível está ocupada!");
                return false;
            }
        } else {
            System.out.println("Você não tem nenhuma Pokébola!");
            return false;
        }
    }

    public void adicionarPokemon(Pokemon p) {
        pokemonsJogador.add(p);
    }

    public void adicionarPocao(Pocao p) {
        pocaoJogador.add(p);
    }

    public void adicionarPokebola(Pokebola p) {
        pokebolaJogador.add(p);
    }

    public void removerPokemon(int indice) {
        pokemonsJogador.remove(indice);
    }

    public boolean verificaSePerdeu() {
        for (Pokemon p : pokemonsJogador) {
            if (p.getVida() > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean trocarParaProximoPokemonVivo() {
        int tamanho = pokemonsJogador.size();
        for (int i = 1; i <= tamanho; i++) {
            int idx = (indicePokemonComVida + i) % tamanho;
            if (pokemonsJogador.get(idx).getVida() > 0) {
                indicePokemonComVida = idx;
                return true;
            }
        }
        return false;
    }

    public void adicionarDinheiro(int moedas) {
        this.moedas += moedas;
        System.out.printf("Você recebeu %s moedas\nTotal: %s\n\n", moedas, this.moedas);
    }

    public void removerDinheiro(int moedas) {
        this.moedas -= moedas;
    }

    public int getMoedas() {
        return moedas;
    }


    public int getIndicePokemonComVida() {
        return indicePokemonComVida;
    }

    public ArrayList<Pokebola> getPokebolaJogador() {
        return pokebolaJogador;
    }

    @Override
    public String toString() {
        return "Jogador: " + nome;
    }
}
