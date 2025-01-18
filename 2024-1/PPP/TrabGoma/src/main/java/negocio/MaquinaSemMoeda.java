package negocio;

public class MaquinaSemMoeda extends MaquinaState {
  MaquinaSemMoeda(Maquina maquina) {
    super(maquina);
  }

  @Override
  void inserirMoeda() {
    this.maquina.setEstado(new MaquinaComMoeda(this.maquina));
  }

  @Override
  void ejetaMoeda() {
    this.maquina.setEstado(this);
  }

  @Override
  void acionaAlavanca() {
    this.maquina.setEstado(this);
  }

  @Override
  void adicionarGoma(int qtdGoma) {
    this.maquina.setQtdGomas(qtdGoma);
    this.maquina.setEstado(this);
  }
}
