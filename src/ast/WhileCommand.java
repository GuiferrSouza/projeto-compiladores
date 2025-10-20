package ast;

public class WhileCommand extends Declaration {
    public Expression condicao;
    public Block bloco;

    public WhileCommand(Expression condicao, Block bloco) {
        this.condicao = condicao;
        this.bloco = bloco;
    }

    @Override
    public String toString() {
        return "enquanto (" + condicao + ") " + bloco;
    }
}
