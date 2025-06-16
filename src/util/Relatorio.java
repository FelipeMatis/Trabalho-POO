package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Relatorio {
    private static final String NOME_ARQUIVO = "relatorio_batalha.txt";

    public static void registrar(String texto) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOME_ARQUIVO, true))) {
            writer.println(texto);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no relatório: " + e.getMessage());
        }
    }

    public static void limpar() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOME_ARQUIVO))) {
            writer.print(""); // limpa o conteúdo
        } catch (IOException e) {
            System.err.println("Erro ao limpar o relatório: " + e.getMessage());
        }
    }
}
