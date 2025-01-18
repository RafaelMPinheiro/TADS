#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct aluno {
  char nome[100];
  int idade;
  int matricula;
  struct aluno *proximo;
} Aluno;

typedef struct lse {
  Aluno *primeiro;
  int n_elementos;
  char turma[20];
} LSE;

LSE *criaListaLSE(char turma[]) {
  LSE *nova = (LSE *)malloc(sizeof(LSE));
  nova->primeiro = NULL;
  nova->n_elementos = 0;
  strcpy(nova->turma, turma);
  return nova;
}

Aluno *cadastraAluno(char nome[], int idade, int matricula) {
  Aluno *novoAluno = (Aluno *)malloc(sizeof(Aluno));
  strcpy(novoAluno->nome, nome);
  novoAluno->idade = idade;
  novoAluno->matricula = matricula;
  novoAluno->proximo = NULL;
  return novoAluno;
}

Aluno *lerAluno() {
  Aluno *novoAluno = (Aluno *)malloc(sizeof(Aluno));
  printf("\n Informe o nome do novo aluno:");
  scanf("%s", novoAluno->nome);
  printf("\n Informe a idade do novo aluno:");
  scanf("%d", &novoAluno->idade);
  printf("\n Informe a matricula do novo aluno:");
  scanf("%d", &novoAluno->matricula);
  novoAluno->proximo = NULL;
  return novoAluno;
}

void mostraAluno(Aluno *aluno) {
  if (aluno != NULL) {
    printf("\t Nome:%s", aluno->nome);
    printf("\t Idade:%d", aluno->idade);
    printf("\t Matricula:%d", aluno->matricula);
  } else {
    printf("\n Erro ao imprimir Aluno Valor = NULL\n");
  }
}

void mostraLista(LSE *ls) {
  Aluno *aux = ls->primeiro;
  printf("\n\n Mostra a Lista de %s:", ls->turma);
  int contador = 1;
  while (aux != NULL) {
    printf("\n\t Id:%d", contador);
    mostraAluno(aux);
    aux = aux->proximo;
    contador++;
  }
  printf("\n Fim da Lista!!\n");
}

void insereNoInicio(LSE *ls, Aluno *novo) {
  if (ls->primeiro == NULL)
    novo->proximo = NULL;
  else
    novo->proximo = ls->primeiro;
  ls->primeiro = novo;
  ls->n_elementos++;
  printf("\n Elemento %s inserido com sucesso!!!", novo->nome);
}

void insereNoFim(LSE *ls, Aluno *novo) {
  Aluno *aux = ls->primeiro;
  if (aux == NULL)
    insereNoInicio(ls, novo);
  else {
    while (aux->proximo != NULL) {
      aux = aux->proximo;
    }
    novo->proximo = NULL;
    aux->proximo = novo;
    ls->n_elementos++;
  }
}

// insere o novo aluno na turma na posicao "pos";
void insereNaPosicao(LSE *ls, Aluno *novo, int pos) {
  Aluno *aux = ls->primeiro;
  if (aux == NULL || pos == 1)
    insereNoInicio(ls, novo);
  else {
    for (int i = 1; i < pos - 1; i++) {
      if (aux->proximo == NULL)
        break;
      aux = aux->proximo;
    }
    novo->proximo = aux->proximo;
    aux->proximo = novo;
    ls->n_elementos++;
  }
}

Aluno *removeNoInicio(LSE *ls) {
  Aluno *aux = ls->primeiro;
  if (aux == NULL) {
    printf("\n\t Erro - Lista Vazia!!");
    return NULL;
  } else {
    Aluno *auxReturn = aux;
    ls->primeiro = aux->proximo;
    ls->n_elementos--;
  }
  return aux;
}

Aluno *removeNoFim(LSE *ls) {
  Aluno *aux = ls->primeiro;
  if (aux == NULL) {
    printf("\n\t Erro - Lista Vazia!!");
    return NULL;
  } else {
    while (aux->proximo->proximo != NULL) {
      aux = aux->proximo;
    }
    Aluno *auxReturn = aux->proximo;
    aux->proximo = NULL;
    ls->n_elementos--;
    return auxReturn;
  }
}

Aluno *removeNaPosicao(LSE *ls, int pos) {
  Aluno *aux = ls->primeiro;
  if (aux == NULL) {
    printf("\n\t Erro - Lista Vazia!!");
    return NULL;
  } else if (pos == 1) {
    return removeNoInicio(ls);
  } else {
    for (int i = 1; i < pos - 1; i++) {
      if (aux == NULL || aux->proximo == NULL) {
        printf("\n\t Erro - Posição Inválida!!\n");
        return NULL;
      }
      aux = aux->proximo;
    }
    Aluno *aux2 = aux->proximo;
    aux->proximo = aux2->proximo;

    return aux2;
  }
}

int retornaQuantidade(LSE *ls) {
  Aluno *aux = ls->primeiro;
  int alunos = 0;
  if (aux == NULL)
    return alunos;
  else {
    alunos++;
    while (aux->proximo != NULL) {
      aux = aux->proximo;
      alunos++;
    }
    return alunos;
  }
}

void mostraPosicao(LSE *ls, int pos) {
  Aluno *aux = ls->primeiro;
  for (int i = 1; i < pos; i++) {
    if (aux->proximo == NULL) {
      printf("Não tem aluno na posição %d", pos);
      mostraLista(ls);
      return;
    }
    aux = aux->proximo;
  }
  mostraAluno(aux);
}

