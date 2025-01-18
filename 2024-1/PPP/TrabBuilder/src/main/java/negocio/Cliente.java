package negocio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cliente extends Pessoa {
  private String nomeMae;
  private String cpf;
  private Endereco endereco;

  public Cliente(int id, String nome, String nomeMae, String cpf, Endereco endereco) {
    super(id, nome);
    this.nomeMae = nomeMae;
    this.cpf = cpf;
    this.endereco = endereco;
  }

  public Cliente(String nome, String nomeMae, String cpf, Endereco endereco) {
    super(nome);
    this.nomeMae = nomeMae;
    this.cpf = cpf;
    this.endereco = endereco;
  }

  public Cliente(int id, String nome, Endereco endereco) {
    super(id, nome);
    this.endereco = endereco;
  }
}
