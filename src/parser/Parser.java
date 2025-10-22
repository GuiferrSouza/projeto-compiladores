package parser;

import lexer.Token;
import ast.*;
import java.util.List;
import java.util.ArrayList;

public class Parser {
    private final List<Token> tokens;
    private int posicao;
    private Token tokenAtual;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.posicao = 0;
        this.tokenAtual = tokens.getFirst();
    }

    private void avancar() {
        if (posicao < tokens.size() - 1) {
            posicao++;
            tokenAtual = tokens.get(posicao);
        }
    }

    private boolean verificar(String tipo) {
        return tokenAtual.getTipo().equals(tipo);
    }

    private Token consumir(String tipo, String mensagemErro) {
        if (verificar(tipo)) {
            Token token = tokenAtual;
            avancar();
            return token;
        }
        
        throw new RuntimeException("Erro de sintaxe: " + mensagemErro +
                ". Esperado: " + tipo + ", Encontrado: " +
                tokenAtual.getTipo() + " '" + tokenAtual.getLexema() + "'"+
                " na posição " + (posicao+1));
    }

    public Program parsear() {
        List<Declaration> declaracoes = new ArrayList<>();

        while (!verificar("EOF")) {
            declaracoes.add(parsearDeclaracao());
        }

        return new Program(declaracoes);
    }

    private Declaration parsearDeclaracao() {
        if (verificar("TIPO_TEXTO") || verificar("TIPO_INTEIRO") ||
                verificar("TIPO_DECIMAL") || verificar("TIPO_LOGICO")) {
            return parsearDeclaracaoVariavel();
        }

        if (verificar("IMPRIMIR")) {
            return parsearComandoImprimir();
        }

        if (verificar("SE")) {
            return parsearComandoSe();
        }

        if (verificar("ENQUANTO")) {
            return parsearComandoEnquanto();
        }

        if (verificar("FUNCAO")) {
            return parsearDeclaracaoFuncao();
        }

        if (verificar("RETORNAR")) {
            return parsearComandoRetornar();
        }

        if (verificar("IDENTIFICADOR")) {
            return parsearAtribuicao();
        }

        throw new RuntimeException("Declaração inválida: " + tokenAtual.getLexema());
    }

    private VariableDeclaration parsearDeclaracaoVariavel() {
        String tipo = tokenAtual.getLexema();
        avancar();

        Token nomeToken = consumir("IDENTIFICADOR", "Esperado nome da variável");
        String nome = nomeToken.getLexema();

        consumir("ATRIBUICAO", "Esperado '='");
        Expression valor = parsearExpressao();
        consumir("PONTO_VIRGULA", "Esperado ';'");

        return new VariableDeclaration(tipo, nome, valor);
    }

    // NOVO: Método para parsear atribuição
    private Assignment parsearAtribuicao() {
        Token nomeToken = consumir("IDENTIFICADOR", "Esperado identificador");
        String nome = nomeToken.getLexema();

        consumir("ATRIBUICAO", "Esperado '='");
        Expression valor = parsearExpressao();
        consumir("PONTO_VIRGULA", "Esperado ';'");

        return new Assignment(nome, valor);
    }

    private PrintCommand parsearComandoImprimir() {
        consumir("IMPRIMIR", "Esperado 'imprimir'");
        consumir("ABRE_PAREN", "Esperado '('");

        List<Expression> expressoes = CreateExpressionList();
        consumir("PONTO_VIRGULA", "Esperado ';'");

        return new PrintCommand(expressoes);
    }

    private IfCommand parsearComandoSe() {
        consumir("SE", "Esperado 'se'");
        consumir("ABRE_PAREN", "Esperado '('");
        Expression condicao = parsearExpressao();
        consumir("FECHA_PAREN", "Esperado ')'");

        Block blocoSe = parsearBloco();
        Block blocoSenao = null;

        if (verificar("SENAO")) {
            avancar();
            blocoSenao = parsearBloco();
        }

        return new IfCommand(condicao, blocoSe, blocoSenao);
    }

    private WhileCommand parsearComandoEnquanto() {
        consumir("ENQUANTO", "Esperado 'enquanto'");
        consumir("ABRE_PAREN", "Esperado '('");

        Expression condicao = parsearExpressao();
        consumir("FECHA_PAREN", "Esperado ')'");
        Block bloco = parsearBloco();

        return new WhileCommand(condicao, bloco);
    }

    private Block parsearBloco() {
        List<Declaration> declaracoes = new ArrayList<>();

        if (verificar("ABRE_CHAVE")) {
            avancar();

            while (!verificar("FECHA_CHAVE") && !verificar("EOF")) {
                declaracoes.add(parsearDeclaracao());
            }

            consumir("FECHA_CHAVE", "Esperado '}'");
        } else {
            // Bloco de uma única linha (sem chaves)
            declaracoes.add(parsearDeclaracao());
        }

        return new Block(declaracoes);
    }

    private FunctionDeclaration parsearDeclaracaoFuncao() {
        consumir("FUNCAO", "Esperado 'funcao'");

        String tipoRetorno = tokenAtual.getLexema();
        avancar();

        Token nomeToken = consumir("IDENTIFICADOR", "Esperado nome da função");
        String nome = nomeToken.getLexema();

        consumir("ABRE_PAREN", "Esperado '('");
        List<Parameter> parametros = new ArrayList<>();

        if (!verificar("FECHA_PAREN")) {
            parametros.add(parsearParametro());

            while (verificar("VIRGULA")) {
                avancar();
                parametros.add(parsearParametro());
            }
        }

        consumir("FECHA_PAREN", "Esperado ')'");
        Block corpo = parsearBloco();

        return new FunctionDeclaration(tipoRetorno, nome, parametros, corpo);
    }

    private Parameter parsearParametro() {
        String tipo = tokenAtual.getLexema();
        avancar();

        Token nomeToken = consumir("IDENTIFICADOR", "Esperado nome do parâmetro");
        String nome = nomeToken.getLexema();

        return new Parameter(tipo, nome);
    }

    private ReturnCommand parsearComandoRetornar() {
        consumir("RETORNAR", "Esperado 'retornar'");
        Expression expressao = parsearExpressao();
        consumir("PONTO_VIRGULA", "Esperado ';'");

        return new ReturnCommand(expressao);
    }

    private Expression parsearExpressao() {
        return parsearExpressaoLogica();
    }

    private Expression parsearExpressaoLogica() {
        Expression esquerda = parsearExpressaoRelacional();

        while (verificar("E_LOGICO") || verificar("OU_LOGICO")) {
            String operador = tokenAtual.getLexema();
            avancar();
            Expression direita = parsearExpressaoRelacional();
            esquerda = new BinaryExpression(esquerda, operador, direita);
        }

        return esquerda;
    }

    private Expression parsearExpressaoRelacional() {
        Expression esquerda = parsearExpressaoAritmetica();

        while (verificar("IGUAL_IGUAL") || verificar("DIFERENTE") ||
                verificar("MENOR") || verificar("MAIOR") ||
                verificar("MENOR_IGUAL") || verificar("MAIOR_IGUAL")) {
            String operador = tokenAtual.getLexema();
            avancar();
            Expression direita = parsearExpressaoAritmetica();
            esquerda = new BinaryExpression(esquerda, operador, direita);
        }

        return esquerda;
    }

    private Expression parsearExpressaoAritmetica() {
        Expression esquerda = parsearTermo();

        while (verificar("MAIS") || verificar("MENOS")) {
            String operador = tokenAtual.getLexema();
            avancar();
            Expression direita = parsearTermo();
            esquerda = new BinaryExpression(esquerda, operador, direita);
        }

        return esquerda;
    }

    private Expression parsearTermo() {
        Expression esquerda = parsearFator();

        while (verificar("MULT") || verificar("DIV")) {
            String operador = tokenAtual.getLexema();
            avancar();
            Expression direita = parsearFator();
            esquerda = new BinaryExpression(esquerda, operador, direita);
        }

        return esquerda;
    }

    private Expression parsearFator() {
        if (verificar("NUM")) {
            String valor = tokenAtual.getLexema();
            avancar();
            return new NumberLiteral(valor);
        }

        if (verificar("STRING")) {
            String valor = tokenAtual.getLexema();
            avancar();
            return new StringLiteral(valor);
        }

        if (verificar("VERDADEIRO") || verificar("FALSO")) {
            boolean valor = tokenAtual.getLexema().equals("verdadeiro");
            avancar();
            return new BooleanLiteral(valor);
        }

        if (verificar("IDENTIFICADOR")) {
            String nome = tokenAtual.getLexema();
            avancar();

            if (verificar("ABRE_PAREN")) {
                avancar();

                List<Expression> argumentos = CreateExpressionList();

                return new FunctionCall(nome, argumentos);
            }

            return new Identifier(nome);
        }

        if (verificar("ABRE_PAREN")) {
            avancar();
            Expression expressao = parsearExpressao();
            consumir("FECHA_PAREN", "Esperado ')'");
            return expressao;
        }

        if (verificar("NAO")) {
            String operador = tokenAtual.getLexema();
            avancar();
            Expression operando = parsearFator();
            return new UnitExpression(operador, operando);
        }

        if (verificar("MENOS")) {
            String operador = tokenAtual.getLexema();
            avancar();
            Expression operando = parsearFator();
            return new UnitExpression(operador, operando);
        }

        throw new RuntimeException("Expressão inválida: " + tokenAtual.getLexema());
    }

    private List<Expression> CreateExpressionList() {
        List<Expression> argumentos = new ArrayList<>();

        if (!verificar("FECHA_PAREN")) {
            argumentos.add(parsearExpressao());

            while (verificar("VIRGULA")) {
                avancar();
                argumentos.add(parsearExpressao());
            }
        }

        consumir("FECHA_PAREN", "Esperado ')'");
        return argumentos;
    }
}
