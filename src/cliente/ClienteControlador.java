package cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe responsável por controlar a interação entre a interface gráfica
 * ({@link ClienteGUI}) e a conexão com o servidor ({@link ClienteConexao}).
 * <p>
 * Gerencia o envio de mensagens, a exibição de mensagens recebidas
 * e a inicialização da aplicação.
 */
public class ClienteControlador {
    private ClienteGUI gui;
    private ClienteConexao conexao;

    public ClienteControlador(ClienteGUI gui, ClienteConexao conexao) {
        this.gui = gui;
        this.conexao = conexao;

        this.gui.adicionarActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensagem();
            }
        });
    }

    /**
     * Envia a mensagem digitada pelo usuário para o servidor.
     * <p>
     * Obtém a mensagem da interface gráfica, envia para o servidor
     * através da conexão, adiciona a mensagem na interface gráfica
     * e limpa o campo de texto.
     */
    private void enviarMensagem() {
        String mensagem = gui.getMensagem();

        if (!mensagem.isEmpty()) {
            // Se o cliente digitar "sair" no chat ele encerrará a conexão e fechará o chat
            if (mensagem.equalsIgnoreCase("Sair")) {
                conexao.desconectar();  // Desconecta do servidor
                gui.fecharJanela(); // Fecha a interface gráfica
            }else{
                conexao.enviarMensagem(mensagem);   // Envia a mensagem
                gui.adicionarMensagem(mensagem, true);  // Adiciona à interface gráfica
                gui.limparMensagem();    // Limpa o campo de texto
            }
        }
    }

    /**
     * Inicia a aplicação.
     * <p>
     * Mostra a interface gráfica, solicita o nome do usuário,
     * conecta ao servidor e inicia a escuta de mensagens.
     */
    public void iniciar() {
        String userName = gui.solicitarNomeUsuario();

        // Exibe uma caixa de diálogo com o IP e a porta do servidor.
        JLabel lblMensagem = new JLabel("Verificar!");
        JTextField txtIP = new JTextField("127.0.0.1");
        JTextField txtPorta = new JTextField("12345");
        Object[] texts = {lblMensagem, "IP: ", txtIP, "Porta: ", txtPorta};

        int opc = JOptionPane.showConfirmDialog(null, texts, "Configuração de conexão",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opc != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Execução encerrada pelo usuário.",
                    "Encerrado", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0); // Encerra a aplicação
        }

        conexao.conectar(txtIP.getText(), Integer.parseInt(txtPorta.getText()), userName);
        gui.mostrar();
        conexao.ouvirMensagem(gui);
    }
}
