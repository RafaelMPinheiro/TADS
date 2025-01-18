package negocio;

public class Maquina {
  protected int qtdGomas;
  protected MaquinaState estado;

  public Maquina(int qtdGomas) {
    this.qtdGomas = qtdGomas;
    if (qtdGomas > 0) {
      this.estado = new MaquinaSemMoeda(this);
    } else {
      this.estado = new MaquinaSemGoma(this);
    }
  }

  public int getQtdGomas() {
    return qtdGomas;
  }

  public void setQtdGomas(int qtdGomas) {
    this.qtdGomas = qtdGomas;
  }

  public MaquinaState getEstado() {
    return estado;
  }

  public void setEstado(MaquinaState estado) {
    this.estado = estado;
  }

  public void acionaAlavanca() {
    this.estado.acionaAlavanca();
  }

  public void inserirMoeda() {
    this.estado.inserirMoeda();
  }

  public void ejetaMoeda() {
    this.estado.ejetaMoeda();
  }

  public void adicionarGoma(int qtd) {
    this.estado.adicionarGoma(qtd);
  }

}
