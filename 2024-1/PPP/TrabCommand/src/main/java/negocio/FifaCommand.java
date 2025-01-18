package negocio;

import games.Fifa;

public class FifaCommand implements Command {
  private Fifa fifa;

  public FifaCommand(Fifa fifa) {
    this.fifa = fifa;
  }

  @Override
  public String getName() {
    return this.fifa.getClass().getSimpleName();
  }

  @Override
  public void executeA() {
    this.fifa.chutar();
  }

  @Override
  public void executeB() {
    this.fifa.passe();
  }
}
