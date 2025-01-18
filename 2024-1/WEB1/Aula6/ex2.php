<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Exercício 2</title>
</head>

<body>
  <form action="" method="get">
    V1: <input type="number" name="v1" value="0" /> <br />
    V2: <input type="number" name="v2" value="0" /> <br /> <br />

    Respostas: <br />
    <input type="number" name="soma" placeholder="V1+V2" /><br />
    <input type="number" name="subtracao" placeholder="V1-V2" /><br />
    <input type="number" name="multiplicacao" placeholder="V1*V2" /><br />
    <input type="number" name="divisao" placeholder="V1/V2" /><br />

    <input type="submit" value="Calcular" />
  </form>
</body>

</html>
<?php
if (isset($_GET['v1']) && isset($_GET['v2']) && isset($_GET['soma']) && isset($_GET['subtracao']) && isset($_GET['multiplicacao']) && isset($_GET['divisao'])) {
  $v1 = $_GET['v1'];
  $v2 = $_GET['v2'];
  $acertos = 0;

  if ($_GET['soma'] == ($v1 + $v2)) {
    $acertos++;
  }
  if ($_GET['subtracao'] == ($v1 - $v2)) {
    $acertos++;
  }
  if ($_GET['multiplicacao'] == ($v1 * $v2)) {
    $acertos++;
  }
  if ($_GET['divisao'] == ($v1 / $v2)) {
    $acertos++;
  }

  print("Vc acertou " . $acertos . " respostas!");
}
?>