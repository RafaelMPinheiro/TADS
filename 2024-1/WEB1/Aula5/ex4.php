<?php
$a = rand(1, 100);
$b = rand(1, 100);

if ($a >= $b) {
  print($a . "  " . $b);
} else {
  print($b . "  " . $a);
}
