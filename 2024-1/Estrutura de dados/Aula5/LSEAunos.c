#include <stdio.h>
#include <string.h>

typedef struct Aluno {
  char nome[20];
  int idade;
  int matricula;
  struct Aluno *proximo;
} Aluno;

typedef struct LSE {
  Aluno *primeiro;
  int n;
} LSE;

void criaLista(LSE *ls) {
  ls->primeiro = NULL;
  ls->n = 0;
}

void cadastraAluno(Aluno *aluno, char nome[], int idade, int matricula) {
  strcpy(aluno->nome, nome);
  aluno->idade = idade;
  aluno->matricula = matricula;
  aluno->proximo = NULL;
}

void insereInicio(LSE *ls, Aluno *aluno) {
  aluno->proximo = ls->primeiro;
  ls->primeiro = aluno;
  ls->n++;
}

void insereFim(LSE *ls, Aluno *aluno) {
  if (ls->primeiro == NULL) {
    insereInicio(ls, aluno);
  } else {
    Aluno *aux = ls->primeiro;
    while (aux->proximo != NULL) {
      aux = aux->proximo;
    }
    aux->proximo = aluno;
    ls->n++;
  }
}

Aluno *removeInicio(LSE *ls) {
  Aluno *aux = ls->primeiro;
  if (ls->primeiro != NULL) {
    ls->primeiro = aux->proximo;
    aux->proximo = NULL;
    ls->n--;
  }
  return aux;
}

Aluno *removeFim(LSE *ls) {
  Aluno *aux = ls->primeiro;
  Aluno *aux2;
  if (ls->primeiro == NULL) {
    return aux;
  } else {
    while (aux->proximo->proximo != NULL) {
      aux = aux->proximo;
    }
    aux2 = aux->proximo;
    aux->proximo = NULL;
    ls->n--;
  }
  return aux2;
}

void mostraAluno(Aluno aluno) {
  printf("\n Dados do Aluno:");
  printf("\n\t Nome: %s", aluno.nome);
  printf("\n\t Idade: %d", aluno.idade);
  printf("\n\t Matricula: %d\n", aluno.matricula);
}

void mostraLista(LSE ls) {
  Aluno *aux = ls.primeiro;
  int i = 0;
  printf("\n\n Mostra Lista LSE\n");
  while (aux != NULL) {
    printf("\n Elemento E%d", i++);
    mostraAluno(*aux);
    aux = aux->proximo;
  }
  printf("\n Fim da Lista \n");
}

int main() {
  LSE matematica;
  criaLista(&matematica);

  Aluno paulo;
  cadastraAluno(&paulo, "Paulo", 23, 12131);
  insereFim(&matematica, &paulo);

  Aluno maria;
  cadastraAluno(&maria, "Maria", 23, 13112);
  insereFim(&matematica, &maria);

  Aluno juca;
  cadastraAluno(&juca, "Juca", 31, 13113);
  insereFim(&matematica, &juca);

  LSE portugues;
  criaLista(&portugues);

  Aluno novosAlunos[1];
  cadastraAluno(&novosAlunos[0], "Luiz", 25, 12133);

  insereFim(&portugues, &novosAlunos[0]);

  Aluno rafael;
  cadastraAluno(&rafael, "Rafael", 31, 13113);
  insereFim(&matematica, &rafael);

  Aluno *alunoRemovido = removeInicio(&matematica);
  insereFim(&portugues, alunoRemovido);

  // mostraLista(matematica);
  mostraLista(portugues);

  alunoRemovido = removeFim(&portugues);

  mostraLista(portugues);
}
