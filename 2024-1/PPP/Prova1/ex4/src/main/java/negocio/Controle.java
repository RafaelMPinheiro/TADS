package negocio;

public class Controle {
  private Command[] commands;

  public Controle() {
    this.commands = new Command[5];
    for (int i = 0; i < commands.length; i++) {
      this.commands[i] = new NoCommand();
    }
  }

  public Command[] getAparelhos() {
    return commands;
  }

  public Command getAparelho(int commandNumber) {
    return commands[commandNumber];
  }

  public void setAparelho(int commandNumber, Command command) {
    this.commands[commandNumber] = command;
  }

  public void setAparelhos(Command[] commands) {
    this.commands = commands;
  }

  public void buttonAPressed(int commandNumber) {
    this.commands[commandNumber].executeA();
    System.out.println();
  }

  public void buttonBPressed(int commandNumber) {
    this.commands[commandNumber].executeB();
    System.out.println();
  }
}