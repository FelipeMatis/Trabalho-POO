import java.util.Set;

public class Pokemon{
    private String nome;
    private int nivel;
    private int xp;
    private double vida;
    private double ataque;
    private double defesa;
    private Set<Tipo> tipos;

    public Pokemon(String nome, int nivel, int xp, double vida, double ataque, double defesa, Set<Tipo> tipos) {
        this.nome = nome;
        this.nivel = nivel;
        this.xp = xp;
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.tipos = tipos;
    }

    public Set<Tipo> getTipos() {
        return tipos;
    }

    public double getAtaque() {
        return ataque;
    }

    public double getVida() {
        return vida;
    }

    public String getNome() {
        return nome;
    }

    public double atacar(Pokemon alvo, double ataque) {
        double efetividade = Tipo.calcularEfetividade(this.tipos, alvo.getTipos());
        System.out.println(efetividade); // teste saida
        double dano = ataque * efetividade;
        alvo.receberDano(dano);
        return dano;
    }

    public void receberDano(double dano) {
        this.vida -= dano;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }
}
