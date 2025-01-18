# Resumo sobre a Linguagem C

## Introdução

A linguagem de programação C é uma linguagem de propósito geral amplamente utilizada. Foi desenvolvida por Dennis Ritchie na década de 1970 na Bell Labs.

## Características Principais

- **Simplicidade**: C é uma linguagem simples, com um conjunto reduzido de palavras-chave e construções sintáticas.
- **Eficiência**: C permite um controle próximo sobre o hardware do computador e oferece uma alta performance.
- **Portabilidade**: Os programas escritos em C podem ser compilados e executados em diferentes sistemas operacionais com poucas modificações.
- **Flexibilidade**: C suporta programação procedural e também possui recursos para programação estruturada.

## Estrutura Básica

Um programa em C geralmente consiste em uma ou mais funções, sendo que uma delas, chamada `main()`, é obrigatória e é o ponto de entrada do programa.

Exemplo de programa em C:

```c
#include <stdio.h>

int main() {
    printf("Olá, mundo!\n");
    return 0;
}
```

## Compilação e Execução

Os programas em C são escritos em arquivos de texto com a extensão `.c`. Eles precisam ser compilados para gerar um arquivo executável.

- **Compilação**: `gcc programa.c -o programa`
- **Execução**: `./programa`

## Tipos de Dados

C suporta diversos tipos de dados, incluindo inteiros, reais, caracteres, ponteiros, entre outros.

Exemplo:
```c
int idade = 25; 
float altura = 1.75; 
char genero = 'M';
```

## Controle de Fluxo

C oferece estruturas de controle de fluxo como `if`, `else`, `while`, `for`, `switch`, entre outras.

Exemplo:
```c
int numero = 10;
if (numero > 0) {
    printf("O número é positivo\n");
} else {
    printf("O número é negativo\n");
}
```

## Ponteiros

Ponteiros são uma característica fundamental em C. Eles armazenam endereços de memória e são frequentemente usados para manipular arrays e estruturas de dados de forma eficiente.

Exemplo:
```c
int x = 10;
int *ptr = &x; // ptr agora contém o endereço de x
```

## Bibliotecas Padrão

C possui um conjunto de bibliotecas padrão que oferecem funcionalidades como entrada/saída, manipulação de strings, alocação de memória, entre outras.

Exemplo:
```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
```

## [[Aula 2 - ED]]
