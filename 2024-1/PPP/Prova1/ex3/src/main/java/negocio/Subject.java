package negocio;

public interface Subject {
  void subscribe(Observer observer);

  void unscribe(Observer observer);

  void notify(double valorAcao);
}
