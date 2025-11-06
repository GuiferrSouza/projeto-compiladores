import files.FileReader;
import files.FileWriter;
import lexer.Lexer;
import lexer.Token;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //linha 11 ate a 19 eu criei o teste para demonstrar e comentei a linha 21 ate 30
        String codigo = null;
        try {
            codigo = FileReader.LerArquivoCompleto("teste.txt"); //Aqui ele le um arquivo com o path correto, deixa ele junto da pasta que tem a gramatica
            FileWriter.CriarArquivo("testete.txt", codigo); // Aqui vc passa um nome para o arquivo e o valor em string, ele vai criar um arquivo tbm junto com a gramatica
            System.out.println(codigo);
        }catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }

        /*String codigo = """
                imprimir("Olá Mundo!");
                texto meuTexto = "abc";
                inteiro meuInteiro = 100;
                decimal meuDecimal é meuInteiro + 10.0;
                logico meulogico = verdadeiro;""";

        System.out.println("=== CÓDIGO FONTE ===");
        System.out.println(codigo);
        System.out.println("\n=== TOKENS GERADOS ===\n");*/

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
                "logico ativo é verdadeiro;",
                "logico inativo é falso;",
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