package ast;

import java.util.List;

public class PrintCommand extends Declaration {
    public List<Expression> expressoes;

    public PrintCommand(List<Expression> expressoes) {
        this.expressoes = expressoes;
    }

    @Override
    public String toString() {
        return "imprimir(" + expressoes + ");";
    }
}
