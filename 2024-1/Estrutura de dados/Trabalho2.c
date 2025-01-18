#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/_types/_null.h>

typedef struct cliente {
  char nome[20];
  int idade;
  int operacao;
  float valor;
  int id;
  struct cliente *proximo;
} Cliente;

typedef struct fila {
  Cliente *inicio;
  Cliente *fim;
  int qtd;
} Fila;

float caixa = 1000;

Cliente *lerCliente() {
  Cliente *novoCliente = (Cliente *)malloc(sizeof(Cliente));
  printf("\n Informe o nome do novo cliente:");
  scanf("%s", novoCliente->nome);
  printf("\n Informe a idade do novo cliente:");
  scanf("%d", &novoCliente->idade);
  novoCliente->proximo = NULL;
  return novoCliente;
}

Cliente *criaCliente(char *nome, int idade) {
  Cliente *cliente = (Cliente *)malloc(sizeof(Cliente));
  strcpy(cliente->nome, nome);
  cliente->idade = idade;
  return cliente;
}

void adicionarFila(Fila *fila, Cliente *cliente) {
  if (fila->fim == NULL) {
    fila->inicio = cliente;
    fila->fim = cliente;
    fila->qtd++;
    cliente->id = fila->qtd;
  } else {
    fila->fim->proximo = cliente;
    fila->fim = cliente;
    fila->qtd++;
    cliente->id = fila->qtd;
  }
};

void printCliente(Cliente *c) {
  if (c != NULL) {
    printf("ID: %d\n", c->id);
    printf("Nome: %s\n", c->nome);
    printf("Idade: %d\n", c->idade);
    printf("Operacao: %d\n", c->operacao);
    printf("Valor: %.2f\n", c->valor);
    printf("---------------------\n");
  }
}

void printFila(Fila *f) {
  if (f != NULL && f->inicio != NULL) {
    Cliente *atual = f->inicio;
    while (atual != NULL) {
      printCliente(atual);
      atual = atual->proximo;
    }
  } else {
    printf("A fila está vazia.\n");
  }
}

void lerOperacaoCaixa(Cliente *cliente) {
  int op = 0;
  float transicao = 0;
  printf("\n Menu de recurso:\n");
  printf("\n\t 1 - Saque:");
  printf("\n\t 2 - Deposito:");
  printf("\n\t Informe a opção:");
  scanf("%d", &op);
  switch (op) {
  case 1:
    printf("\n\tQual o valor do saque:");
    scanf("%f", &transicao);
    cliente->operacao = 1;
    cliente->valor = transicao;
    caixa -= transicao;
    break;
  case 2:
    printf("\n\tQual o valor do deposito:");
    scanf("%f", &transicao);
    cliente->operacao = 2;
    cliente->valor = transicao;
    caixa += transicao;
    break;
  default:
    printf("\n\n *** Opção Inválida!!! ***\n");
  }
  if (caixa < 0) {
    printf("\n\nSaldo – Negativo no caixa\n\n");
    caixa += 1000;
  }
}

Cliente *chamarCliente(Fila *fila1, Fila *fila2) {
  Fila *filaParaChamar = NULL;

  if (fila1->inicio != NULL &&
      (fila2->inicio == NULL || fila1->inicio->id < fila2->inicio->id)) {
    filaParaChamar = fila1;
    printf("Chamar lista geral\n");
  } else if (fila2->inicio != NULL) {
    filaParaChamar = fila2;
    printf("Chamar lista prioritario\n");
  }

  if (filaParaChamar != NULL) {
    printCliente(filaParaChamar->inicio);
    Cliente *aux = filaParaChamar->inicio;
    filaParaChamar->inicio = aux->proximo;
    filaParaChamar->qtd--;
    if (filaParaChamar->inicio == NULL) {
      filaParaChamar->fim = NULL;
    }
    aux->proximo = NULL;
    return aux;
  } else {
    printf("Todas as filas estão vazias.\n");
    return NULL;
  }
}

