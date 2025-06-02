import java.util.Set;

public enum Tipo {
    FOGO,
    AGUA,
    GRAMA,
    ELETRICO,
    GELO,
    LUTADOR,
    VOADOR,
    PEDRA;

    public double efetividadeContra(Tipo outro) {
        return switch (this) {
            case FOGO -> switch (outro) {
                case GRAMA, GELO -> 2.0;
                case AGUA, PEDRA -> 0.5;
                default -> 1.0;
            };
            case AGUA -> switch (outro) {
                case FOGO, PEDRA -> 2.0;
                case AGUA, GRAMA -> 0.5;
                default -> 1.0;
            };
            case GRAMA -> switch (outro) {
                case AGUA, PEDRA -> 2.0;
                case FOGO, GRAMA, VOADOR -> 0.5;
                default -> 1.0;
            };
            case ELETRICO -> switch (outro) {
                case AGUA, VOADOR -> 2.0;
                case ELETRICO, GRAMA -> 0.5;
                default -> 1.0;
            };
            case GELO -> switch (outro) {
                case GRAMA, VOADOR -> 2.0;
                case FOGO, AGUA, GELO -> 0.5;
                default -> 1.0;
            };
            case LUTADOR -> switch (outro) {
                case PEDRA, GELO -> 2.0;
                case VOADOR -> 0.5;
                default -> 1.0;
            };
            case VOADOR -> switch (outro) {
                case GRAMA, LUTADOR -> 2.0;
                case PEDRA, ELETRICO -> 0.5;
                default -> 1.0;
            };
            case PEDRA -> switch (outro) {
                case FOGO, GELO, VOADOR -> 2.0;
                case LUTADOR, PEDRA -> 0.5;
                default -> 1.0;
            };
        };
    }

    public static double calcularEfetividade(Set<Tipo> tiposAtacante, Set<Tipo> tiposAlvo) {
        double efetividadeATQ = 1.0;
        for (Tipo tipoAtacante : tiposAtacante) {
            for (Tipo tipoAlvo : tiposAlvo) {
                double efetividade = tipoAtacante.efetividadeContra(tipoAlvo);
                efetividadeATQ = efetividadeATQ * efetividade;
                if (efetividadeATQ > 4) {
                    return 4;
                }
                if (efetividadeATQ < 0.25) {
                    return 0.25;
                }
            }
        }
        return efetividadeATQ;
    }
}
