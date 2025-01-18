package personagens;

import ferramenta.Arma;

public abstract class Personagem {
  protected String nome;
  protected int saude;
  protected boolean vivo;

  protected Arma arma;

  public Personagem() {
    this.saude = 100;
    this.vivo = true;
    this.arma = null;
  }

  public String getNome() {
    return this.nome;
  }

  public boolean estaVivo() {
    return saude > 0;
  }

  public int getSaude() {
    return saude;
  }

  public Arma getArma() {
    return arma;
  }

  public int getForca() {
    return 0;
  }

  public int getPontaria() {
    return 0;
  }

  public boolean estaDesarmado() {
    return arma == null;
  }

  public void pegaArma(Arma a) {
    if (a != null && a.isDisponivel() && estaDesarmado()) {
      a.setDisponivel(false);
      this.arma = a;
    }
  }

  public void largaArma() {
    if (this.arma != null) {
      this.arma.setDisponivel(true);
      this.arma = null;
    }
  }

  public void atacar(Personagem alvo) {
    if (!estaDesarmado() && alvo != null && alvo.estaVivo()) {
      double dano = 0;
      if ("arco".equals(arma.getNome())) {
        dano = Math.ceil((double) getPontaria() / 10 * arma.getDano());
      } else {
        dano = Math.ceil((double) getForca() / 10 * arma.getDano());
      }
      alvo.saude -= (int) dano;
      if (alvo.saude <= 0) {
        alvo.saude = 0;
      }
    }
  }

  @Override
  public String toString() {
    return "Nome: " + this.nome + "\nVida: " + this.saude + "\nArma: "
        + (this.arma != null ? this.arma.getNome() : "Nenhuma");
  }
}