void apagaLista(LSE *ls) {
  if (ls->primeiro == NULL) {
    printf("\n\t Erro - Lista Vazia!!\n");
    return;
  }

  Aluno *aux = ls->primeiro;
  ls->primeiro = NULL;
  while (aux != NULL) {
    Aluno *aux2 = aux->proximo;
    free(aux);
    aux = aux2;
  }
  ls->n_elementos = 0;
}

void menuTesteLista(LSE *lse) {
  int op = 0, posicao = 0;
  Aluno *aux = NULL;
  printf("\n Menu de operações sobre um LSE:\n");
  printf("\n\t 1 - Insere no Início:");
  printf("\n\t 2 - Insere no Fim:");
  printf("\n\t 3 - Insere na Posição:");
  printf("\n\t 4 - Remove no Início:");
  printf("\n\t 5 - Remove no Fim:");
  printf("\n\t 6 - Remove na Posição:");
  printf("\n\t 7 - Mostra Lista:");
  printf("\n\t 8 - Mostra Aluno na Posicao:");
  printf("\n\t 9 - Apaga Lista:");
  printf("\n\t 0 - Para Sair da Função Menu:");
  printf("\n\t Informe a opção:");
  scanf("%d", &op);
  switch (op) {
  case 1:
    printf("\n\t Função Insere no Início!!");
    insereNoInicio(lse, lerAluno());
    break;
  case 2:
    printf("\n\t Função Insere no Fim!!");
    insereNoFim(lse, lerAluno());
    break;
  case 3:
    printf("\n\t Função Insere na Posição!!");
    printf("\n\t\t Informe a posição:");
    scanf("%d", &posicao);
    Aluno aluno = *lerAluno();
    insereNaPosicao(lse, &aluno, posicao);
    break;
  case 4:
    printf("\n\t Função remove  no Início:");
    aux = removeNoInicio(lse);
    if (aux != NULL) {
      mostraAluno(aux);
      free(aux);
    }
    break;
  case 5:
    printf("\n\t Função remove  no FIM:");
    aux = removeNoFim(lse);
    free(aux);
    break;
  case 6:
    printf("\n\t Função Remove na Posição!!");
    printf("\n\t\t Informe a posição:");
    scanf("%d", &posicao);
    aux = removeNaPosicao(lse, posicao);
    free(aux);
    break;
  case 7:
    printf("\n\n Mostra Lista %s!!!", lse->turma);
    mostraLista(lse);
    break;
  case 8:
    printf("\n\t Função Mostra um Aluno na Posicao - Pos!!");
    printf("\n\t\t Informe a posição:");
    scanf("%d", &posicao);
    mostraPosicao(lse, posicao);
    break;
  case 9:
    printf("\n\t Função Apaga toda Lista!");
    apagaLista(lse);
    break;
  case 0:
    printf("\n\n *** Fim do Programa!!! ***\n");
    break;
  default:
    printf("\n\n *** Opção Inválida!!! ***\n");
  }
  if (op > 0 && op < 10)
    menuTesteLista(lse);
}

void trocaTurmaPorMat(LSE *lmatematica, LSE *lportugues) {
  printf("\n\t Trocar um aluno da turma de Portugues para turma de Matematica");
  printf("\n\t Informe a posição do aluno:");
}

void trocaTurmaMatPor(LSE *lmatematica, LSE *lportugues) {
  printf("\n\t Trocar um aluno de turma de Matematica para turma de Portugues");
  printf("\n\t Informe a posição do aluno:");
}

void mostrarTurmaPorMat(LSE *lmatematica, LSE *lportugues) {
  printf("\n\t Mostrar as listas das turmas de Portugues e Matematica");
  mostraLista(lmatematica);
  mostraLista(lportugues);
}

void menuTrocaTurmas(LSE *lmatematica, LSE *lportugues) {
  int op = 0, posicao = 0;
  Aluno *aux = NULL;
  printf("\n Menu de operações Troca Turmas:\n");
  printf("\n Escolha um Opção:");
  printf("\n\t 1 - Trocar um aluno de Portugues para Matematica:");
  printf("\n\t 2 - Trocar um aluno de Matemática para Portugues:");
  printf("\n\t 3 - Mostrar as Turmas de Portugues e Matematica:");
  printf("\n\t 0 - Para Sair do Programa:");
  printf("\n\t Informe a opção:");
  scanf("%d", &op);
  switch (op) {
  case 1:
    trocaTurmaPorMat(lmatematica, lportugues);
    break;
  case 2:
    trocaTurmaMatPor(lmatematica, lportugues);
    break;
  case 3:
    mostrarTurmaPorMat(lmatematica, lportugues);
    break;
  case 0:
    printf("\n\n *** Fim do Programa!!! ***\n");
    break;
  default:
    printf("\n\n *** Opção Inválida!!! ***\n");
  }
  if (op > 0 && op < 4)
    menuTrocaTurmas(lmatematica, lportugues);
}

int main() {
  system("clear");
  LSE *listaMatematica = criaListaLSE("Matematica");

  Aluno *pedro = cadastraAluno("Pedro", 44, 1123301);
  insereNoInicio(listaMatematica, pedro);

  Aluno *novo = cadastraAluno("Joao", 21, 212121);
  insereNoInicio(listaMatematica, novo);

  novo = cadastraAluno("Rafael", 23, 232323);
  insereNoInicio(listaMatematica, novo);

  novo = cadastraAluno("Paulo", 18, 181818);
  insereNoInicio(listaMatematica, novo);

  mostraLista(listaMatematica);
  printf("\n");

  menuTesteLista(listaMatematica);

  LSE *listaPortugues = criaListaLSE("Portugues");
  menuTrocaTurmas(listaMatematica, listaPortugues);
}
