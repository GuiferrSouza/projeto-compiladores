package lexer;
import lexer.automatos.*;

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

        afds.add(new StringAutomatos());
        afds.add(new NumberAutomatos());
        afds.add(new KeywordAutomatos());
        afds.add(new MathOperatorAutomatos());
        afds.add(new DelimiterAutomatos());
    }

    public void skipWhiteSpace() {
        while (code.current() == ' ' ||
                code.current() == '\n' ||
                code.current() == '\t' ||
                code.current() == '\r') {
            code.next();
        }
    }

    public void skipComments() {
        if (code.current() == '/' && peekNext() == '/') {
            while (code.current() != '\n' && code.current() != CharacterIterator.DONE) {
                code.next();
            }
            if (code.current() == '\n') {
            code.next();
            }
        }
    }

    private char peekNext() {
        int pos = code.getIndex();
        code.next();
        char next = code.current();
        code.setIndex(pos);
        return next;
    }

    private void error() {
        throw new RuntimeException("Erro: token não reconhecido: '" + code.current() +
                "' na posição " + code.getIndex());
    }

    public List<Token> getTokens() {
        Token t;
        do {
            skipWhiteSpace();
            skipComments();

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