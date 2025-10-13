import lexer.Lexer;
import lexer.Token;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String codigo = """
                imprimir("Olá Mundo!");
                texto meuTexto = "abc";
                inteiro meuInteiro = 100;
                decimal meuDecimal é meuInteiro + 10.0;
                boleano meuBoleano = verdadeiro;""";

        System.out.println("=== CÓDIGO FONTE ===");
        System.out.println(codigo);
        System.out.println("\n=== TOKENS GERADOS ===\n");

        try {
            Lexer lexer = new Lexer(codigo);
            List<Token> tokens = lexer.getTokens();

            for (Token token : tokens) {
                System.out.println(token);
            }

            System.out.println("\n=== ANÁLISE CONCLUÍDA COM SUCESSO ===");
            System.out.println("Total de tokens: " + tokens.size());

        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }

        System.out.println("\n=== TESTES ADICIONAIS ===\n");
        testarExpressoes();
    }

    private static void testarExpressoes() {
        String[] testes = {
                "inteiro x é 10 + 20 * 2;",
                "decimal pi é 3.14159;",
                "texto nome é \"João Silva\";",
                "boleano ativo é verdadeiro;",
                "boleano inativo é falso;",
                "imprimir(\"Teste: \" + nome);"
        };

        for (String teste : testes) {
            System.out.println("Testando: " + teste);
            try {
                Lexer lexer = new Lexer(teste);
                List<Token> tokens = lexer.getTokens();
                System.out.print("  Tokens: ");
                for (Token token : tokens) {
                    if (!token.getTipo().equals("EOF")) {
                        System.out.print(token.getTipo() + " ");
                    }
                }
                System.out.println("✓");
            } catch (Exception e) {
                System.out.println("  Erro: " + e.getMessage() + " ✗");
            }
            System.out.println();
        }
    }
}