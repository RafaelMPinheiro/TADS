<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Exercício 8</title>
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

  sort($valores, SORT_NUMERIC);
  foreach ($valores as $valor) {
    print($valor . " ");
  }
}
