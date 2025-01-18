package negocio;

import games.NeedforSpeed;

public class NeedforSpeedCommand implements Command {
  private NeedforSpeed needforSpeed;

  public NeedforSpeedCommand(NeedforSpeed needforSpeed) {
    this.needforSpeed = needforSpeed;
  }

  @Override
  public String getName() {
    return this.needforSpeed.getClass().getSimpleName();
  }

  @Override
  public void executeA() {
    this.needforSpeed.acelerar();
  }

  @Override
  public void executeB() {
    this.needforSpeed.freiar();
  }
}
