package lexer.automatos;

import lexer.AFD;
import lexer.Token;
import java.text.CharacterIterator;

public class DelimiterAutomatos extends AFD {
    @Override
    public Token evaluate(CharacterIterator code) {
        char current = code.current();

        switch (current) {
            case '=':
                code.next();
                if (code.current() == '=') {
                    code.next();
                    return new Token("IGUAL_IGUAL", "==");
                } else {
                    return new Token("ATRIBUICAO", "=");
                }
            case 'é':
                code.next();
                return new Token("ATRIBUICAO", "é");
            case '!':
                code.next();
                if (code.current() == '=') {
                    code.next();
                    return new Token("DIFERENTE", "!=");
                } else {
                    return new Token("NAO", "!");
                }
            case '<':
                code.next();
                if (code.current() == '=') {
                    code.next();
                    return new Token("MENOR_IGUAL", "<=");
                } else {
                    return new Token("MENOR", "<");
                }
            case '>':
                code.next();
                if (code.current() == '=') {
                    code.next();
                    return new Token("MAIOR_IGUAL", ">=");
                } else {
                    return new Token("MAIOR", ">");
                }
            case '&':
                code.next();
                if (code.current() == '&') {
                    code.next();
                    return new Token("E_LOGICO", "&&");
                }
                return null;
            case '|':
                code.next();
                if (code.current() == '|') {
                    code.next();
                    return new Token("OU_LOGICO", "||");
                }
                return null;
            case '(':
                code.next();
                return new Token("ABRE_PAREN", "(");
            case ')':
                code.next();
                return new Token("FECHA_PAREN", ")");
            case '{':
                code.next();
                return new Token("ABRE_CHAVE", "{");
            case '}':
                code.next();
                return new Token("FECHA_CHAVE", "}");
            case '[':
                code.next();
                return new Token("ABRE_COLCHETE", "[");
            case ']':
                code.next();
                return new Token("FECHA_COLCHETE", "]");
            case ';':
                code.next();
                return new Token("PONTO_VIRGULA", ";");
            case ',':
                code.next();
                return new Token("VIRGULA", ",");
            case '.':
                code.next();
                return new Token("PONTO", ".");
            case ':':
                code.next();
                return new Token("DOIS_PONTOS", ":");
            case CharacterIterator.DONE:
                return new Token("EOF", "$");
            default:
                return null;
        }
    }
}