# Herança em JPA (Java Persistence API) 

O JPA permite que você modele relações de herança entre classes Java de forma que também sejam refletidas nas tabelas do banco de dados. É especialmente útil quando você tem uma hierarquia de classes com atributos comuns.

### Estratégias de herança em JPA

JPA fornece **três estratégias principais** para mapear herança no banco de dados:

---

## 1. `SINGLE_TABLE` (Tabela Única)

* **Todas as classes da hierarquia são mapeadas em uma única tabela.**
* Uma coluna discriminadora (`@DiscriminatorColumn`) é usada para identificar o tipo de cada linha.

### Exemplo:

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Animal {
    @Id
    private Long id;
    private String nome;
}

@Entity
@DiscriminatorValue("Cachorro")
public class Cachorro extends Animal {
    private String raca;
}

@Entity
@DiscriminatorValue("Gato")
public class Gato extends Animal {
    private Boolean gostaDePeixe;
}
```

#### Tabela gerada:

| id | nome | tipo     | raca   | gostaDePeixe |
| -- | ---- | -------- | ------ | ------------ |
| 1  | Rex  | Cachorro | Poodle | null         |
| 2  | Miau | Gato     | null   | true         |

✅ Mais performance (menos joins).
❌ Muitas colunas nulas quando a hierarquia é grande.

---

## 2. `JOINED` (Tabelas com Junções)

* **Cada classe tem sua própria tabela.**
* As tabelas são ligadas por chaves estrangeiras.

### Exemplo:

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Animal {
    @Id
    private Long id;
    private String nome;
}

@Entity
public class Cachorro extends Animal {
    private String raca;
}

@Entity
public class Gato extends Animal {
    private Boolean gostaDePeixe;
}
```

#### Tabelas geradas:

* `Animal(id, nome)`
* `Cachorro(id, raca)` → `id` é FK para `Animal`
* `Gato(id, gostaDePeixe)` → `id` é FK para `Animal`

✅ Mais normalizado.
❌ Requer joins (pode ser mais lento).

---

## 3. `TABLE_PER_CLASS` (Tabela por Classe Concreta)

* **Cada classe concreta tem sua própria tabela com todas as colunas necessárias (inclusive herdadas).**

### Exemplo:

```java
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Animal {
    @Id
    private Long id;
    private String nome;
}

@Entity
public class Cachorro extends Animal {
    private String raca;
}

@Entity
public class Gato extends Animal {
    private Boolean gostaDePeixe;
}
```

#### Tabelas geradas:

* `Cachorro(id, nome, raca)`
* `Gato(id, nome, gostaDePeixe)`

✅ Sem joins.
❌ Difícil de consultar toda a hierarquia (`SELECT * FROM Animal` não funciona diretamente).

---

## Considerações

| Estratégia        | Performance           | Normalização | Facilidade de Consulta |
| ----------------- | --------------------- | ------------ | ---------------------- |
| `SINGLE_TABLE`    | Alta                  | Baixa        | Fácil                  |
| `JOINED`          | Média                 | Alta         | Média                  |
| `TABLE_PER_CLASS` | Alta (leitura direta) | Média        | Difícil (em heranças)  |

---

