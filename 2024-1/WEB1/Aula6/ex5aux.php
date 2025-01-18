<?php
if (isset($_GET['clientes'])) {
  $idades = array();

  for ($i = 1; $i <= $_GET['clientes']; $i++) {
    $idades[] = $_GET['cliente' . $i];
  }

  $idadeCounts = array();

  
  foreach ($idades as $idade) {
    if (isset($idadeCounts[$idade])) {
      $idadeCounts[$idade]++;
    } else {
      $idadeCounts[$idade] = 1;
    }
  }
  // print_r($idadeCounts);
  
  foreach ($idadeCounts as $idade => $count) {
    $porcentagem = ($count / $_GET['clientes']) * 100;
    echo "Porcentagem de pacientes com $idade anos: $porcentagem% <br>";
  }
}
