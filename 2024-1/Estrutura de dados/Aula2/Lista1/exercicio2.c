#include <stdio.h>
#include <stdlib.h>

int main() {
  system("clear");
  int A, B;
  int *pS;

  pS = (int *)malloc(sizeof(int));

  printf("Insira a primeira idade: \n");
  scanf("%d", &A);
  printf("Insira a segunda idade: \n");
  scanf("%d", &B);

  *pS = A + B;

  printf("\nEndereço de pS: %p\n", pS);
  printf("Valor armazenado em pS: %d\n", *pS);

  printf("Resultado de A+B: %d\n", (A + B));

  free(pS);
}