package negocio;

public interface Model {
  public void entrarGaragem(Garagem garagem);

  public void sairGaragem();

  public void manobrar(Empregado empregado);

  public String getPlaca();

  public void setPlaca(String placa);

  public String getModelo();

  public void setModelo(String modelo);

  public String getMarca();

  public void setMarca(String marca);

  public Garagem getGaragem();
}