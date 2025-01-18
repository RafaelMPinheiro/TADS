<?php
$vetor = array();
for ($i = 0; $i < 15; $i++) {
  $vetor[] = rand(1, 100);
}

foreach ($vetor as $valor) {
  print($valor);
  if ($valor % 2 == 0) {
    print(" é par");
  } else {
    print(" é impar");
  }
  print("<br>");
}
