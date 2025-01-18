package negocio;

public class Personagem implements Cloneable {
  private String nome;
  private int vida;
  private String especialidade;

  public Personagem(String nome, int vida, String especialidade) {
    this.nome = nome;
    this.vida = vida;
    this.especialidade = especialidade;
  }

  public Personagem(Personagem p) {
    this.nome = p.nome;
    this.vida = p.vida;
    this.especialidade = p.especialidade;
  }

  @Override
  public Personagem clone() {
    try {
      return (Personagem) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException("Clonagem não suportada", e);
    }
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getVida() {
    return vida;
  }

  public void setVida(int vida) {
    this.vida = vida;
  }

  public String getEspecialidade() {
    return especialidade;
  }

  public void setEspecialidade(String especialidade) {
    this.especialidade = especialidade;
  }
}
