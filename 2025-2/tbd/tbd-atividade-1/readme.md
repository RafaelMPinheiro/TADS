Para o ambiente:
- docker compose up -d
- rodar o projeto Java

Para o teste:
```bash
curl --request GET \
  --url 'http://localhost:7070/livros?autor=Autor%201'
````

ou importar o arquivo json no httpie