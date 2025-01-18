package negocio;

public interface Command {
  public String getName();

  public void executeA();

  public void executeB();
}
