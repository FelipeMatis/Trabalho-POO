package itens;

/**
 * Classe Poção
 * @author .
 * @version 1.0
 * */
public class Pocao {
    private String nome;
    private String descricao;
    private int preco;

    /**
     * Construtor da classe poção
     * @param nome nome da poção
     * @param descricao descrição da poção
     * @param preco preço da poção
     * */
    public Pocao(String nome, String descricao, int preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return "Poção: " + nome + " - " + descricao;
    }
}