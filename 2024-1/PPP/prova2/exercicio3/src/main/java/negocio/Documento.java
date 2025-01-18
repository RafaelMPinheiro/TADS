package negocio;

public class Documento {
  private String nome;
  private String extensao;
  private int paginas;

  public Documento(String nome, String extensao, int paginas) {
    this.nome = nome;
    this.extensao = extensao;
    this.paginas = paginas;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getExtensao() {
    return extensao;
  }

  public void setExtensao(String extensao) {
    this.extensao = extensao;
  }

  public int getPaginas() {
    return paginas;
  }

  public void setPaginas(int paginas) {
    this.paginas = paginas;
  }

  @Override
  public String toString() {
    return "Documento [nome=" + nome + ", extensao=" + extensao + ", paginas=" + paginas + "]";
  }
}
