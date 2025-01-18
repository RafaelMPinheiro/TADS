#include <stdio.h>
#include <stdlib.h>

int main() {
  system("clear");
  double A, B, C;
  double *pS, *qS;

  pS = (double *)malloc(sizeof(int));

  printf("Insira a primeira idade: \n");
  scanf("%lf", &A);
  printf("Insira a segunda idade: \n");
  scanf("%lf", &B);

  *pS = A + B;

  printf("\nEndereço de pS: %p\n", pS);
  printf("Valor armazenado em pS: %lf\n", *pS);

  C = *pS;
  printf("\nC = *pS\n");
  printf("Valor armazenado em C: %lf\n", C);

  qS = pS;
  printf("\nqS = pS;\n");
  printf("Endereço de pS: %p\n", pS);
  printf("Endereço de qS: %p\n", qS);

  *qS += 100;
  printf("\n*qS += 100;\n");
  printf("Valor armazenado em qS: %lf\n", *qS);
  printf("Valor armazenado em pS: %lf\n", *pS);

  printf("\nValor armazenado em A: %lf\n", A);
  A = *qS;
  printf("A = *qS;\n");
  printf("Valor armazenado em A: %lf\n", A);

  qS = &B;
  printf("\nqS = &B;\n");
  printf("Endereço de qS: %p\n", qS);

  *qS -= 10;
  printf("\n*qS -= 10;\n");
  printf("Valor armazenado em A: %lf\n", A);
  printf("Valor armazenado em B: %lf\n", B);
}