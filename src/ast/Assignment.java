package ast;

public class Assignment extends Declaration {
    private final String nome;
    private final Expression valor;

    public Assignment(String nome, Expression valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public Expression getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "atribuicao(" + nome + " = " + valor + ")";
    }
}