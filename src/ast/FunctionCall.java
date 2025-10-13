package ast;

import java.util.List;

public class FunctionCall extends Expression {
    public String nome;
    public List<Expression> argumentos;

    public FunctionCall(String nome, List<Expression> argumentos) {
        this.nome = nome;
        this.argumentos = argumentos;
    }

    @Override
    public String toString() {
        return nome + "(" + argumentos + ")";
    }
}
