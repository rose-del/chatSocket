package cliente;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class Cliente {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField mensagemFiel;
    private JButton sendButton;
    private Socket socket;
    private OutputStream ou;
    private Writer ouw;
    private BufferedWriter bfw;
    private JTextField txtIP;
    private JTextField txtPorta;
    private JTextField txtNome;

    public Cliente () {
        frame = new JFrame("CHAT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        //chatArea.setEditable(false);
        chatArea.setLineWrap(true); //Ativa a quebra automática de linha para o texto na área.
        chatArea.setWrapStyleWord(true);
        chatArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(chatArea);

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
    }

   /** public void conectar() throws IOException {
        socket = new Socket(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
        ou = socket.getOutputStream();
        ouw = new OutputStreamWriter(ou);
        bfw = new BufferedWriter(ouw);
        bfw.write(txtNome.getText() + "\r\n");
        bfw.flush();
    }
    **/

    private void enviarMensagem() {
        String mensagem = mensagemFiel.getText().trim();
        if (!mensagem.isEmpty()) {
            chatArea.append("Você: " + mensagem + "\n");
            mensagemFiel.setText("");
        }
    }

    /** private void ouvirMensagens() {
        String mensagemRecebida;
        try {
            while ((mensagemRecebida = reader.readLine()) != null) {
                chatArea.append(mensagemRecebida + "\n");
            }
        } catch (IOException ex) {
            chatArea.append("Conexão encerrada: " + ex.getMessage() + "\n");
        }
    } **/

    public void mostrar() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Cliente cli = new Cliente();
        cli.mostrar();
    }
}
