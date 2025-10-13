package ast;

public class UnitExpression extends Expression {
    public String operador;
    public Expression operando;

    public UnitExpression(String operador, Expression operando) {
        this.operador = operador;
        this.operando = operando;
    }

    @Override
    public String toString() {
        return operador + operando;
    }
}
