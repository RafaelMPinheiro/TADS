<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Exercício 10</title>
</head>

<body>
  <form action="" method="get">
    Insira o peso: <br />
    <input name="peso" type="number" value="0" placeholder="Peso" /> <br />
    Insira a altura: <br />
    <input name="altura" type="number" value="0" placeholder="Altura" /> <br />
    <input type="submit" value="Calcular" />
  </form>
</body>

</html>
<?php

if (isset($_GET['peso']) && isset($_GET['altura'])) {
  $peso = $_GET['peso'];
  $altura = $_GET['altura'];

  $imc = $peso / ($altura * $altura);

  if ($imc > 25) {
    print("Você está acima do peso!");
  } else {
    print("Você está saudável");
  }
}
