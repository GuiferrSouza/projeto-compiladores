package ast;

public class BinaryExpression extends Expression {
    public Expression esquerda;
    public String operador;
    public Expression direita;

    public BinaryExpression(Expression esquerda, String operador, Expression direita) {
        this.esquerda = esquerda;
        this.operador = operador;
        this.direita = direita;
    }

    @Override
    public String toString() {
        return "(" + esquerda + " " + operador + " " + direita + ")";
    }
}
