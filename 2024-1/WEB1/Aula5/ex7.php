<?php
$a = rand(1, 100);
$b = rand(1, 100);
$soma = $a + $b;

print("a: " . $a . " b: " . $b . "<br>");
if ($soma > 20) {
  print($soma + 8);
} else {
  print($soma - 5);
}
