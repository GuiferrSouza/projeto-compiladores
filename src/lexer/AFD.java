package lexer;

import java.text.CharacterIterator;

public abstract class AFD {
    public abstract Token evaluate(CharacterIterator code);
    public boolean isTokenSeparator(CharacterIterator code) {
        return code.current() == ' ' ||
                code.current() == '+' ||
                code.current() == '-' ||
                code.current() == '*' ||
                code.current() == '/' ||
                code.current() == '(' ||
                code.current() == ')' ||
                code.current() == ';' ||
                code.current() == '\n' ||
                code.current() == '\t' ||
                code.current() == CharacterIterator.DONE;
    }
}