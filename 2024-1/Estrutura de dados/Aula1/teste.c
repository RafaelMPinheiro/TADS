#include <stdio.h>
#include <stdlib.h>

int main() {
  int i, f;
  printf("Digite um valor para i e f:\n");
  scanf("%i", &i);
  scanf("%i", &f);
  printf("valor de i:%d\nNa memoria %p\n", i, &i);
  printf("valor de f:%d\nNa memoria %p\n", f, &f);
  return 0;
}