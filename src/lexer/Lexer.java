package lexer;

import lexer.automatos.MathOperator;
import lexer.automatos.Number;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final List<Token> tokens;
    private final List<AFD> afds;
    private final CharacterIterator code;

    public Lexer(String code) {
        tokens = new ArrayList<>();
        this.code = new StringCharacterIterator(code);
        afds = new ArrayList<>();

        afds.add(new Number());
        afds.add(new MathOperator());
    }

    public void skipWhiteSpace() {
        while (code.current() == ' ' || code.current() == '\n' || code.current() == '\t') {
            code.next();
        }
    }

    private void error() {
        throw new RuntimeException("Error: token not recognized: '" + code.current() + "' at position " + code.getIndex());
    }

    public List<Token> getTokens() {
        Token t;
        do {
            skipWhiteSpace();
            t = searchNextToken();
            if (t == null) error();
            tokens.add(t);
        } while (!t.getTipo().equals("EOF"));

        return tokens;
    }

    private Token searchNextToken() {
        int pos = code.getIndex();

        for (AFD afd : afds) {
            Token t = afd.evaluate(code);
            if (t != null) return t;
            code.setIndex(pos);
        }

        return null;
    }
}