void menuPainel(Fila *filas[6]) {
  int op = 0;
  Cliente *aux = NULL;
  printf("\n Menu de recurso:\n");
  printf("\n\t 1 - Chamar fila caixa:");
  printf("\n\t 2 - Chamar fila gerente:");
  printf("\n\t 3 - Print filas:");
  printf("\n\t 4 - Caixa logs:");
  printf("\n\t 5 - Gerente logs:");
  printf("\n\t 0 - Para voltar Função Menu:");
  printf("\n\t Informe a opção:");
  scanf("%d", &op);
  switch (op) {
  case 1:
    aux = chamarCliente(filas[0], filas[1]);
    lerOperacaoCaixa(aux);
    adicionarFila(filas[4], aux);
    break;
  case 2:
    aux = chamarCliente(filas[2], filas[3]);
    break;
  case 3:
    printf("Caixa (%d)\n", filas[0]->qtd);
    printFila(filas[0]);
    printf("Caixa prioritário (%d)\n", filas[1]->qtd);
    printFila(filas[1]);
    printf("Gerente (%d)\n", filas[2]->qtd);
    printFila(filas[2]);
    printf("Gerente prioritário (%d)\n", filas[3]->qtd);
    printFila(filas[3]);
    break;
  case 4:
    printf("\n\nCaixa logs:\n");
    printf("Saldo caixa: %f\n", caixa);
    printFila(filas[4]);
    break;
  case 5:
    printf("\n\nGerente logs:\n");
    printFila(filas[5]);
    break;
  case 0:
    printf("\n\n *** Fim do Programa!!! ***\n");
    break;
  default:
    printf("\n\n *** Opção Inválida!!! ***\n");
  }
  if (op > 0 && op <= 5)
    menuPainel(filas);
}

void menuClienteLista(Fila *filas[6]) {
  int op = 0;
  Cliente *aux = NULL;
  printf("\n Menu de recurso:\n");
  printf("\n\t 1 - Fila caixa:");
  printf("\n\t 2 - Fila gerente:");
  printf("\n\t 3 - Print filas:");
  printf("\n\t 4 - Sistema painel:");
  printf("\n\t 0 - Para Sair da Função Menu:");
  printf("\n\t Informe a opção:");
  scanf("%d", &op);
  switch (op) {
  case 1:
    aux = lerCliente();
    if (aux->idade > 60)
      adicionarFila(filas[1], aux);
    else
      adicionarFila(filas[0], aux);
    break;
  case 2:
    aux = lerCliente();
    if (aux->idade > 60)
      adicionarFila(filas[3], aux);
    else
      adicionarFila(filas[2], aux);
    break;
  case 3:
    printf("Caixa (%d)\n", filas[0]->qtd);
    printFila(filas[0]);
    printf("Caixa prioritário (%d)\n", filas[1]->qtd);
    printFila(filas[1]);
    printf("Gerente (%d)\n", filas[2]->qtd);
    printFila(filas[2]);
    printf("Gerente prioritário (%d)\n", filas[3]->qtd);
    printFila(filas[3]);
    break;
  case 4:
    menuPainel(filas);
    break;
  case 0:
    printf("\n\n *** Fim do Programa!!! ***\n");
    break;
  default:
    printf("\n\n *** Opção Inválida!!! ***\n");
  }
  if (op > 0 && op <= 4)
    menuClienteLista(filas);
}

int main() {
  Fila *filaCaixa = (Fila *)malloc(sizeof(Fila));
  Fila *filaCaixaLogs = (Fila *)malloc(sizeof(Fila));
  Fila *filaCaixaPrioritario = (Fila *)malloc(sizeof(Fila));
  Fila *filaGerente = (Fila *)malloc(sizeof(Fila));
  Fila *filaGerentePrioritario = (Fila *)malloc(sizeof(Fila));
  Fila *filaGerenteLogs = (Fila *)malloc(sizeof(Fila));

  Fila *filas[6] = {filaCaixa,     filaCaixaPrioritario,
                    filaGerente,   filaGerentePrioritario,
                    filaCaixaLogs, filaGerenteLogs};

  char *nomes[] = {"Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda"};
  int idades[] = {25, 34, 45, 52, 63, 29};

  adicionarFila(filas[0], criaCliente(nomes[0], idades[0]));
  adicionarFila(filas[2], criaCliente(nomes[1], idades[1]));
  adicionarFila(filas[2], criaCliente(nomes[2], idades[2]));
  adicionarFila(filas[0], criaCliente(nomes[3], idades[3]));
  adicionarFila(filas[3], criaCliente(nomes[4], idades[4]));
  adicionarFila(filas[0], criaCliente(nomes[5], idades[5]));

  menuClienteLista(filas);

  printf("\n\nCaixa logs:\n");
  printf("\tSaldo caixa: %f\n", caixa);
  printFila(filas[4]);

  printf("\n\nGerente logs:\n");
  printFila(filas[5]);
}