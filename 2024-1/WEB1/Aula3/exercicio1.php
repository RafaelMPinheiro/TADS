<?php
$nota1 = 7;
$nota2 = 8.5;
$nota3 = 6;

$media = ($nota1 + $nota2 + $nota3) / 3;

echo "<h1>Sua nota ficou ", number_format($media, 1, '.', ','), "</h1>";
if ($media >= 6) {
  echo "<h1>Parabéns você foi aprovado!!</h1>";
} else {
  echo "<h1>Infelizmente não foi desta vez!!</h1>";
}
