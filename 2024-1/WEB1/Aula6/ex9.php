<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Exercício 9</title>
</head>

<body>
  <form action="" method="get">
    Insira os valores: <br />
    <input name="v1" type="number" value="0" placeholder="Valor 1" /> <br />
    <input name="v2" type="number" value="0" placeholder="Valor 2" /> <br />
    <input type="submit" value="Calcular" />
  </form>
</body>

</html>
<?php

if (isset($_GET['v1']) && isset($_GET['v2'])) {
  $v1 = $_GET['v1'];
  $v2 = $_GET['v2'];

  print("A area do retangulo é: " . ($v1 * $v2) . "<br/>");
  print("O perimetro do retangulo é: " . ((2 * $v1) + (2 * $v2)));
}
