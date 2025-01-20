package cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private void enviarMensagem() {
        String mensagem = gui.getMensagem();
        if (!mensagem.isEmpty()) {
            conexao.enviarMensagem(mensagem);
            gui.adicionarMensagem(mensagem, true);
            gui.limparMensagem();
        }
    }

    public void iniciar() {

        String userName = gui.solicitarNomeUsuario();

        JLabel lblMensagem = new JLabel("Verificar!");
        JTextField txtIP = new JTextField("127.0.0.1");
        JTextField txtPorta = new JTextField("12345");
        Object[] texts = {lblMensagem, txtIP, txtPorta};
        JOptionPane.showMessageDialog(null, texts);

        conexao.conectar(txtIP.getText(), Integer.parseInt(txtPorta.getText()), userName);
        gui.mostrar();
        conexao.ouvirMensagem(gui);

    }
}
