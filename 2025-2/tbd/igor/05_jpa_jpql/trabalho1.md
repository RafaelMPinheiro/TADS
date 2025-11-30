# Trabalho 1

---

## ✅ 1. **NamedQuery para buscar formandos por curso ou turma**

* Criar uma `@NamedQuery` na entidade `Formando` para recuperar todos os formandos de um curso ou turma.
* Exemplo de uso comum: busca por filtro de formulário.

---

## ✅ 2. **Consulta com agregação (contagem de formandos por curso)**

* Criar uma consulta JPQL com `GROUP BY` para contar o total de formandos por curso.
* Ideal para dashboards ou relatórios administrativos.

---

## ✅ 3. **Cache com Redis para relatórios agregados por curso**

* Consultas que agregam dados, como "total de formandos por curso", podem ser pesadas e não mudam com frequência.
* Use Redis para armazenar esses resultados com um TTL (ex: 15 minutos).
* Ao acessar `/relatorio/formandos-por-curso`, o sistema:

  * Verifica se o relatório já está no Redis
  * Se estiver, retorna diretamente
  * Se não, consulta via JPA, armazena no Redis e retorna

---

## ✅ 4. **Paginação na listagem de formandos usando JPA puro**

* Implementar paginação na consulta de formandos usando `setFirstResult()` e `setMaxResults()` do `Query` ou `TypedQuery`.
* Criar um método que receba parâmetros de página e tamanho, retornando apenas os formandos daquele intervalo.
* Permite construir uma API ou interface que carregue dados em partes, melhorando desempenho e usabilidade.

---

## ✅ 5. **Cache com Redis para listagem de formandos por curso (com TTL)**

* Ao consultar todos os formandos de um determinado curso, usar Redis como cache.
* Ideal para páginas públicas ou painéis que listam dados frequentemente requisitados.
* TTL configurado para garantir que dados sejam atualizados periodicamente.

---
