<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Exercício 5</title>
</head>

<body>
  <?php
  if (isset($_GET['clientes'])) {
    $clientes = $_GET['clientes'];
    print('<form action="ex5aux.php" method="get">' .
      'Insira as idades: <br />');
    for ($i = 1; $i <= $_GET['clientes']; $i++) {
      print('<input name="cliente' . $i . '" type="number" value="0" placeholder="Idade do cliente ' . $i . '" /> <br />');
    }
    print('<input name="clientes" type="hidden" value="' . $clientes . '" />');
    print('<input type="submit" value="Calcular" />' .
      '</form>');
  }
  ?>
</body>

</html>