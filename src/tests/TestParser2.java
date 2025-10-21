package tests;

import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import ast.Program;
import java.util.List;

public class TestParser2 {

    public static void main(String[] args) {
        System.out.println("=== TESTE DO PARSER (ANÁLISE SINTÁTICA) ===\n");

        testar("Loop ENQUANTO",
                """
                        inteiro contador = 0;
                        enquanto (contador - 4 < 5) {
                            imprimir(contador);
                            contador = contador + 1;
                        }""");
    }

    private static void testar(String descricao, String codigo) {
        System.out.println("─────────────────────────────────────");
        System.out.println("Teste: " + descricao);
        System.out.println("─────────────────────────────────────");
        System.out.println("CÓDIGO:");
        System.out.println(codigo);
        System.out.println();

        try {
            // Fase 1: Análise Léxica
            System.out.println("ANÁLISE LÉXICA:");
            Lexer lexer = new Lexer(codigo);
            List<Token> tokens = lexer.getTokens();

            System.out.println("✓ " + tokens.size() + " tokens gerados");

            // Mostra os primeiros tokens
            System.out.print("  Tokens: ");
            int count = 0;
            for (Token token : tokens) {
                if (!token.getTipo().equals("EOF") && count < 10) {
                    System.out.print(token.getTipo() + " ");
                    count++;
                }
            }
            if (tokens.size() > 10) {
                System.out.print("...");
            }
            System.out.println();
            System.out.println();

            // Fase 2: Análise Sintática
            System.out.println("ANÁLISE SINTÁTICA:");
            Parser parser = new Parser(tokens);
            Program programa = parser.parsear();

            System.out.println("✓ Análise sintática concluída com sucesso!");
            System.out.println();

            // Mostra a AST gerada
            System.out.println("AST GERADA:");
            System.out.println(programa);

        } catch (Exception e) {
            System.out.println("✗ ERRO: " + e.getMessage());
        }

        System.out.println();
    }
}