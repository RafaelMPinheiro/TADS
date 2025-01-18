package negocio;

public class NoCommand implements Command {
  @Override
  public String getName() {
    throw new UnsupportedOperationException("Unimplemented method 'getName'");
  }

  @Override
  public void executeA() {
    throw new UnsupportedOperationException("Unimplemented method 'executeA'");
  }

  @Override
  public void executeB() {
    throw new UnsupportedOperationException("Unimplemented method 'executeB'");
  }
}
