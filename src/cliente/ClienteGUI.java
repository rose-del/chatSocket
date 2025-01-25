package cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Classe responsável pela interface gráfica do cliente de chat.
 * <p>
 * Contém os componentes visuais da aplicação, como a janela principal,
 * o painel de mensagens, o campo de texto para digitar mensagens
 * e o botão de enviar.
 */
public class ClienteGUI {
    private JFrame frame;
    private JPanel chatPanel;
    private JScrollPane scrollPane;
    private JTextField mensagemField;
    private JButton sendButton;
    private String userName = "Usuário";
    private  ClienteConexao conexao;

    public ClienteGUI() {
        frame = new JFrame("CHATsZAp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Exibe um diálogo de confirmação quando o usuário tenta fechar a janela.
                int confirmacao = JOptionPane.showConfirmDialog(frame, "Deseja realmente sair do chat?",
                        "Confirmação de Saída", JOptionPane.YES_NO_OPTION
                );

                if (confirmacao == JOptionPane.YES_OPTION) {
                    // Se o usuário confirmar, encerra a conexão e fecha o programa.
                    if (conexao != null) {
                        conexao.desconectar(); // Encerra a conexão com o servidor.
                    }
                    frame.dispose(); // Fecha o frame principal.
                    System.exit(0); // Encerra a aplicação.
                } else {
                    // Se o usuário clicar em "No", cancela o fechamento da janela.
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        // Cria o painel de mensagens.
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(new Color(245, 245, 245));

        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Cria o painel inferior com o campo de texto e o botão de enviar.
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mensagemField = new JTextField();
        sendButton = new JButton("Enviar");

        // Adiciona o campo de texto e o botão de enviar ao painel inferior.
        bottomPanel.add(mensagemField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // Adiciona o painel de mensagens e o painel inferior à janela principal.
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public void fecharJanela() {
        frame.dispose();
    }

    public String solicitarNomeUsuario() {
        this.userName = JOptionPane.showInputDialog(frame, "Nome: ", "username",
                JOptionPane.PLAIN_MESSAGE);

        if (userName == null) {
            JOptionPane.showMessageDialog(frame, "Execução encerrada pelo usuário.", "Encerrado",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0); // Encerra a aplicação
        }

        if (userName.trim().isEmpty()) {
            userName = "Usuário";
        }
        return userName;
    }

    /**
     * Adiciona uma mensagem ao painel de mensagens.
     *
     * @param mensagem Mensagem a ser exibida.

     * @param isSelf Indica se a mensagem foi enviada pelo próprio cliente (true) ou recebida
    de outro cliente (false).
     */
    public void adicionarMensagem(String mensagem, boolean isSelf) {
        JPanel mensagemPanel = new JPanel();
        mensagemPanel.setLayout(new BorderLayout());
        mensagemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel mensagemLabel = new JLabel("<html><p style=\"width: 200px;\">" + mensagem + "</p></html>"); // Cria um rótulo para a mensagem.
        mensagemLabel.setOpaque(true);
        mensagemLabel.setBackground(isSelf ? new Color(220, 248, 198) : Color.WHITE);
        mensagemLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mensagemPanel.add(mensagemLabel, isSelf ? BorderLayout.EAST : BorderLayout.WEST);
        chatPanel.add(mensagemPanel);

        // Atualiza o layout e repinta o painel de mensagens para garantir que a nova mensagem seja exibida.
        chatPanel.revalidate();
        chatPanel.repaint();

        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }

    /**
     * Obtém a mensagem digitada no campo de texto.
     *
     * @return A mensagem digitada no campo de texto, com espaços e caracteres trimmados.
     */
    public String getMensagem() {
        return mensagemField.getText().trim();
    }

    public void limparMensagem() {
        mensagemField.setText("");
    }

    /**
     * Adiciona um listener ao botão de enviar.
     *
     * @param listener O listener a ser adicionado.
     */
    public void adicionarActionListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }
}
