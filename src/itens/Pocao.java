package itens;

public class Pocao {
    private String nome;
    private String descricao;
    private int preco;

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
}
