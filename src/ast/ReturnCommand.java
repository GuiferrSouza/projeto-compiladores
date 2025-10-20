package ast;

public class ReturnCommand extends Declaration {
    public Expression expressao;

    public ReturnCommand(Expression expressao) {
        this.expressao = expressao;
    }

    @Override
    public String toString() {
        return "retornar " + expressao + ";";
    }
}
