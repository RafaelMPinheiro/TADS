<?php
$nome = "Rafael";
$idade = rand(1, 50);

if ($idade < 18) {
  print($nome . " não é maior de 18 e tem " . $idade . " anos");
} else {
  print($nome . " é maior de 18 e tem " . $idade . " anos");
}
