<?php
$vetor = array();
for ($i = 0; $i < 5; $i++) {
  $vetor[] = rand(1, 100);
}

print("Vetor: ");
foreach ($vetor as $valor) {
  print($valor . " ");
  $soma += $valor;
}
print("<br>");

$media = $soma / count($vetor);
print("Média: " . $media . "<br>");

print("Valores acima da média: ");
foreach ($vetor as $valor) {
  if ($valor >= $media) print($valor . " ");
}
