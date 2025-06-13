package personagens;

import jogo.Acao;
import java.util.Set;
import java.util.Objects;

public class Pokemon extends Criatura implements Acao {
    private Set<Tipo> tipos;

    public Pokemon(String nome, int nivel, double xp, double vida, double vidaTotal, double ataque, double defesa, Set<Tipo> tipos) {
        super(nome, nivel, xp, vida, vidaTotal, ataque, defesa);
        this.tipos = tipos;
    }

    @Override
    public void receberDano(double dano) {
        this.vida -= dano;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }

    @Override
    public void atacar(Criatura alvo) {
        if (!(alvo instanceof Pokemon outro)) {
            System.out.println("Alvo inválido para ataque.");
            return;
        }

        double efetividade = Tipo.calcularEfetividade(this.tipos, outro.getTipos());
        double dano = (this.getAtaque() - outro.getDefesa()) * efetividade;

        if (dano <= 0) {
            if (efetividade > 1) {
                dano = this.getAtaque() * 0.20;
            } else if (efetividade == 1) {
                dano = this.getAtaque() * 0.10;
            } else {
                dano = this.getAtaque() * 0.05;
            }
        }

        outro.receberDano(dano);
        System.out.printf("%s atacou %s causando %.2f de dano! (Efetividade: %.2f)\n",
                this.getNome(), outro.getNome(), dano, efetividade);
    }

    @Override
    public void executar(Criatura alvo) {
        Objects.requireNonNull(alvo, "O alvo da ação não pode ser nulo.");
        atacar(alvo);
    }

    public void ganharXp(Pokemon inimigoDerrotado) {
        double xpGanho = calcularXpAdquirido(inimigoDerrotado);
        this.xp += xpGanho;

        while (this.xp >= xpParaProximoNivel()) {
            this.xp -= xpParaProximoNivel();
            this.nivel++;
            this.vidaTotal *= 1.05;
            this.ataque *= 1.05;
            this.defesa *= 1.05;
            preencherVida();
            System.out.println(this.getNome() + " subiu para o nível " + this.nivel + "!");
        }

        System.out.println(this.getNome() + " ganhou " + xpGanho + " XP! Faltam " +
                (xpParaProximoNivel() - this.xp) + " para o próximo nível.");
    }

    private int xpParaProximoNivel() {
        return this.nivel * 100;
    }

    public double calcularXpAdquirido(Pokemon inimigoDerrotado) {
        double soma = (inimigoDerrotado.getNivel() * 50)
                + inimigoDerrotado.getVida()
                + inimigoDerrotado.getAtaque()
                + inimigoDerrotado.getDefesa();
        return soma * 0.25;
    }

    public int calcularPreco() {
        return (int)(((getNivel() * 50) + getVida() + getAtaque() + getDefesa()) * 0.5);
    }

    public void preencherVida() {
        vida = vidaTotal;
    }

    @Override
    public boolean verificaSePerdeu() {
        return vida == 0;
    }

    public Set<Tipo> getTipos() {
        return tipos;
    }

    @Override
    public String toString() {
        return String.format(
                "Nome: %s | Nível: %d | Vida: %.2f/%.2f | Ataque: %.2f | Defesa: %.2f | Tipos: %s",
                nome, nivel, vida, vidaTotal, ataque, defesa, tipos
        );
    }
}
