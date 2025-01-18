<?php
$vetor["N1"] = rand(1, 10);
$vetor["N2"] = rand(1, 10);
$vetor["N3"] = rand(1, 10);

foreach ($vetor as $nota) {
  $media += $nota;
}

$vetor["MG"] = $media / 3;

foreach ($vetor as $chave => $nota) {
  print($chave . " = " . $nota . " | ");
}
if ($vetor["MG"] >= 6) {
  print("Aprovado");
} else {
  print("Reprovado");
}
