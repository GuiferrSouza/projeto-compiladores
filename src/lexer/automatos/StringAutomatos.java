package lexer.automatos;

import lexer.AFD;
import lexer.Token;
import java.text.CharacterIterator;

public class StringAutomatos extends AFD {
    @Override
    public Token evaluate(CharacterIterator code) {
        if (code.current() == '"') {
            code.next();
            String content = readStringContent(code);

            if (code.current() == '"') {
                code.next();
                return new Token("STRING", content);
            } else {
                throw new RuntimeException("Erro: String não fechada na posição " + code.getIndex());
            }
        }
        return null;
    }

    private String readStringContent(CharacterIterator code) {
        StringBuilder content = new StringBuilder();

        while (code.current() != '"' && code.current() != CharacterIterator.DONE) {
            if (code.current() == '\\') {
                code.next();
                switch (code.current()) {
                    case 'n':
                        content.append('\n');
                        break;
                    case 't':
                        content.append('\t');
                        break;
                    case '\\':
                        content.append('\\');
                        break;
                    case '"':
                        content.append('"');
                        break;
                    default:
                        content.append(code.current());
                        break;
                }
            } else {
                content.append(code.current());
            }
            code.next();
        }

        return content.toString();
    }
}