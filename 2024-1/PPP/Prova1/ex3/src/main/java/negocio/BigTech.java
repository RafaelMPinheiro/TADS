package negocio;

import java.util.ArrayList;

public class BigTech implements Subject {
  private double valorAcao;
  private ArrayList<Observer> vetObserver;

  public BigTech(double valorAcao) {
    this.valorAcao = valorAcao;
    this.vetObserver = new ArrayList<>();
  }

  public double getValorAcao() {
    return valorAcao;
  }

  public void setValorAcao(double valorAcao) {
    this.valorAcao = valorAcao;
    notify(valorAcao);
  }

  @Override
  public void subscribe(Observer observer) {
    this.vetObserver.add(observer);
  }

  @Override
  public void unscribe(Observer observer) {
    this.vetObserver.remove(observer);
  }

  @Override
  public void notify(double valorAcao) {
    for (int i = 0; i < this.vetObserver.size(); i++) {
      this.vetObserver.get(i).update(valorAcao);
    }
  }
}
