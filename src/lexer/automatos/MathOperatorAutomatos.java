package lexer.automatos;

import lexer.AFD;
import lexer.Token;
import java.text.CharacterIterator;

public class MathOperatorAutomatos extends AFD {
    @Override
    public Token evaluate(CharacterIterator code) {
        return switch (code.current()) {
            case '+' -> {
                code.next();
                yield new Token("PLUS", "+");
            }
            case '-' -> {
                code.next();
                yield new Token("MINUS", "-");
            }
            case '*' -> {
                code.next();
                yield new Token("MULT", "*");
            }
            case '/' -> {
                code.next();
                yield new Token("DIV", "/");
            }
            case CharacterIterator.DONE -> new Token("EOF", "$");
            default -> null;
        };
    }
}