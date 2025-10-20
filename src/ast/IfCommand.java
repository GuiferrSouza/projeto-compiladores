package ast;

public class IfCommand extends Declaration {
    public Expression condicao;
    public Block blocoSe;
    public Block blocoSenao;

    public IfCommand(Expression condicao, Block blocoSe, Block blocoSenao) {
        this.condicao = condicao;
        this.blocoSe = blocoSe;
        this.blocoSenao = blocoSenao;
    }

    @Override
    public String toString() {
        String result = "se (" + condicao + ") " + blocoSe;
        if (blocoSenao != null) {
            result += " senao " + blocoSenao;
        }
        return result;
    }
}
