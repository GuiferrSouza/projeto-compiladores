package lexer;

public record Token(String tipo, String lexema) {
    public String getTipo() { return tipo; }
    @Override
    public String toString() {
        return "<" + tipo + ", " + lexema + ">";
    }
}