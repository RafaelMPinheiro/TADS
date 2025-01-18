package negocio;

import java.time.LocalDate;
import java.time.Period;

public class Empregado {
  private String nome;
  private int idade;
  private LocalDate dataNascimento;
  private boolean habilitado;
  private Garagem garagem;

  public Empregado(String nome, LocalDate dataNascimento) {
    this.nome = nome;
    this.dataNascimento = dataNascimento;
    this.habilitado = false;
  }

  public Empregado(String nome, LocalDate dataNascimento, boolean habilitado) {
    this.nome = nome;
    this.idade = Period.between(dataNascimento, LocalDate.now()).getYears();
    this.dataNascimento = dataNascimento;
    this.habilitado = habilitado;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getIdade() {
    return idade;
  }

  public void setIdade(int idade) {
    this.idade = idade;
  }

  public LocalDate getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(LocalDate dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public boolean isHabilitado() {
    return this.habilitado;
  }

  public void setHabilitado(boolean habilitado) {
    this.habilitado = habilitado;
  }

  public Garagem getGaragem() {
    return this.garagem;
  }

  public void setGaragem(Garagem garagem) {
    this.garagem = garagem;
  }
}
