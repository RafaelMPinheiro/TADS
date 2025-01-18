<?php
$vetor = array();
for ($i = 0; $i < 5; $i++) {
  $vetor[] = rand(1, 100);
}
$maior = $vetor[0];
$menor = $vetor[0];
$pares = 0;
$soma = 0;

foreach ($vetor as $valor) {
  print($valor . " ");
  if ($valor >= $maior) {
    $maior = $valor;
  }

  if ($valor <= $menor) {
    $menor = $valor;
  }

  if ($valor % 2 == 0) {
    $pares++;
  }

  $soma += $valor;
}

print("<br>");
print("Maior valor: " . $maior . "<br>");
print("Menor valor: " . $menor . "<br>");
print("Percentual de pares: " . ($pares * 100) / 20 . "%<br>");
print("Média dos elementos: " . $soma / count($vetor));
