package ast;

public class VariableDeclaration extends Declaration {
    public String tipo;
    public String nome;
    public Expression valor;

    public VariableDeclaration(String tipo, String nome, Expression valor) {
        this.tipo = tipo;
        this.nome = nome;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return tipo + " " + nome + " = " + valor + ";";
    }
}
