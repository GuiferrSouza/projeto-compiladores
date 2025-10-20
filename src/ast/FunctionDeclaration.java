package ast;

import java.util.List;

public class FunctionDeclaration extends Declaration {
    public String tipoRetorno;
    public String nome;
    public List<Parameter> parametros;
    public Block corpo;

    public FunctionDeclaration(String tipoRetorno, String nome,
                               List<Parameter> parametros, Block corpo) {
        this.tipoRetorno = tipoRetorno;
        this.nome = nome;
        this.parametros = parametros;
        this.corpo = corpo;
    }

    @Override
    public String toString() {
        return "funcao " + tipoRetorno + " " + nome +
                "(" + parametros + ") " + corpo;
    }
}
