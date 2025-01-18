<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Exercício 1</title>
</head>

<body>
  <form action="" method="get">
    V1: <input type="number" name="v1" value="0" /> <br />
    <select name="select">
      <option value="soma">+</option>
      <option value="subtracao">-</option>
      <option value="multiplicacao">*</option>
      <option value="divisao">/</option>
    </select> <br>
    V2: <input type="number" name="v2" value="0" /> <br />
    <input type="submit" value="Calcular" />
  </form>
</body>

</html>
<?php
if (isset($_GET['v1']) && isset($_GET['v2']) && isset($_GET['select'])) {
  $operador = $_GET['select'];
  $v1 = $_GET['v1'];
  $v2 = $_GET['v2'];

  switch ($operador) {
    case 'soma':
      print($v1 . " + " .  $v2 . " = " . ($v1 + $v2));
      break;
    case 'subtracao':
      print($v1 . " - " .  $v2 . " = " . ($v1 - $v2));
      break;
    case 'multiplicacao':
      print($v1 . " * " .  $v2 . " = " . ($v1 * $v2));
      break;
    case 'divisao':
      print($v1 . " / " .  $v2 . " = " . ($v1 / $v2));
      break;
  }
}
