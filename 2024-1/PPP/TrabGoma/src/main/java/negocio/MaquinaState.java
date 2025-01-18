package negocio;

public abstract class MaquinaState {
  protected Maquina maquina;

  public MaquinaState(Maquina maquina) {
    this.maquina = maquina;
  }

  abstract void inserirMoeda();

  abstract void ejetaMoeda();

  abstract void acionaAlavanca();

  abstract void adicionarGoma(int qtdGoma);
}