import re

# Definição dos tipos de token
TOKEN_REGEX = [
    ('OP', r'\be\b'),                   # operadores matemáticos
    ('NUMERO',    r'\d+'),                 # número inteiro
    ('DECIMAL',   r'\d+\.\d+'),            # número decimal
    ('PALAVRA',   r'"[^"]*"'),             # string entre aspas
    ('ID', r'[^\W\d_][\w]*'),              # identificadores
    ('VIRGULA',   r','),                   # ,
    ('PONTOEVIRGULA', r';'),               # ;
    ('ESPACO',    r'[ \t]+'),              # espaços (ignorados)
    ('QUEBRA',    r'\n'),                  # quebras de linha
]

# Combinação de tudo
TOKEN_REGEX_COMPILED = re.compile(
    '|'.join(f'(?P<{name}>{pattern})' for name, pattern in TOKEN_REGEX)
)

PALAVRAS_RESERVADAS = {
    'declare', 'como', 'com', 'valor', 'você', 'deve',
    'imprimir', 'ouvir', 'número', 'decimal', 'palavra',
    'valor', 'tenha', 'se', 'então', 'senão', 'enquanto',
    'faça', 'soma', 'subtracao','multiplicacao','divisao'
}

def analisar_linha(linha):
    pos = 0
    tokens = []

    while pos < len(linha):
        match = TOKEN_REGEX_COMPILED.match(linha, pos)
        if not match:
            raise SyntaxError(f"[Erro léxico] Símbolo inválido: '{linha[pos]}' na posição {pos}")

        tipo = match.lastgroup
        valor = match.group()

        if tipo == 'ESPACO' or tipo == 'QUEBRA':
            pass  # ignora
        elif tipo == 'ID' and valor in PALAVRAS_RESERVADAS:
            tokens.append(('RESERVADA', valor))
        else:
            tokens.append((tipo, valor))

        pos = match.end()

    return tokens

if __name__ == "__main__":
    exemplo = 'valor a e b, tenha soma;'
    print(exemplo)
    try:
        tokens = analisar_linha(exemplo)
        print("Tokens:")
        for tipo, valor in tokens:
            print(f"{tipo}: {valor}")
    except SyntaxError as e:
        print(str(e))