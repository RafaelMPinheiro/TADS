package negocio;

public class MaquinaComMoeda extends MaquinaState {

  public MaquinaComMoeda(Maquina maquina) {
    super(maquina);
  }

  @Override
  void inserirMoeda() {
    this.maquina.setEstado(this);
  }

  @Override
  void ejetaMoeda() {
    this.maquina.setEstado(new MaquinaSemMoeda(this.maquina));
  }

  @Override
  void acionaAlavanca() {
    if (this.maquina.getQtdGomas() > 0) {
      this.maquina.setEstado(new MaquinaSemMoeda(this.maquina));
      this.maquina.setQtdGomas(this.maquina.getQtdGomas() - 1);
    } else {
      this.maquina.setEstado(new MaquinaSemGoma(this.maquina));
    }
  }

  @Override
  void adicionarGoma(int qtdGoma) {
    this.maquina.setQtdGomas(qtdGoma);
    this.maquina.setEstado(this);
  }
}
