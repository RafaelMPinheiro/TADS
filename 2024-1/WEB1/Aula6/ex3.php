<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Exercício 3</title>
</head>

<body>
  <form action="" method="get">
    Idade: <input type="number" name="idade" value="0" /> <br />
    <input type="submit" value="Calcular" />
  </form>
</body>

</html>
<?php
if (isset($_GET['idade'])) {
  $idade = $_GET['idade'];

  if ($idade < 13) {
    print("Criança");
  } else if ($idade >= 13 && $idade < 20) {
    print("Adolescente");
  } else if ($idade >= 20 && $idade < 60) {
    print("Adulto");
  } else if ($idade >= 60) {
    print("Idoso");
  }
}
