# Índice de Envelhecimento por Raça
# ano | id_municipio | cor_raca | indice_envelhecimento | idade_mediana | razao_sexo

import pandas as pd
df = pd.read_csv("dados.csv.gz")

print(df.head())