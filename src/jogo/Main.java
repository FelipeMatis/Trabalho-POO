package jogo;

import personagens.Pokemon;
import personagens.Tipo;
import exceptions.PokemonDesmaiadoException;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        jogo.iniciar();
    }
}
