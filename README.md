# Portfólio Acadêmico — TADS (IFRS Campus Rio Grande)

Repositório com as atividades práticas, trabalhos e módulos de estudo desenvolvidos por Rafael Pinheiro durante o curso superior de Tecnologia em Análise e Desenvolvimento de Sistemas no IFRS (Campus Rio Grande).

---

## Sobre o Repositório

Este repositório reúne o histórico de código e o aprendizado prático consolidado ao longo da graduação em **Tecnologia em Análise e Desenvolvimento de Sistemas** pelo **Instituto Federal do Rio Grande do Sul (IFRS — Campus Rio Grande)**.

O material aqui contido funciona como um **portfólio acadêmico**, documentando a evolução técnica desde os fundamentos de lógica e estruturas de dados em baixo nível até a aplicação de padrões de projeto (GoF), boas práticas de arquitetura de software, persistência com múltiplos modelos de banco de dados (relacionais e NoSQL) e desenvolvimento full stack (web e mobile).

> **Nota sobre a organização:** O repositório é composto por módulos didáticos, exercícios de fixação e trabalhos práticos organizados por semestre e disciplina, e não por projetos independentes únicos.

---

## Estrutura por Semestre e Disciplinas

O repositório é organizado cronologicamente em pastas por período letivo (`AAAA-S`), subdivididas de acordo com as disciplinas da matriz curricular:

| Semestre | Pasta / Disciplina | Descrição e Conteúdo Confirmado | Tecnologias Principais |
| :--- | :--- | :--- | :--- |
| **2024-1** | `Estrutura de dados` | Implementação de estruturas de dados clássicas (listas encadeadas, pilhas, filas, árvores), alocação dinâmica de memória, aritmética de ponteiros e algoritmos de busca e ordenação. | C |
| **2024-1** | `PPP` *(Princípios e Padrões de Projeto)* | Aplicação prática de Programação Orientada a Objetos e Padrões de Projeto GoF (Builder, Command, Proxy, Decorator, State, Observer, Iterator, Prototype, Strategy), além de manipulação de planilhas e relatórios. | Java 17, Maven, Apache POI, PDFBox |
| **2024-1** | `PBD` *(Programação em Banco de Dados)* | Modelagem de dados relacionais, criação de scripts DDL/DML, elaboração de consultas relacionais, triggers, procedimentos e integridade de dados. | SQL (PostgreSQL / MySQL) |
| **2024-1** | `WEB1` *(Desenvolvimento Web I)* | Fundamentos de desenvolvimento para web, abrangendo estruturação de páginas, processamento no lado do servidor e manipulação de formulários. | PHP, HTML, CSS |
| **2024-2** | `WEB-II` & `tads-webii-2024-2` *(Desenvolvimento Web II)* | Construção de aplicações web e APIs RESTful, renderização dinâmica com templates, autenticação baseada em tokens (JWT) com criptografia (bcrypt), modelagem e persistência com ORM e arquitetura MVC. | Node.js, Express, Prisma ORM, EJS, SQLite, PostgreSQL |
| **2025-1** | `Redes` *(Redes de Computadores)* | Comunicação em rede cliente-servidor utilizando sockets TCP e UDP (incluindo aplicação interativa distribuída) e simulação de topologias e protocolos de rede. | Python (Sockets), Cisco Packet Tracer |
| **2025-1** | `PPA` *(Princípios e Padrões de Arquitetura)* | Estrutura inicial voltada ao estudo e aplicação de princípios de arquitetura de software corporativo. | Configurações de ambiente |
| **2025-2** | `CC` *(Tópicos em Ciência da Computação)* | Algoritmos com processamento paralelo e concorrente em CPU (`multiprocessing`), resolução por força bruta e análise exploratória de dados censitários e demográficos. | Python, Pandas, NumPy, Matplotlib, Seaborn |
| **2025-2** | `apps` *(Tópicos em Desenv. de Aplicativos)* | Desenvolvimento de aplicativos mobile multiplataforma, abordando componentização, navegação em abas e pilhas, gerenciamento de estado global (Context API), tipagem e consumo de serviços externos. | React Native, TypeScript, Expo Router |
| **2025-2** | `sistemas` *(Tópicos em Desenv. de Sistemas)* | Implementação de algoritmos de busca recursiva e técnicas de exploração do espaço de estados (*backtracking*). | JavaScript |
| **2025-2** | `tbd` *(Tópicos em Banco de Dados)* | Persistência avançada e ecossistema NoSQL em Java: mapeamento objeto-relacional com JPA / Hibernate e consultas JPQL, Spring Data, bancos chave-valor em memória (Redis/Jedis), orientados a documentos (MongoDB) e em grafos (Neo4j), com ambientes conteinerizados. | Java 17, Maven, Spring Data, JPA, MongoDB, Neo4j, Redis, Docker Compose |

