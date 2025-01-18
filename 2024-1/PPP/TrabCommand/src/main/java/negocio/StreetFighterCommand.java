package negocio;

import games.StreetFighter;

public class StreetFighterCommand implements Command {
  private StreetFighter streetFighter;

  public StreetFighterCommand(StreetFighter streetFighter) {
    this.streetFighter = streetFighter;
  }

  @Override
  public String getName() {
    return this.streetFighter.getClass().getSimpleName();
  }

  @Override
  public void executeA() {
    this.streetFighter.soco();
  }

  @Override
  public void executeB() {
    this.streetFighter.chute();
  }
}
