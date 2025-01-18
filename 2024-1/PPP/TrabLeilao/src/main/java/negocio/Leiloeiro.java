package negocio;

import java.util.ArrayList;

public class Leiloeiro implements Subject {
  private Leilao leilao;
  private ArrayList<Observer> vetObserver;

  public Leiloeiro(Leilao leilao) {
    this.leilao = leilao;
    this.vetObserver = new ArrayList<>();
    subscribe(leilao);
  }

  public void finalizarLeilao() {
    this.leilao.getHistorico();
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
  public void notify(double valorLance, String nomeLicitante) {
    for (int i = 0; i < this.vetObserver.size(); i++) {
      this.vetObserver.get(i).update(valorLance, nomeLicitante);
    }
  }

  @Override
  public void avaliarLance(double valorLance, Licitante licitante) {
    if (valorLance > this.leilao.getValorAtual() && valorLance > this.leilao.getValorMinimo()) {
      this.notify(valorLance, licitante.getNome());
    }
  }
}
