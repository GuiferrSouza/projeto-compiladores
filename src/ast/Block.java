package ast;

import java.util.List;

public class Block extends ASTNode {
    public List<Declaration> declaracoes;

    public Block(List<Declaration> declaracoes) {
        this.declaracoes = declaracoes;
    }

    @Override
    public String toString() {
        return "{ " + declaracoes + " }";
    }
}
