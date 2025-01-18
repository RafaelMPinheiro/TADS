<?php
$idade = 13;

if ($idade < 13) {
  echo "<h1>Criança!</h1>";
} else if ($idade >= 13 && $idade < 20) {
  echo "<h1>Adolescente</h1>";
} else if ($idade >= 20 && $idade < 60) {
  echo "<h1>Adulto</h1>";
} else {
  echo "<h1>Idoso</h1>";
}
