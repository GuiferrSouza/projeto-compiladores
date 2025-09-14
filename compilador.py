import re
import sys
import random

def compilar_linha(linha):
    linha = linha.strip()

    if linha == "" or linha.startswith("#"):
        return ""  # ignora linha vazia ou comentário

    # Declaração
    match = re.match(r"declare (\w+) como (número|decimal|palavra), com (.+?) você deve;", linha)
    if match:
        nome, tipo, valor = match.groups()
        if tipo == "palavra":
            return f'{nome} = {valor}'
        elif tipo == "decimal":
            return f'{nome} = float({valor})'
        else:  # número
            return f'{nome} = int({valor})'

    # Atribuição
    match = re.match(r"valor (.+?), tenha (\w+);", linha)
    if match:
        expr, nome = match.groups()
        return f'{nome} = {expr}'

    # Impressão
    match = re.match(r'imprimir (.+?), você deve;', linha)
    if match:
        expr = match.group(1).strip()
        return f'print({expr})'

    # Leitura
    match = re.match(r"ouvir (\w+) você deve;", linha)
    if match:
        nome = match.group(1)
        return f'{nome} = input()'

    return f'# [ERRO] Não sei o que fazer com: {linha}'

def compilar_arquivo(caminho_entrada, caminho_saida):
    with open(caminho_entrada, 'r', encoding='utf-8') as entrada:
        linhas = entrada.readlines()

    codigo_saida = []

    mensagens_finais = [
        '"Que a Força esteja com você."',
        '"Muito a aprender, você ainda tem."',
        '"O fim, este não é. Apenas o começo, é."',
        '"Difícil de ver. Sempre em movimento está o futuro."',
        '"Grande guerreiro, você se tornou."']

    for linha in linhas:
        traduzida = compilar_linha(linha)
        codigo_saida.append(traduzida)

    codigo_saida.append(f'print({random.choice(mensagens_finais)})')
    
    with open(caminho_saida, 'w', encoding='utf-8') as saida:
        saida.write('\n'.join([linha for linha in codigo_saida if linha.strip() != ""]))

    print(f"[Yoda]: Gerado, o arquivo '{caminho_saida}' foi.")

# Execução via terminal
if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Uso: python compilador.py entrada.yoda -o saida.py")
        sys.exit(1)

    caminho_entrada = sys.argv[1]

    if "-o" in sys.argv:
        caminho_saida = sys.argv[sys.argv.index("-o") + 1]
    else:
        caminho_saida = "saida.py"

    compilar_arquivo(caminho_entrada, caminho_saida)
