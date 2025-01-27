package servidor;

import javax.swing.*;

/**
 * Classe principal responsável por iniciar o servidor de chat.
 * <p>
 * Esta classe:
 * <ul>
 *     <li> Cria um objeto ConfiguradorServidor para obter a porta do servidor.</li>
 *     <li> Inicia o servidor com a porta configurada, caso seja válida.</li>
 * </ul>
 */
public class Main {

    /**
     * Ponto de entrada do programa.
     * Inicia o processo de configuração e inicialização do servidor.
     * @param args Argumentos de linha de comando (não utilizados neste caso).
     */
    public static void main(String[] args) {

        // Cria um novo objeto da classe ConfiguradorServidor.
        ConfiguradorServidor configuradorServidor = new ConfiguradorServidor();

        // Irá obter a porta ao qual o responsável pelo servidor digitar.
        int porta = configuradorServidor.obterPorta();

        // Se a porta escolhida for uma porta válida (diferente de -1).
        if (porta != -1) {

            // Cria um objeto da classe Servidor, passando a porta como parâmetro e inicia o mesmo.
            Servidor servidor = new Servidor(porta);
            servidor.iniciar();

        } else {    // Caso a porta escolhida for inválida exibe uma mensagem de erro.
            JOptionPane.showMessageDialog(null, "Servidor não iniciado. A configuração foi " +
                    "cancelada.");
        }
    }
}