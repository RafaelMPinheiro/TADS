package negocio;

abstract class SistemaLogin {
  public SistemaLogin() {
  }

  protected abstract String solicitarUsuario();

  protected abstract String solicitarSenha();

  protected boolean autentificacao(String usuario, String senha) {
    String usuarioCorreto = "Rafael";
    String senhaCorreta = "12345";
    if (usuario.equals(usuarioCorreto) && senha.equals(senhaCorreta)) {
      return true;
    } else {
      return false;
    }
  }

  protected abstract void resposta(boolean autentificacao);

  public void executarLogin() {
    String usuario = solicitarUsuario();
    String senha = solicitarSenha();

    resposta(autentificacao(usuario, senha));
  }
}
