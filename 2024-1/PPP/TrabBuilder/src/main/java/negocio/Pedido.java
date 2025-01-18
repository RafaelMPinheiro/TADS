package negocio;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Pedido {
  private int id;
  private Cliente cliente;
  private Vendedor vendedor;
  private ArrayList<Item> items;
}