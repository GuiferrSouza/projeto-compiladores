package ast;

public class NumberLiteral extends Expression {
    public String valor;

    public NumberLiteral(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}
