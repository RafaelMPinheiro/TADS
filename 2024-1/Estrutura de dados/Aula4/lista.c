#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/_types/_null.h>

typedef struct NodeInt {
  int dado;
  struct NodeInt *prox;
} NodeInt;

void adicionaValor(NodeInt **lista, int valor) {
  NodeInt *ultimo = *lista;

  NodeInt *novo = (NodeInt *)malloc(sizeof(NodeInt));
  novo->prox = NULL;
  novo->dado = valor;

  if (*lista == NULL) {
    *lista = novo;
    return;
  }

  while (ultimo->prox != NULL) {
    ultimo = ultimo->prox;
  }
  ultimo->prox = novo;
}

void printLista(NodeInt *lista) {
  if (lista == NULL) {
    printf("Lista vazia!\n");
  }

  while (lista != NULL) {
    printf("%d\n", lista->dado);
    lista = lista->prox;
  }
}

int main() {
  NodeInt *listaValores = NULL;
  printLista(listaValores);

  adicionaValor(&listaValores, 10);
  adicionaValor(&listaValores, 20);
  adicionaValor(&listaValores, 30);
  printLista(listaValores);

  printf("Adicionando mais valores!\n");
  adicionaValor(&listaValores, 40);
  adicionaValor(&listaValores, 50);
  printLista(listaValores);
}