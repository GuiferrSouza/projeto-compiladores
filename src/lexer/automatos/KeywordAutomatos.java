package lexer.automatos;

import lexer.AFD;
import lexer.Token;
import java.text.CharacterIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class KeywordAutomatos extends AFD {
    private static final Map<String, String> KEYWORDS = new HashMap<>();

    static {
        KEYWORDS.put("texto", "TIPO_TEXTO");
        KEYWORDS.put("inteiro", "TIPO_INTEIRO");
        KEYWORDS.put("decimal", "TIPO_DECIMAL");
        KEYWORDS.put("boleano", "TIPO_BOLEANO");
        KEYWORDS.put("imprimir", "IMPRIMIR");
        KEYWORDS.put("se", "SE");
        KEYWORDS.put("senao", "SENAO");
        KEYWORDS.put("senão", "SENAO");
        KEYWORDS.put("enquanto", "ENQUANTO");
        KEYWORDS.put("para", "PARA");
        KEYWORDS.put("funcao", "FUNCAO");
        KEYWORDS.put("função", "FUNCAO");
        KEYWORDS.put("retornar", "RETORNAR");
        KEYWORDS.put("verdadeiro", "VERDADEIRO");
        KEYWORDS.put("falso", "FALSO");
        KEYWORDS.put("nulo", "NULO");
    }

    @Override
    public Token evaluate(CharacterIterator code) {
        if (Character.isLetter(code.current()) || code.current() == '_') {
            String word = readWord(code);

            if (isTokenSeparator(code)) {
                String tokenType = KEYWORDS.get(word.toLowerCase());
                return new Token(Objects.requireNonNullElse(tokenType, "IDENTIFICADOR"), word);
            }
        }
        return null;
    }

    private String readWord(CharacterIterator code) {
        StringBuilder word = new StringBuilder();
        while (Character.isLetterOrDigit(code.current()) || code.current() == '_') {
            word.append(code.current());
            code.next();
        }
        return word.toString();
    }
}