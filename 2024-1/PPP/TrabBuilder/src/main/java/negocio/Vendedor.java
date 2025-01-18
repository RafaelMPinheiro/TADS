package negocio;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Vendedor extends Pessoa {
  private LocalDate inicio;
  private LocalDate fim;

  public Vendedor(int id, String nome, LocalDate inicio) {
    super(id, nome);
    this.inicio = inicio;
  }

  public Vendedor(String nome, LocalDate inicio) {
    super(nome);
    this.inicio = inicio;
  }
}