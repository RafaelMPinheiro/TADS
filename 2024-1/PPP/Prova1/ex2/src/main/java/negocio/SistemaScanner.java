package negocio;

import java.util.Scanner;

public class SistemaScanner extends SistemaLogin {
  private Scanner in;

  public SistemaScanner() {
    this.in = new Scanner(System.in);
  }

  @Override
  protected String solicitarUsuario() {
    System.out.print("Informe seu usuario: ");
    String usuario = in.nextLine();
    return usuario;
  }

  @Override
  protected String solicitarSenha() {
    System.out.print("Informe sua senha: ");
    String senha = in.nextLine();
    return senha;
  }

  @Override
  protected void resposta(boolean autentificacao) {
    if (autentificacao) {
      System.out.println("Sucesso no login!");
      return;
    }
    System.out.println("Erro no login!");
  }
}
