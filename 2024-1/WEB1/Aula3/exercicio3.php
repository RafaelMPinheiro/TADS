<?php
$temp = 30;

if ($temp < -50 || $temp > 500) {
  echo "Crítico";
} else if ($temp < 0 || $temp > 300) {
  echo "Alerta";
} else {
  echo "Normal";
}
