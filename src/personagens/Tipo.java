package personagens;
import java.util.Set;

public enum Tipo {
    NORMAL,
    FOGO,
    AGUA,
    GRAMA,
    ELETRICO,
    GELO,
    LUTADOR,
    VENENO,
    TERRA,
    VOADOR,
    PSIQUICO,
    INSETO,
    PEDRA,
    FANTASMA,
    DRAGAO,
    NOTURNO,
    METAL,
    FADA;

    public double efetividadeContra(Tipo outro) {
        return switch (this) {
            case NORMAL -> switch (outro) {
                case PEDRA, METAL -> 0.5;
                case FANTASMA -> 0.0;
                default -> 1.0;
            };
            case FOGO -> switch (outro) {
                case GRAMA, GELO, INSETO, METAL -> 2.0;
                case FOGO, AGUA, PEDRA, DRAGAO -> 0.5;
                default -> 1.0;
            };
            case AGUA -> switch (outro) {
                case FOGO, PEDRA, TERRA -> 2.0;
                case AGUA, GRAMA, DRAGAO -> 0.5;
                default -> 1.0;
            };
            case GRAMA -> switch (outro) {
                case AGUA, PEDRA, TERRA -> 2.0;
                case FOGO, GRAMA, VOADOR, INSETO, DRAGAO, VENENO -> 0.5;
                default -> 1.0;
            };
            case ELETRICO -> switch (outro) {
                case AGUA, VOADOR -> 2.0;
                case ELETRICO, GRAMA, DRAGAO -> 0.5;
                case TERRA -> 0.0;
                default -> 1.0;
            };
            case GELO -> switch (outro) {
                case GRAMA, VOADOR, TERRA, DRAGAO -> 2.0;
                case FOGO, AGUA, GELO, METAL -> 0.5;
                default -> 1.0;
            };
            case LUTADOR -> switch (outro) {
                case NORMAL, PEDRA, GELO, METAL, NOTURNO -> 2.0;
                case VENENO, VOADOR, PSIQUICO, INSETO, FADA -> 0.5;
                case FANTASMA -> 0.0;
                default -> 1.0;
            };
            case VENENO -> switch (outro) {
                case GRAMA, FADA -> 2.0;
                case VENENO, TERRA, PEDRA, FANTASMA -> 0.5;
                case METAL -> 0.0;
                default -> 1.0;
            };
            case TERRA -> switch (outro) {
                case FOGO, ELETRICO, VENENO, PEDRA, METAL -> 2.0;
                case GRAMA, INSETO -> 0.5;
                case VOADOR -> 0.0;
                default -> 1.0;
            };
            case VOADOR -> switch (outro) {
                case GRAMA, LUTADOR, INSETO -> 2.0;
                case ELETRICO, PEDRA, METAL -> 0.5;
                default -> 1.0;
            };
            case PSIQUICO -> switch (outro) {
                case LUTADOR, VENENO -> 2.0;
                case PSIQUICO, METAL -> 0.5;
                case NOTURNO -> 0.0;
                default -> 1.0;
            };
            case INSETO -> switch (outro) {
                case GRAMA, PSIQUICO, NOTURNO -> 2.0;
                case FOGO, LUTADOR, VENENO, VOADOR, FANTASMA, METAL, FADA -> 0.5;
                default -> 1.0;
            };
            case PEDRA -> switch (outro) {
                case FOGO, GELO, VOADOR, INSETO -> 2.0;
                case LUTADOR, TERRA, METAL -> 0.5;
                default -> 1.0;
            };
            case FANTASMA -> switch (outro) {
                case PSIQUICO, FANTASMA -> 2.0;
                case METAL -> 0.5;
                case NORMAL -> 0.0;
                default -> 1.0;
            };
            case DRAGAO -> switch (outro) {
                case DRAGAO -> 2.0;
                case METAL -> 0.5;
                case FADA -> 0.0;
                default -> 1.0;
            };
            case NOTURNO -> switch (outro) {
                case PSIQUICO, FANTASMA -> 2.0;
                case LUTADOR, NOTURNO, FADA -> 0.5;
                default -> 1.0;
            };
            case METAL -> switch (outro) {
                case GELO, PEDRA, FADA -> 2.0;
                case FOGO, AGUA, ELETRICO, METAL -> 0.5;
                default -> 1.0;
            };
            case FADA -> switch (outro) {
                case LUTADOR, DRAGAO, NOTURNO -> 2.0;
                case FOGO, VENENO, METAL -> 0.5;
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
