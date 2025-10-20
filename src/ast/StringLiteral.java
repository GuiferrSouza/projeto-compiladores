package ast;

public class StringLiteral extends Expression {
    public String valor;

    public StringLiteral(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "\"" + valor + "\"";
    }
}
