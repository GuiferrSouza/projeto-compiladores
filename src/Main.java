import lexer.Lexer;
import lexer.Token;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String[] testCases = {
                "+++",
                "123 + 456",
                "12.5 * 3.14",
                "10 / 2 - 5",
                "123.45 + 67.89 * 2"
        };

        for (String testCase : testCases) {
            System.out.println("Analisando: " + testCase);
            try {
                Lexer lexer = new Lexer(testCase);
                List<Token> tokens = lexer.getTokens();

                for (Token token : tokens) {
                    System.out.println("  " + token);
                }
                System.out.println();

            } catch (Exception e) {
                System.out.println("  Erro: " + e.getMessage());
                System.out.println();
            }
        }
    }
}