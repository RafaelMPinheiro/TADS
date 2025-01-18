# Resumo sobre Ponteiros e Alocação de Memória em C

## Memória RAM - Ex

- A memória RAM é onde os programas em execução são armazenados temporariamente.
- Os dados na memória RAM são acessados por meio de endereços.
- Cada variável em um programa C ocupa espaço na memória RAM, e cada espaço tem um endereço único.

## Ponteiros

- Ponteiros são variáveis que armazenam endereços de memória como valor.
- Em C, os ponteiros são usados para acessar e manipular diretamente os dados na memória.
- Eles são essenciais para a alocação dinâmica de memória e para trabalhar com arrays e estruturas de dados complexas.

Exemplo:
```c
int x = 10;
int *ptr = &x; // ptr agora contém o endereço de x
```

## Operador ' * '

- O operador ' * ' é usado para desreferenciar um ponteiro, ou seja, acessar o valor armazenado no endereço apontado pelo ponteiro.
- Também é usado para declarar ponteiros.

## Troca de Valores

- Ponteiros podem ser usados para trocar valores entre variáveis.
- Isso é útil quando se deseja alterar o valor de uma variável em uma função e manter essa mudança após o retorno da função.

## Ponteiro NULL

- O ponteiro NULL é um ponteiro que não aponta para nenhum lugar válido na memória.
- É comumente usado para indicar que um ponteiro não está apontando para nenhum valor útil.

## Alocação Dinâmica de Memória

- Em C, a alocação dinâmica de memória é feita usando as funções `malloc()`, `calloc()` ou `realloc()`.
- Isso permite que o programa reserve memória durante a execução, o que é útil para estruturas de dados de tamanho variável.

Exemplo:
```c
int *arr = (int *)malloc(5 * sizeof(int)); // Aloca um array de inteiros com tamanho 5
```

## Comando "Free"

- O comando `free()` é usado para liberar a memória alocada dinamicamente quando ela não é mais necessária.
- Falhar em liberar a memória pode levar a vazamentos de memória, onde o programa continua alocando memória sem liberá-la, resultando em um uso excessivo de recursos do sistema.

## Uso de Ponteiros com Scanf

- O scanf() pode ser usado com ponteiros para ler valores diretamente na memória apontada pelos ponteiros.
- Isso é útil quando se deseja armazenar entrada do usuário em variáveis sem ter que passar por variáveis intermediárias.

Exemplo:
```c
int num;
scanf("%d", &num); // Lê um número inteiro e armazena na variável 'num'
```

## [[Exercícios aula 2]]