---

## Tecnologias e Conceitos Demonstrados

O código presente no repositório evidencia domínio prático nos seguintes domínios:

- **Linguagens de Programação:** Java (17+), C, Python, JavaScript, TypeScript, SQL, PHP.
- **Back-end & Persistência:**
  - *Java:* JPA / Hibernate, Spring Data JPA, Drivers nativos para Redis (Jedis), MongoDB e Neo4j.
  - *Node.js:* Express, Prisma ORM, EJS, JWT, bcrypt.
  - *Banco de Dados:* PostgreSQL, MySQL, SQLite, MongoDB, Neo4j, Redis.
- **Front-end & Mobile:** React Native, Expo Router, TypeScript, HTML5, CSS3.
- **Ciência de Dados & Concorrência:** Python `multiprocessing`, Pandas, NumPy, Matplotlib, Seaborn.
- **Engenharia de Software & Paradigmas:**
  - Padrões de Projeto (Design Patterns GoF): Builder, Command, Proxy, Decorator, State, Observer, Iterator, Prototype, Strategy.
  - Princípios SOLID e Programação Orientada a Objetos (POO).
  - Arquitetura MVC, Camadas de Serviços/DAO e APIs RESTful.
  - Estruturas de Dados fundamentais e Algoritmos de Ordenação/Busca.
- **Infraestrutura & Ferramentas:** Apache Maven, Docker Compose, Git, Cisco Packet Tracer.

---

## Como Executar

Como este repositório é composto por diversos módulos independentes de estudo e trabalhos práticos, cada diretório contém sua respectiva configuração de compilação ou execução.

### Módulos Java (Maven)
Cada módulo acadêmico em Java possui seu próprio manifesto `pom.xml` (configurado para Java 17). Para compilar ou executar:

1. Acesse o diretório do módulo desejado (por exemplo, em `2024-1/PPP/TrabCommand` ou `2025-2/tbd/tbd-trab-2/eventos`):
   ```bash
   cd 2024-1/PPP/TrabCommand
   ```
2. Compile o código com o Maven:
   ```bash
   mvn compile
   ```
3. Para executar uma classe específica contendo o método `main`, utilize sua IDE preferida ou o plugin exec do Maven:
   ```bash
   mvn exec:java -Dexec.mainClass="apresentacao.Main"
   ```

### Aplicações Node.js / React Native
Os módulos em JavaScript e TypeScript contam com seus respectivos arquivos `package.json`:

1. Acesse o diretório do trabalho (por exemplo, em `2025-2/apps/trabalho-1` ou `2024-2/WEB-II/Trabalho-I`):
   ```bash
   cd 2025-2/apps/trabalho-1
   ```
2. Instale as dependências:
   ```bash
   npm install
   ```
3. Execute o script indicado no `package.json` (por exemplo, `npm start` ou `npx expo start` para aplicações Expo):
   ```bash
   npx expo start
   ```

### Scripts em C e Python
- **Programas em C:** podem ser compilados diretamente via GCC:
  ```bash
  gcc "2024-1/Estrutura de dados/Trabalho.c" -o trabalho && ./trabalho
  ```
- **Scripts em Python:** podem ser executados com o interpretador Python 3, instalando eventuais dependências listadas em arquivos de requisitos locais:
  ```bash
  python3 2025-2/CC/Trabalho-1/paralelo.py
  ```

---

## Contato

- **GitHub:** [https://github.com/RafaelMPinheiro](https://github.com/RafaelMPinheiro)
- **LinkedIn:** [https://www.linkedin.com/in/rafaelmpinheiro/](https://www.linkedin.com/in/rafaelmpinheiro/)
