import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class Cliente {
    private JFrame frame;
    private JPanel chatPanel;
    private JScrollPane scrollPane;
    private JTextField mensagemFiel;
    private JButton sendButton;
    private String userName = "Você";
    private Socket socket;
    private OutputStream ou;
    private Writer ouw;
    private BufferedWriter bfw;

    public Cliente () {
        frame = new JFrame("CHAT");
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

        mensagemFiel = new JTextField();
        sendButton = new JButton("Enviar");

        bottomPanel.add(mensagemFiel, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensagem();
            }
        });

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                desconectar();
            }
        });

        solicitarNomeUsuario();
    }

    private void solicitarNomeUsuario() {
        userName = JOptionPane.showInputDialog(frame, "Nome:", "userName", JOptionPane.PLAIN_MESSAGE);
        if (userName == null || userName.trim().isEmpty()) {
            userName = "Usuário";
        }
    }

    public void conectar(String ip, int porta) {
        try {
            socket = new Socket(ip, porta);
            ou = socket.getOutputStream();
            ouw = new OutputStreamWriter(ou);
            bfw = new BufferedWriter(ouw);

            bfw.write(userName + "\r\n"); // Envia o nome do cliente ao servidor.
            bfw.flush();

            ouvirMensagens();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao conectar: " + e.getMessage());
        }
    }



    private void enviarMensagem() {
        String mensagem = mensagemFiel.getText().trim();
        if (!mensagem.isEmpty()) {
            try {
                bfw.write(mensagem + "\r\n"); // Envia a mensagem ao servidor.
                bfw.flush();
                adicionarMensagem(mensagem, true); // Mostra no chat do cliente.
                mensagemFiel.setText("");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Erro ao enviar mensagem: " + e.getMessage());
            }
        }
    }


    private void adicionarMensagem(String mensagem, boolean isSelf) {
        JPanel mensagemPanel = new JPanel();
        mensagemPanel.setLayout(new BorderLayout());
        mensagemPanel.setBorder(BorderFactory.createEmptyBorder(55, 55, 50, 15));

        JLabel mensagemLabel = new JLabel("<html><p style=\"width: 200px;\">" + mensagem + "</p></html>");
        mensagemLabel.setOpaque(true);
        mensagemLabel.setBackground(isSelf ? new Color(220, 248, 198) : Color.WHITE);
        mensagemLabel.setBorder(BorderFactory.createEmptyBorder(55, 55, 50, 15));

        mensagemPanel.add(mensagemLabel, isSelf ? BorderLayout.EAST : BorderLayout.WEST);
        chatPanel.add(mensagemPanel);
        chatPanel.revalidate();
        chatPanel.repaint();
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }

    private void desconectar() {
        try {
            if (bfw != null) bfw.close();
            if (socket != null) socket.close();
        } catch (IOException ex) {
            System.err.println("Erro ao desconectar: " + ex.getMessage());
        }
    }

    private void ouvirMensagens() {
        new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String mensagemRecebida;
                while ((mensagemRecebida = br.readLine()) != null) {
                    adicionarMensagem(mensagemRecebida, false); // Adiciona a mensagem de outro cliente.
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Conexão encerrada: " + e.getMessage());
            }
        }).start();
    }


    public void mostrar() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Cliente cli = new Cliente();
        cli.mostrar();
        cli.conectar("127.0.0.1", 12345);
    }
}
