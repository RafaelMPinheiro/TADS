package negocio;

import aparelhos.Projetor;

public class ProjetorCommand implements Command {
  public Projetor projetor;

  public ProjetorCommand(Projetor projetor) {
    this.projetor = projetor;
  }

  public ProjetorCommand(String nome) {
    this.projetor = new Projetor(nome);
  }

  @Override
  public String getName() {
    return this.projetor.getNome();
  }

  @Override
  public void executeA() {
    this.projetor.ligar();
  }

  @Override
  public void executeB() {
    this.projetor.desligar();
  }
}
