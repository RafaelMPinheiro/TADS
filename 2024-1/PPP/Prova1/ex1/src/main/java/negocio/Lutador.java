package negocio;

import golpes.Golpe;

public class Lutador {
  protected String nome;

  public Lutador(String nome) {
    this.nome = nome;
  }

  public void atacar(Golpe golpe) {
    System.out.println(this.nome + " usou " + golpe.getNome());
  }
}
