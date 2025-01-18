package negocio;

public class CarroProxy implements Model {
  private Carro carro;

  public CarroProxy(int ano, String placa, String modelo, String marca, String chassi) {
    this.carro = new Carro(ano, modelo, marca, placa, chassi);
  }

  @Override
  public void entrarGaragem(Garagem garagem) {
    this.carro.entrarGaragem(garagem);
  }

  @Override
  public void sairGaragem() {
    this.carro.sairGaragem();
  }

  @Override
  public void manobrar(Empregado empregado) {
    if (!empregado.isHabilitado() || empregado.getIdade() < 18) {
      System.out.println("Empregado não tem permição para manobrar.");
    } else {
      this.carro.manobrar(empregado);
    }
  }

  @Override
  public String getPlaca() {
    return this.carro.getPlaca();
  }

  @Override
  public void setPlaca(String placa) {
    this.carro.setPlaca(placa);
  }

  @Override
  public String getModelo() {
    return this.carro.getModelo();
  }

  @Override
  public void setModelo(String modelo) {
    this.carro.setModelo(modelo);
  }

  @Override
  public String getMarca() {
    return this.carro.getMarca();
  }

  @Override
  public void setMarca(String marca) {
    this.carro.setMarca(marca);
  }

  @Override
  public Garagem getGaragem() {
    return this.carro.getGaragem();
  }
}
