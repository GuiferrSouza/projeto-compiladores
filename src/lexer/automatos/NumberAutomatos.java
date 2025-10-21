package lexer.automatos;

import lexer.AFD;
import lexer.Token;
import java.text.CharacterIterator;

public class NumberAutomatos extends AFD {
    @Override
    public Token evaluate(CharacterIterator code) {
        if (Character.isDigit(code.current())) {
            String number = readNumber(code);

            if (code.current() == '.') {
                code.next();
                    if (Character.isDigit(code.current())) {
                        number += "." + readNumber(code);
                    } else {
                        // Se não tem dígitos após o ponto, é um erro léxico
                        throw new RuntimeException("Número decimal inválido na posição " + (code.getIndex() - 1));
                    }
            }

            char next = code.current();
            if (!Character.isLetter(next) && next != '_') {
                return new Token("NUM", number);
            }
        }
        return null;
    }

    private String readNumber(CharacterIterator code) {
        StringBuilder number = new StringBuilder();
        while (Character.isDigit(code.current())) {
            number.append(code.current());
            code.next();
        }
        return number.toString();
    }
}