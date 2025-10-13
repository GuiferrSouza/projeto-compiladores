package ast;

public class Parameter {
    public String tipo;
    public String nome;

    public Parameter(String tipo, String nome) {
        this.tipo = tipo;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return tipo + " " + nome;
    }
}
