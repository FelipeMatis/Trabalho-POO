package personagens;
import java.util.ArrayList;
import java.util.Scanner;
import itens.*;
import exceptions.*;
import util.Relatorio;

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
        Relatorio.registrar("Jogador criado: " + nome); // Registro inicial
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Pokemon> getPokemonsJogador() {
        return pokemonsJogador;
    }

    public void mostrarPocoesJogador() {
        if (pocaoJogador.isEmpty()) {
            System.out.println("Você não possui poções.");
            Relatorio.registrar(nome + " visualizou as poções: Nenhuma poção.");
            return;
        }
        System.out.println("Poções de " + nome + ":");
        for (int i = 0; i < pocaoJogador.size(); i++) {
            System.out.print(i + 1 + ": ");
            System.out.println(pocaoJogador.get(i));
        }
        Relatorio.registrar(nome + " visualizou as poções disponíveis.");
    }

    public void mostrarPokemonsJogador() {
        if (pokemonsJogador.isEmpty()) {
            System.out.println("Você não possui pokémons.");
            Relatorio.registrar(nome + " visualizou os Pokémons: Nenhum Pokémon.");
            return;
        }
        System.out.println("Pokémons de " + nome + ":");
        for (Pokemon p : pokemonsJogador ) {
            System.out.println(p);
        }
        Relatorio.registrar(nome + " visualizou os Pokémons disponíveis.");
    }

    public Pokemon getPokemonAtivo() {
        if (indicePokemonComVida >= 0 && indicePokemonComVida < pokemonsJogador.size()) {
            return pokemonsJogador.get(indicePokemonComVida);
        }
        Relatorio.registrar("Tentativa de obter Pokémon ativo falhou (índice inválido ou lista vazia).");
        return null;
    }

    public boolean usarPokebola(Pokemon pokemon) {
        Relatorio.registrar(nome + " tentando usar Pokébola para capturar " + pokemon.getNome() + " (vida atual: " + pokemon.getVida() + ").");
        int i = 0;
        ArrayList<Pokebola> pokebolasDisponiveis = new ArrayList<>();

        if (pokebolaJogador.size() > 0) { // Alterado para verificar se há alguma pokebola
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
                Relatorio.registrar(nome + " não tinha Pokébolas vazias para captura.");
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
                    Relatorio.registrar(nome + " errou a escolha da Pokébola: " + e.getMessage());
                }
            }

            Pokebola escolhida = pokebolasDisponiveis.get(escolha - 1);
            Relatorio.registrar(nome + " escolheu a Pokébola: " + escolhida.getTipo() + " para tentar capturar " + pokemon.getNome() + ".");
            boolean resultado = escolhida.capturar(pokemon, pokemonsJogador);

            if (!resultado) {
                pokebolaJogador.remove(escolhida);
                Relatorio.registrar("Captura de " + pokemon.getNome() + " com " + escolhida.getTipo() + " falhou. Pokébola consumida.");
            } else {
                Relatorio.registrar("Captura de " + pokemon.getNome() + " com " + escolhida.getTipo() + " bem-sucedida!");
            }
            return resultado;

        } else { // Se pokebolaJogador estiver vazia desde o início
            System.out.println("Você não tem nenhuma Pokébola!");
            Relatorio.registrar(nome + " não possui nenhuma Pokébola para uso.");
            return false;
        }
    }

    public void adicionarPokemon(Pokemon p) {
        pokemonsJogador.add(p);
        Relatorio.registrar(nome + " adicionou o Pokémon: " + p.getNome() + " à sua equipe.");
    }

    public void adicionarPocao(Pocao p) {
        pocaoJogador.add(p);
        Relatorio.registrar(nome + " adicionou a Poção: " + p.getNome() + " ao inventário.");
    }

    public void adicionarPokebola(Pokebola p) {
        pokebolaJogador.add(p);
        Relatorio.registrar(nome + " adicionou a Pokébola: " + p.getTipo() + " ao inventário.");
    }

    public void removerPokemon(int indice) {
        if (indice >= 0 && indice < pokemonsJogador.size()) {
            Pokemon pRemovido = pokemonsJogador.get(indice);
            pokemonsJogador.remove(indice);
            Relatorio.registrar(nome + " removeu o Pokémon: " + pRemovido.getNome() + " da sua equipe.");
        } else {
            Relatorio.registrar("Tentativa de remover Pokémon com índice inválido: " + indice + " por " + nome + ".");
        }
    }

    public boolean verificaSePerdeu() {
        for (Pokemon p : pokemonsJogador) {
            if (p.getVida() > 0) {
                return false; // Encontrou um Pokémon vivo, o jogador NÃO perdeu
            }
        }
        // REMOVIDOS os registros de relatório daqui.
        // Eles serão tratados no contexto onde a perda do jogo é realmente decidida.
        return true;
    }

    public boolean trocarParaProximoPokemonVivo() {
        Relatorio.registrar(nome + " tentando trocar para o próximo Pokémon vivo.");
        int tamanho = pokemonsJogador.size();
        for (int i = 1; i <= tamanho; i++) {
            int idx = (indicePokemonComVida + i) % tamanho;
            if (pokemonsJogador.get(idx).getVida() > 0) {
                indicePokemonComVida = idx;
                Relatorio.registrar(nome + " trocou para o Pokémon: " + pokemonsJogador.get(idx).getNome() + ".");
                return true;
            }
        }
        Relatorio.registrar(nome + " não encontrou outro Pokémon vivo para trocar.");
        return false;
    }

    public void adicionarDinheiro(int moedas) {
        this.moedas += moedas;
        System.out.printf("Você recebeu %s moedas\nTotal: %s\n\n", moedas, this.moedas);
        Relatorio.registrar(nome + " recebeu " + moedas + " moedas. Total: " + this.moedas + ".");
    }

    public void removerDinheiro(int moedas) {
        this.moedas -= moedas;
        Relatorio.registrar(nome + " gastou " + moedas + " moedas. Total: " + this.moedas + ".");
    }

    public void usarPocao(int indice, Scanner scanner) {
        if (indice < 0 || indice >= pocaoJogador.size()) {
            System.out.println("Índice de poção inválido.");
            Relatorio.registrar(nome + " tentou usar poção com índice inválido: " + indice + ".");
            return;
        }

        Pocao pocaoUsada = pocaoJogador.get(indice);
        System.out.println("Você escolheu: " + pocaoUsada.getNome());
        Relatorio.registrar(nome + " escolheu usar a poção: " + pocaoUsada.getNome() + ".");

        int i = 0;

        if (pokemonsJogador.isEmpty()) {
            System.out.println("Você não possui pokémons disponíveis para usar a poção!");
            Relatorio.registrar(nome + " não possui Pokémons para usar a poção " + pocaoUsada.getNome() + ".");
            return; // Sai do método se não houver pokemons
        }

        while (true) { // Loop infinito que será quebrado por 'return' ou 'break'
            if (i < 0 || i >= pokemonsJogador.size()) { // Garante que i esteja dentro dos limites
                System.out.println("Índice de Pokémon inválido. Saindo do menu de Pokémons.");
                Relatorio.registrar(nome + " encontrou um índice de Pokémon inválido ao usar poção. Saindo.");
                return;
            }

            Pokemon p = pokemonsJogador.get(i);

            System.out.println("Exibindo Pokémon " + (i + 1) + " de " + pokemonsJogador.size());
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
                    if (i < pokemonsJogador.size() - 1) {
                        i++;
                        Relatorio.registrar(nome + " avançou para o próximo Pokémon (" + pokemonsJogador.get(i).getNome() + ") ao usar poção.");
                    } else {
                        System.out.println("Você já está no último Pokémon.");
                        Relatorio.registrar(nome + " tentou avançar além do último Pokémon ao usar poção.");
                    }
                    break;
                case "A":
                    if (i > 0) {
                        i--;
                        Relatorio.registrar(nome + " retrocedeu para o Pokémon anterior (" + pokemonsJogador.get(i).getNome() + ") ao usar poção.");
                    } else {
                        System.out.println("Você já está no primeiro Pokémon.");
                        Relatorio.registrar(nome + " tentou retroceder antes do primeiro Pokémon ao usar poção.");
                    }
                    break;
                case "S":
                    System.out.println("Saindo do menu de Pokémons.");
                    Relatorio.registrar(nome + " saiu do menu de Pokémons sem usar a poção " + pocaoUsada.getNome() + ".");
                    return; // Sai do método
                case "E":
                    if (pocaoUsada.getNome().equals("Subir nível")) {
                        p.uparPokemon();
                        System.out.println(p.getNome() + " subiu de nivel!");
                        Relatorio.registrar(nome + " usou '" + pocaoUsada.getNome() + "' em " + p.getNome() + ". Nível atual: " + p.getNivel() + ".");
                    }
                    else if (pocaoUsada.getNome().equals("Vida")) {
                        p.preencherVidaPocao();
                        System.out.println(p.getNome() + " recuperou 40% da vida!");
                        System.out.println(p.getVida() + "/" + p.getVidaTotal());
                        Relatorio.registrar(nome + " usou '" + pocaoUsada.getNome() + "' em " + p.getNome() + ". Vida atual: " + p.getVida() + "/" + p.getVidaTotal() + ".");
                    }
                    pocaoJogador.remove(pocaoUsada);
                    Relatorio.registrar(nome + " removeu a poção '" + pocaoUsada.getNome() + "' do inventário após uso.");
                    return; // Sai do método após usar a poção
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    Relatorio.registrar(nome + " digitou uma opção inválida ao usar poção: '" + opcao + "'.");
            }
        }
    }

    public int getMoedas() {
        return moedas;
    }

    public ArrayList<Pokebola> getPokebolaJogador() {
        return pokebolaJogador;
    }

    public ArrayList<Pocao> getPocaoJogador() {
        return pocaoJogador;
    }

    @Override
    public String toString() {
        return "Jogador: " + nome;
    }
}