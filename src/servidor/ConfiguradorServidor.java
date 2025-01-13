package servidor;

import javax.swing.*;

// Classe responsável pela interface visual para configuração do servidor.
public class ConfiguradorServidor {

    public int obterPorta() {
        JTextField textPorta = new JTextField("12345");
        JLabel lblPortaServidor = new JLabel("Porta do servidor: ");
        Object[] texts = {lblPortaServidor, textPorta};

        int resposta = JOptionPane.showConfirmDialog(null, texts, "Configurar porta",
                JOptionPane.OK_CANCEL_OPTION);

        if (resposta == JOptionPane.OK_OPTION) {
            try {
                return Integer.parseInt(textPorta.getText());

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Porta inválida. Por favor, insira um " +
                        "número válido");
            }
        }

        return -1;
    }
}
