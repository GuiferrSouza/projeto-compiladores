package ast;

public class Identifier extends Expression {
    public String nome;

    public Identifier(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
