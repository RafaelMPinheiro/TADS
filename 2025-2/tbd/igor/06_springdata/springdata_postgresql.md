# Spring DATA + PostgreSQL

### 1. Criar o projeto

Você pode usar o [Spring Initializr](https://start.spring.io/):

* **Project**: Maven (ou Gradle, se preferir)
* **Language**: Java
* **Spring Boot**: escolha a versão estável mais recente
* **Dependencies**:

  * **Spring Web**
  * **Spring Data JPA**
  * **PostgreSQL Driver**
  * (Opcional) **Spring Boot DevTools** para facilitar no desenvolvimento
  * (Opcional)  👍 Se você quiser usar o **Mustache** como mecanismo de templates no seu projeto, basta adicionar a dependência `spring-boot-starter-mustache` no `pom.xml`.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.exemplo</groupId>
    <artifactId>spring-data-postgres</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-data-postgres</name>
    <description>Projeto Spring Boot com Spring Data JPA, PostgreSQL e Mustache</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.4</version> <!-- use a versão estável mais recente -->
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <!-- Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Data JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- Driver PostgreSQL -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Mustache -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mustache</artifactId>
        </dependency>

        <!-- DevTools (opcional, mas útil no desenvolvimento) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <!-- Testes -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

---

### 2. Configurar o `application.properties` (ou `application.yml`)

No arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/meubanco
spring.datasource.username=meuusuario
spring.datasource.password=senha
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

⚠️ Ajuste `meubanco`, `meuusuario` e `senha` para os dados reais do seu PostgreSQL.

---

### 3. Criar uma entidade (exemplo `Cliente`)

```java
import jakarta.persistence.*;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;

    // getters e setters
}
```

---

### 4. Criar um repositório

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // você pode adicionar consultas personalizadas aqui se quiser
}
```

---

### 5. Criar um controller de teste

```java
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Cliente salvar(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }
}
```

---

### 6. Rodar o projeto

* Suba seu PostgreSQL (`docker` ou instalado localmente).
* Rode o projeto com `mvn spring-boot:run` ou direto pela IDE.
* Teste os endpoints:

  * `GET http://localhost:8080/clientes`
  * `POST http://localhost:8080/clientes` com JSON:

    ```json
    {
      "nome": "João",
      "email": "joao@email.com"
    }
    ```

---

### 7. 📌 Estrutura para usar o Mustache no Spring Boot:

* Coloque os templates em `src/main/resources/templates/`

  * Exemplo: `clientes.mustache`
* Exemplo de template simples (`clientes.mustache`):

```mustache
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Clientes</title>
</head>
<body>
<h1>Clientes</h1>
<ul>
    {{#clientes}}
        <li>{{nome}} - {{email}}</li>
    {{/clientes}}
</ul>
</body>
</html>
```

* Controller que retorna a view:

```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClienteViewController {

    private final ClienteRepository repository;

    public ClienteViewController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/clientes/view")
    public String listarClientes(Model model) {
        List<Cliente> clientes = repository.findAll();
        model.addAttribute("clientes", clientes);
        return "clientes"; // vai procurar por clientes.mustache em /templates
    }
}
```

### 8. Estrutura do projeto

```
src/main/java/com/exemplo/springdatapostgres/
 ├── SpringDataPostgresApplication.java   # Classe main
 ├── entity/
 │     └── Cliente.java
 ├── repository/
 │     └── ClienteRepository.java
 ├── controller/
 │     ├── ClienteController.java         # REST
 │     └── ClienteViewController.java     # Mustache
src/main/resources/
 ├── application.properties
 └── templates/
       └── clientes.mustache
```

---

### 9. Classe Main

```java
package com.exemplo.springdatapostgres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataPostgresApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataPostgresApplication.class, args);
    }
}
```

---

### 10. Entidade (`Cliente.java`)

```java
package com.exemplo.springdatapostgres.entity;

import jakarta.persistence.*;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
```

---

### 11. Repositório (`ClienteRepository.java`)

```java
package com.exemplo.springdatapostgres.repository;

import com.exemplo.springdatapostgres.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
```

---

## 4. Controller REST (`ClienteController.java`)

```java
package com.exemplo.springdatapostgres.controller;

import com.exemplo.springdatapostgres.entity.Cliente;
import com.exemplo.springdatapostgres.repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Cliente salvar(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }
}
```

---

### 12. Controller para Mustache (`ClienteViewController.java`)

```java
package com.exemplo.springdatapostgres.controller;

import com.exemplo.springdatapostgres.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteViewController {

    private final ClienteRepository repository;

    public ClienteViewController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", repository.findAll());
        return "clientes"; // carrega clientes.mustache
    }
}
```

---

### 13. Template Mustache (`clientes.mustache` em `src/main/resources/templates/`)

```mustache
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Clientes</title>
</head>
<body>
<h1>Clientes</h1>
<ul>
    {{#clientes}}
        <li>{{nome}} - {{email}}</li>
    {{/clientes}}
    {{^clientes}}
        <li>Nenhum cliente cadastrado.</li>
    {{/clientes}}
</ul>
</body>
</html>
```

---

### 14. Configuração (`application.properties`)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/meubanco
spring.datasource.username=meuusuario
spring.datasource.password=senha
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

---

👉 Com isso você já tem:

* **API REST** em `/api/clientes` (GET e POST)
* **View com Mustache** em `/clientes`
