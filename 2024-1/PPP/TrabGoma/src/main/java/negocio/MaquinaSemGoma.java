package negocio;

public class MaquinaSemGoma extends MaquinaState {

  public MaquinaSemGoma(Maquina maquina) {
    super(maquina);
  }

  @Override
  void inserirMoeda() {
    this.maquina.setEstado(this);
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
    this.maquina.setEstado(new MaquinaSemMoeda(this.maquina));
  }
}
