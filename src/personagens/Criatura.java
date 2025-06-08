package personagens;

public abstract class Criatura {
    protected String nome;
    protected int nivel;
    protected double xp;
    protected double vida;
    protected double vidaTotal;
    protected double ataque;
    protected double defesa;

    public Criatura(String nome, int nivel, double xp, double vida, double vidaTotal, double ataque, double defesa) {
        this.nome = nome;
        this.nivel = nivel;
        this.xp = xp;
        this.vida = vida;
        this.vidaTotal = vidaTotal;
        this.ataque = ataque;
        this.defesa = defesa;
    }

    public String getNome() {
        return nome;
    }

    public double getVidaTotal() {
        return vidaTotal;
    }

    public double getVida() {
        return vida;
    }

    public double getDefesa() {
        return defesa;
    }

    public int getNivel() {
        return nivel;
    }

    public double getAtaque() {
        return ataque;
    }

    public abstract void receberDano(double dano);
    public abstract void atacar(Criatura alvo);
    public abstract boolean verificaSePerdeu();

    @Override
    public String toString() {
        return nome + vida + ataque + defesa;
    }
}
