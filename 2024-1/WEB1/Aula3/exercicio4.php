<?php
$valor = 10000;
$renda = 1000;
$nrm_parcelas = 100;

$valor_parcela = $valor / $nrm_parcelas;

if ($nrm_parcelas > 180 || $valor_parcela > ($renda * 0.3)) {
  echo "Não foi aprovado! Pois ficou com parcelas de R$ ", $valor_parcela;
} else {
  echo "Foi aprovado com parcelas de R$", $valor_parcela;
}
