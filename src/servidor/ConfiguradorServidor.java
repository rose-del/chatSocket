package servidor;

import javax.swing.*;

/**
* <b>ConfiguradorServidor</b>
* <p>
* Classe responsável por obter a porta do servidor por meio de uma interface gráfica simples.
*<p>
*<i>Responsabilidades:</i>
 * <ul>
 *     <li> Cria uma caixa de diálogo para que o usuário insira a porta do servidor.
 *     <li> Valida a entrada do usuário para garantir que seja um número inteiro válido.
 *     <li> Retorna a porta configurada pelo usuário ou -1 em caso de erro.
 * </ul>
*/
public class ConfiguradorServidor {

    /**
     * Obtém a porta do servidor a partir da entrada do usuário.
     *
     * @return A porta configurada pelo usuário, ou -1 se a entrada for inválida.
     */
    public int obterPorta() {

        // Cria um campo de texto para a entrada da porta com um valor padrão.
        JTextField textPorta = new JTextField("12345");

        // Cria um rótulo para identificar o campo de texto.
        JLabel lblPortaServidor = new JLabel("Porta do servidor: ");

        // Cria um array de objetos para exibir uma caixa de diálogos.
        Object[] texts = {lblPortaServidor, textPorta};

        // Exibe a caixa de diálogo e obtém a resposta do usuário.
        int resposta = JOptionPane.showConfirmDialog(null, texts, "Configurar porta",
                JOptionPane.OK_CANCEL_OPTION);

        // Se o usuário clicou em OK, tenta converter a entrada em um número inteiro.
        if (resposta == JOptionPane.OK_OPTION) {
            try {

                // Retorna a entrada em número inteiro.
                return Integer.parseInt(textPorta.getText());

            } catch (NumberFormatException e) {

                // Exibe uma mensagem de erro caso a entrada não seja um número.
                JOptionPane.showMessageDialog(null, "Porta inválida. Por favor, insira um " +
                        "número válido");
            }
        }

        // Retorna -1 para indicar que a configuração foi cancelada ou a entrada foi inválida.
        return -1;
    }
}
