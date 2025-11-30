# Many to Many

A relação **muitos para muitos (Many-to-Many)** em JPA é usada quando **múltumas instâncias de uma entidade podem estar associadas a múltiplas instâncias de outra entidade**.

---

## 🔁 Muitos-para-Muitos: Conceito

Exemplo clássico:

* Um **Aluno** pode estar matriculado em várias **Disciplinas**.
* Uma **Disciplina** pode ter vários **Alunos**.

---

## 🧩 Modelagem com JPA

### Exemplo simples com anotação `@ManyToMany`:

```java
@Entity
public class Aluno {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;

    @ManyToMany
    @JoinTable(
        name = "aluno_disciplina", // Nome da tabela de junção
        joinColumns = @JoinColumn(name = "aluno_id"),
        inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    private List<Disciplina> disciplinas = new ArrayList<>();
}

@Entity
public class Disciplina {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "disciplinas")
    private List<Aluno> alunos = new ArrayList<>();
}
```

### 🔨 Resultado no banco de dados:

* Tabela `aluno`
* Tabela `disciplina`
* **Tabela de junção `aluno_disciplina`** com:

  * `aluno_id`
  * `disciplina_id`

---

## 📌 Notas importantes

* **`@JoinTable`** é opcional — se você não colocar, o JPA cria uma tabela de junção com nomes padrão.
* O lado **"dono"** da relação é o que **não usa `mappedBy`**.
* A coleção pode ser `List`, `Set`, etc. (Use `Set` se não quiser duplicatas.)

---

## 🔄 Se precisar de atributos na relação?

Se a relação **aluno-disciplina** tiver atributos próprios (ex: data de matrícula, nota), você precisa **modelar a tabela de junção como uma entidade separada**, transformando a relação em **duas relações Many-to-One**.

Exemplo: `Matricula`

```java
@Entity
public class Matricula {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Aluno aluno;

    @ManyToOne
    private Disciplina disciplina;

    private LocalDate dataMatricula;
    private Double notaFinal;
}
```

---

## ✅ Quando usar @ManyToMany diretamente?

Use quando:

* A tabela de junção **não tem dados próprios** (é só ligação).
* O modelo é simples.
* Performance e manutenibilidade não serão prejudicadas.

Caso contrário, prefira uma entidade intermediária.

