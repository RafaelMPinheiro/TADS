#include <stdio.h>
#include <stdlib.h>

int main() {
  system("clear");
  int *pS;
  int A, B, S;
  pS = &S;

  printf("Insira a primeira idade: \n");
  scanf("%d", &A);
  printf("Insira a segunda idade: \n");
  scanf("%d", &B);

  S = A + B;
  printf("\nEndereço de S: %p\n", &S);
  printf("Valor armazenado em S: %d\n", S);

  printf("\nEndereço de pS: %p\n", pS);
  printf("Valor armazenado em pS: %d\n", *pS);

  printf("\nEndereço de A: %p\nEndereço de B: %p\n", &A, &B);
}