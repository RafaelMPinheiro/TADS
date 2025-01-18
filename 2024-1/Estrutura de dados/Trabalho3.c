#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

typedef struct pino {
  char cor[100];
  struct pino *proximo;
} Pino;

typedef struct coluna {
  Pino *primeiro;
  int n_elementos;
} Coluna;

void insereNoInicio(Coluna *coluna, Pino *pino) {
  if (coluna->n_elementos >= 4)
    return;
  if (coluna->primeiro == NULL)
    pino->proximo = NULL;
  else
    pino->proximo = coluna->primeiro;
  coluna->primeiro = pino;
  coluna->n_elementos++;
}

void removerNoInicio(Coluna *coluna, Pino *pino) {
  if (coluna->n_elementos == 0) {
    return;
  }
  coluna->primeiro = coluna->primeiro->proximo;
  coluna->n_elementos--;
}

void trocarPino(Coluna **colunas, int colunaSaida, int colunaEntrada) {
  if (colunaEntrada > 3 && colunas[colunaEntrada]->n_elementos > 0) {
    printf("\nColuna aux cheia!\n");
    return;
  }
  if (colunas[colunaEntrada]->n_elementos >= 4) {
    printf("\nColuna invalida!\n");
    return;
  }
  if (colunas[colunaSaida]->n_elementos == 0) {
    printf("\nColuna vazia!\n");
    return;
  }
  Pino *aux = colunas[colunaSaida]->primeiro;
  removerNoInicio(colunas[colunaSaida], aux);
  insereNoInicio(colunas[colunaEntrada], aux);
}

void distribuirPinos(Coluna **colunas) {
  char *cores[] = {"Vermelho", "Amarelo", "Azul", "Verde"};
  int quantidadePorCor[4] = {0, 0, 0, 0};

  int totalPinos = 12;
  for (int i = 0; i < totalPinos; i++) {
    Pino *novoPino = (Pino *)malloc(sizeof(Pino));
    int corAleatoria;

    do {
      corAleatoria = rand() % 4;
    } while (quantidadePorCor[corAleatoria] >= 3);

    strcpy(novoPino->cor, cores[corAleatoria]);
    quantidadePorCor[corAleatoria]++;
    novoPino->proximo = NULL;

    int colunaAleatoria;
    do {
      colunaAleatoria = rand() % 4;
    } while (colunas[colunaAleatoria]->n_elementos >= 3);

    insereNoInicio(colunas[colunaAleatoria], novoPino);
  }
}

Coluna **inicializarColunas(int nColunas) {
  Coluna **colunas = (Coluna **)malloc(nColunas * sizeof(Coluna *));
  for (int i = 0; i < nColunas; i++) {
    colunas[i] = (Coluna *)malloc(sizeof(Coluna));
    colunas[i]->primeiro = NULL;
    colunas[i]->n_elementos = 0;
  }
  return colunas;
}

void mostrarColunas(Coluna **colunas, int nColunas) {
  for (int i = 0; i < nColunas; i++) {
    if (i >= 4)
      printf("\nColuna aux %d:\n", i + 1);
    else
      printf("\nColuna %d:\n", i + 1);
    Pino *p = colunas[i]->primeiro;
    while (p != NULL) {
      printf("  %s\n", p->cor);
      p = p->proximo;
    }
  }
}

int checarVitoria(Coluna **colunas) {
  for (int i = 0; i < 4; i++) {
    if (colunas[i]->n_elementos != 3) {
      return 0;
    }

    Pino *p = colunas[i]->primeiro;
    char *cor = p->cor;

    while (p != NULL) {
      if (strcmp(p->cor, cor) != 0) {
        return 0;
      }
      p = p->proximo;
    }
  }
  return 1;
}

void jogada(Coluna **colunas, int nColunas) {
  time_t inicio = time(NULL);
  printf("\ec\e[3J");
  mostrarColunas(colunas, nColunas);
  do {
    int colunaSaida = 0, colunaEntrada = 0;
    printf("\n\t Qual coluna desejas tirar?");
    scanf("%d", &colunaSaida);
    printf("\n\t Qual coluna desejas colocar?");
    scanf("%d", &colunaEntrada);
    trocarPino(colunas, colunaSaida - 1, colunaEntrada - 1);

    printf("\ec\e[3J");
    mostrarColunas(colunas, nColunas);
  } while (checarVitoria(colunas) != 1);
  printf("\n *** Parabéns! Você venceu o jogo! ***\n");
  printf("\n *** Você demorou %ld segundos. ***\n", time(NULL) - inicio);
}

int main() {
  int nColunas = 6, op = 0;
  printf("\n Dificuldade do jogo:\n");
  printf("\n\t 1 - Fácil");
  printf("\n\t 2 - Médio");
  printf("\n\t 3 - Dificil");
  printf("\n\t 0 - Para Sair do jogo\n");
  scanf("%d", &op);
  switch (op) {
  case 1:
    break;
  case 2:
    nColunas = 5;
    break;
  case 3:
    nColunas = 4;
    break;
  case 0:
    printf("\n\n *** Fim do Programa!!! ***\n");
    break;
  default:
    printf("\n\n *** Opção Inválida!!! ***\n");
    break;
  }

  if (op > 0 && op < 4) {
    Coluna **colunas = inicializarColunas(nColunas);
    distribuirPinos(colunas);

    jogada(colunas, nColunas);
  }
  return 0;
}