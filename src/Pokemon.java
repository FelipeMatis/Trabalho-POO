import java.util.Set;

public class Pokemon extends Criatura {
    private Set<Tipo> tipos;

    public Pokemon(String nome, int nivel, int xp, double vida, double ataque, double defesa, Set<Tipo> tipos) {
        super(nome, nivel, xp, vida, ataque, defesa);
        this.tipos = tipos;
    }

    @Override
    public void atacar(Criatura alvo) {
        if (!(alvo instanceof Pokemon alvoPokemon)) return;

        double efetividade = Tipo.calcularEfetividade(this.tipos, alvoPokemon.getTipos());
        double dano = this.getAtaque() * efetividade;
        alvo.receberDano(dano);

        System.out.println(this.getNome()+ " atacou " + alvo.getNome() + " causando " + dano + " de dano!");
    }

    public Set<Tipo> getTipos() {
        return tipos;
    }
}
