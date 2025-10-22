package tests;

import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import ast.Program;
import java.util.List;

public class TestParser {

    public static void main(String[] args) {
        System.out.println("=== TESTE DO PARSER (ANÁLISE SINTÁTICA) ===\n");

        // Teste 1: Declarações de variáveis
        testar("Declaração de variáveis",
                """
                        inteiro x = 10;
                        texto nome = "João";
                        logico ativo = verdadeiro;""");

        // Teste 2: Comando imprimir
        testar("Comando imprimir",
                """
                        imprimir("Olá Mundo!");
                        inteiro idade = 25;
                        imprimir("Idade:", idade);""");

        // Teste 3: Operações aritméticas
        testar("Operações aritméticas",
                """
                        inteiro a = 10;
                        inteiro b = 20;
                        inteiro soma = a + b;
                        decimal media = (a + b) / 2.0;""");

        testar("Estrutura SE",
                """
                        inteiro idade = 18;
                        se (idade >= 18) {
                            imprimir("Maior de idade");
                        } senao {
                            imprimir("Menor de idade");
                        }""");

        // Teste 5: Loop enquanto
        testar("Loop ENQUANTO",
                """
                        inteiro contador = 0;
                        enquanto (contador < 5) {
                            imprimir(contador);
                            contador = contador + 1;
                        }""");

        // Teste 6: Função
        testar("Declaração de função",
                """
                        funcao inteiro somar(inteiro a, inteiro b) {
                            retornar a + b;
                        }""");

        // Teste 7: Expressões complexas
        testar("Expressões complexas",
                """
                        inteiro x = 10;
                        inteiro y = 20;
                        logico resultado = (x > 5) && (y < 30);
                        inteiro calc = x * 2 + y / 4 - 3;""");

        // Teste 8: Programa completo
        testar("Programa completo",
                """
                        funcao inteiro fatorial(inteiro n) {
                            se (n <= 1) {
                                retornar 1;
                            } senao {
                                retornar n * fatorial(n - 1);
                            }
                        }
                        
                        inteiro numero = 5;
                        inteiro resultado = fatorial(numero);
                        imprimir("Fatorial de", numero, "=", resultado);""");

        // Teste 9: Erro de sintaxe (para testar validação)
        System.out.println("\n=== TESTE DE VALIDAÇÃO DE ERROS ===\n");
        testar("Erro: faltando ponto e vírgula",
                "inteiro _10 = 10");  // Faltando ;

        testar("Erro: faltando parêntese",
                "imprimir(\"teste\";");  // Faltando )
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
