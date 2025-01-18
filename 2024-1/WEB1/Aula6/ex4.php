<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Exercício 4</title>
</head>

<body>
  <form action="" method="get">
    Insira os valores: <br />
    <input name="v1" type="number" value="0" placeholder="Valor 1" /> <br />
    <input name="v2" type="number" value="0" placeholder="Valor 2" /> <br />
    <input name="v3" type="number" value="0" placeholder="Valor 3" /> <br />
    <input name="v4" type="number" value="0" placeholder="Valor 4" /> <br />
    <input name="v5" type="number" value="0" placeholder="Valor 5" /> <br />
    <input name="v6" type="number" value="0" placeholder="Valor 6" /> <br />
    <input name="v7" type="number" value="0" placeholder="Valor 7" /> <br />
    <input name="v8" type="number" value="0" placeholder="Valor 8" /> <br />
    <input name="v9" type="number" value="0" placeholder="Valor 9" /> <br />
    <input name="v10" type="number" value="0" placeholder="Valor 10" /> <br />
    <input type="submit" value="Calcular" />
  </form>
</body>

</html>
<?php

if (isset($_GET['v1'])) {
  $valores = array();
  for ($i = 1; $i <= 10; $i++) {
    $valores[] = $_GET['v' . $i];
  }

  foreach ($valores as $valor) {
    $soma += $valor;
  }

  $media = $soma / count($valores);
  print("<br/>Média: " . $media . "<br/>");
}
