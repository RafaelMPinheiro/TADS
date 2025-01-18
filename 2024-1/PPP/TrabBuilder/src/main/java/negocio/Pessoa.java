package negocio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Pessoa {
  protected int id;
  protected String nome;

  public Pessoa(int id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  public Pessoa(String nome) {
    this.nome = nome;
  }
}
