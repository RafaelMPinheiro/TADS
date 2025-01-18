package negocio;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Associado {
  private String nome;
  private String cpf;
  private String senha;
  private String email;
}
