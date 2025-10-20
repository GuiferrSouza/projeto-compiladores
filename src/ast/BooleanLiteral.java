package ast;

public class BooleanLiteral extends Expression {
    public boolean valor;

    public BooleanLiteral(boolean valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
