# Requisições HTTPie - Sistema de Eventos

## Dashboard
```bash
http GET localhost:7070/
http GET localhost:7070/stats
```

## Criar Dados
```bash
# Criar evento
http POST localhost:7070/eventos nome="Java Conference 2025" data="2025-12-15T09:00:00" local="São Paulo" descricao="Maior conferência de Java do Brasil" tags:='["java", "tecnologia"]'

# Criar participante
http POST localhost:7070/participantes nome="Maria Silva" email="maria.silva@email.com" telefone="11987654321" organizacao="Tech Solutions"
```

## Listar
```bash
http GET localhost:7070/eventos
http GET localhost:7070/participantes
http GET localhost:7070/eventos/{ID}
```

## Filtrar Eventos
```bash
http GET "localhost:7070/eventos/filtrar?local=Paulo"
http GET "localhost:7070/eventos/filtrar?palavraChave=java"
http GET "localhost:7070/eventos/filtrar?dataInicio=2025-12-01T00:00:00&dataFim=2025-12-31T23:59:59"
```

## Relacionamentos (Neo4j)
```bash
http POST localhost:7070/eventos/{EVENTO_ID}/participantes/{PARTICIPANTE_ID}
http POST localhost:7070/eventos/{EVENTO_ID}/organizadores/{PARTICIPANTE_ID}
http GET localhost:7070/eventos/{EVENTO_ID}/relacionamentos
http POST localhost:7070/eventos/{EVENTO_ID}/promover/{PARTICIPANTE_ID}
```

## Exportação
```bash
# JSON
http GET localhost:7070/export/json/eventos
http GET localhost:7070/export/json/participantes

# SQL
http GET localhost:7070/export/sql
http GET localhost:7070/export/sql/create-tables
http GET localhost:7070/export/sql/eventos
http GET localhost:7070/export/sql/participantes
http GET localhost:7070/export/sql/download > eventos_export.sql
```