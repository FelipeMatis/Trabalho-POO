package jogo;

import personagens.Pokemon;
import personagens.Tipo;
import exceptions.PokemonDesmaiadoException;

import java.util.HashSet;
import java.util.Set;
import util.Relatorio;

public class Main {
    public static void main(String[] args) {
        Relatorio.limpar();
        Relatorio.registrar("===== INÍCIO DO JOGO =====");

        Jogo jogo = new Jogo();
        jogo.iniciar();
    }
}
