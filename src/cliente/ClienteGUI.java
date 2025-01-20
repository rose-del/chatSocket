package cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClienteGUI {
    private JFrame frame;
    private JPanel chatPanel;
    private JScrollPane scrollPane;
    private JTextField mensagemField;
    private JButton sendButton;
    private String userName = "Usuário";

    public ClienteGUI() {
        frame = new JFrame("CHATsZAp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(new Color(245, 245, 245));
        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mensagemField = new JTextField();
        sendButton = new JButton("Enviar");

        bottomPanel.add(mensagemField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public String solicitarNomeUsuario() {
        this.userName = JOptionPane.showInputDialog(frame, "Nome: ", "userName",
                JOptionPane.PLAIN_MESSAGE);

        if (userName == null || userName.trim().isEmpty()) {
            userName = "Usuário";
        }
        return userName;
    }

    public void adicionarMensagem(String mensagem, boolean isSelf) {
        JPanel mensagemPanel = new JPanel();
        mensagemPanel.setLayout(new BorderLayout());
        mensagemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel mensagemLabel = new JLabel("<html><p style=\"width: 200px;\">" + mensagem + "</p></html>");
        mensagemLabel.setOpaque(true);
        mensagemLabel.setBackground(isSelf ? new Color(220, 248, 198) : Color.WHITE);
        mensagemLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mensagemPanel.add(mensagemLabel, isSelf ? BorderLayout.EAST : BorderLayout.WEST);
        chatPanel.add(mensagemPanel);
        chatPanel.revalidate();
        chatPanel.repaint();
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }

    public String getMensagem() {
        return mensagemField.getText().trim();
    }

    public void limparMensagem() {
        mensagemField.setText("");
    }

    public void adicionarActionListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }
}
