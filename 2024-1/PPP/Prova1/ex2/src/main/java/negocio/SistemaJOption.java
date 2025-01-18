package negocio;

import javax.swing.JOptionPane;

public class SistemaJOption extends SistemaLogin {

  @Override
  protected String solicitarUsuario() {
    return JOptionPane.showInputDialog("Insira o usuario:");
  }

  @Override
  protected String solicitarSenha() {
    return JOptionPane.showInputDialog("Insira a senha:");
  }

  @Override
  protected void resposta(boolean autentificacao) {
    if (autentificacao) {
      JOptionPane.showMessageDialog(null, "Sucesso no login!");
      return;
    }
    JOptionPane.showMessageDialog(null, "Erro no login!");
  }
}
