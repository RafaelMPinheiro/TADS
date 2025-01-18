package negocio;

public class Carro implements Model {
  private int ano;
  private String modelo;
  private String marca;
  private String placa;
  private String chassi;
  private Garagem garagem;

  public Carro(int ano, String modelo, String marca, String placa, String chassi) {
    this.ano = ano;
    this.modelo = modelo;
    this.marca = marca;
    this.placa = placa;
    this.chassi = chassi;
  }

  public int getAno() {
    return ano;
  }

  public void setAno(int ano) {
    this.ano = ano;
  }

  public String getModelo() {
    return modelo;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public String getPlaca() {
    return placa;
  }

  public void setPlaca(String placa) {
    this.placa = placa;
  }

  public String getChassi() {
    return chassi;
  }

  public void setChassi(String chassi) {
    this.chassi = chassi;
  }

  public Garagem getGaragem() {
    return this.garagem;
  }

  public void setGaragem(Garagem garagem) {
    this.garagem = garagem;
  }

  public void manobrar(Empregado empregado) {
    System.out.println("Empregado tem permição para manobrar.");
  }

  @Override
  public void entrarGaragem(Garagem garagem) {
    if (this.garagem == null && !garagem.getCarros().contains(this)) {
      this.garagem = garagem;
      garagem.addCarro(this);
    }
  }

  @Override
  public void sairGaragem() {
    if (this.garagem != null || garagem.getCarros().contains(this)) {
      this.garagem = null;
      garagem.retirarCarro(this);
    }
  }
}
