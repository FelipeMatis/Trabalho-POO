import java.util.Set;

public class Criatura {
    private String nome;
    private int nivel;
    private int xp;
    private double vida;
    private double ataque;
    private double defesa;

    public Criatura(String nome, int nivel, int xp, double vida, double ataque, double defesa) {
        this.nome = nome;
        this.nivel = nivel;
        this.xp = xp;
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
    }

    public String getNome() {
        return nome;
    }

    public double getVida() {
        return vida;
    }

    public double getAtaque() {
        return ataque;
    }



    public void receberDano(double dano) {
        this.vida -= dano;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }
    public abstract void atacar(Criatura alvo);
}
