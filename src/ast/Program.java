package ast;

import java.util.List;

public class Program extends ASTNode {
    public List<Declaration> declaracoes;

    public Program(List<Declaration> declaracoes) {
        this.declaracoes = declaracoes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Declaration d : declaracoes) {
            sb.append(d.toString()).append("\n");
        }
        return sb.toString();
    }
}
