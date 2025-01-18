package negocio;

import java.util.ArrayList;

public class Garagem {
  private String nome;
  private ArrayList<Empregado> empregados = new ArrayList<Empregado>();
  private ArrayList<Carro> carros = new ArrayList<Carro>();

  public Garagem(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public ArrayList<Empregado> getEmpregados() {
    return empregados;
  }

  public void setEmpregados(ArrayList<Empregado> empregados) {
    this.empregados = empregados;
  }

  public ArrayList<Carro> getCarros() {
    return carros;
  }

  public void setCarros(ArrayList<Carro> carros) {
    this.carros = carros;
  }

  public void contratarFuncionario(Empregado empregado) {
    if (empregado.getGaragem() == null) {
      this.empregados.add(empregado);
      empregado.setGaragem(this);
    }
  }

  public void demitirEmpregado(Empregado empregado) {
    if (empregados.contains(empregado)) {
      this.empregados.remove(empregado);
      empregado.setGaragem(null);
    }
  }

  protected void addCarro(Carro carro) {
    this.carros.add(carro);
  }

  protected void retirarCarro(Carro carro) {
    if (carros.contains(carro)) {
      carros.remove(carro);
    }
  }

  @Override
  public String toString() {
    return "Garagem " + this.nome;
  }

}