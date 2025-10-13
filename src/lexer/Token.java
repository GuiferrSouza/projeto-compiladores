package lexer;

public record Token(String tipo, String lexema) {
    public String getTipo() { return tipo; }
    public String getLexema() { return lexema; }
    @Override
    public String toString() {
        return "<" + tipo + ", " + lexema + ">";
    }
}