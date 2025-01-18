package negocio;

public class Controle {
  private Command[] games;

  public Controle() {
    this.games = new Command[5];
    for (int i = 0; i < games.length; i++) {
      this.games[i] = new NoCommand();
    }
  }

  public Command[] getGames() {
    return games;
  }

  public Command getGame(int gameNumber) {
    return games[gameNumber];
  }

  public void setGame(int gameNumber, Command command) {
    this.games[gameNumber] = command;
  }

  public void setGames(Command[] games) {
    this.games = games;
  }

  public void buttonAPressed(int gameNumber) {
    System.out.println("Game " + this.games[gameNumber].getName());
    System.out.println("Button A pressed");
    this.games[gameNumber].executeA();
    System.out.println();
  }

  public void buttonBPressed(int gameNumber) {
    System.out.println("Game " + this.games[gameNumber].getName());
    System.out.println("Button B pressed");
    this.games[gameNumber].executeB();
    System.out.println();
  }
}