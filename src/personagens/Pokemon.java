package personagens;
import java.util.Set;

public class Pokemon extends Criatura {
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
        if (!(alvo instanceof Pokemon)) return;
        double efetividade = Tipo.calcularEfetividade(this.tipos, ((Pokemon) alvo).getTipos());
        double dano = (this.getAtaque() - alvo.getDefesa())* efetividade;
        // para que não ocorra ataques sem dano
        if (dano <= 0) {
            if (efetividade > 1) {
                dano = this.getAtaque() * 0.20;
            }
            else if (efetividade == 1){
                dano = this.getAtaque() * 0.10;
            }
            else {
                dano = this.getAtaque() * 0.05;
            }
        }
        alvo.receberDano(dano);
        System.out.printf("%s atacou %s causando %.2f de dano!\n", this.getNome(), alvo.getNome(), dano);
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
        System.out.println(this.getNome() + " ganhou " + xpGanho + "XP! +" + (xpParaProximoNivel()-this.xp) + " para o próximo nível!");
    }

    private int xpParaProximoNivel() {
        return this.nivel * 100;
    }

    public double calcularXpAdquirido(Pokemon inimigoDerrotado) {
        double soma = (inimigoDerrotado.getNivel() * 50) + inimigoDerrotado.getVida() + inimigoDerrotado.getAtaque() + inimigoDerrotado.getDefesa();
        soma *= 0.25;
        return soma;
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
