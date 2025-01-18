<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Exercício 6</title>
</head>

<body>
  <form action="" method="get">
    Insira os valores: <br />
    <input name="v1" type="number" value="0" placeholder="Valor 1" /> <br />
    <input name="v2" type="number" value="0" placeholder="Valor 2" /> <br />
    <input name="v3" type="number" value="0" placeholder="Valor 3" /> <br />
    <input type="submit" value="Calcular" />
  </form>
</body>

</html>
<?php

if (isset($_GET['v1'])) {
  $valores = array();
  for ($i = 1; $i <= 3; $i++) {
    $valores[] = $_GET['v' . $i];
  }

  $maior = $_GET['v1'];
  $menor = $_GET['v1'];
  foreach ($valores as $valor) {
    if ($valor >= $maior) {
      $maior = $valor;
    }
    if ($valor <= $menor) {
      $menor = $valor;
    }
  }

  print("O maior valor é " . $maior . "<br/>");
  print("O menor valor é " . $menor);
}
