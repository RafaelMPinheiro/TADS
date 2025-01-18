<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Exercício 7</title>
</head>

<body>
  <form action="" method="get">
    Insira um valor: <br />
    <input name="v1" type="number" value="0" placeholder="Valor 1" /> <br />
    <input type="submit" value="Calcular" />
  </form>
</body>

</html>
<?php

if (isset($_GET['v1'])) {
  $v1 = $_GET['v1'];
  $divisivel = false;

  if ($v1 % 10 == 0) {
    print("O número é divisivel por 10!<br/>");
    $divisivel = true;
  }
  if ($v1 % 5 == 0) {
    print("O número é divisivel por 5!<br/>");
    $divisivel = true;
  }
  if ($v1 % 2 == 0) {
    print("O número é divisivel por 2!<br/>");
    $divisivel = true;
  }

  if(!$divisivel){
    print("O número não é divisivel por 10, 5 ou 2!<br/>");
  }
}
