package cliente;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ClienteConexao {
    private Socket socket;
    private OutputStream ou;
    private Writer ouw;
    private BufferedWriter bfw;

    public void conectar(String ip, int porta, String userName) {
        try {
            socket = new Socket(ip, porta);
            ou = socket.getOutputStream();
            ouw = new OutputStreamWriter(ou);
            bfw = new BufferedWriter(ouw);

            bfw.write(userName + "\r\n");
            bfw.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar: " + e.getMessage());
        }
    }

    public void enviarMensagem(String mensagem) {
        try {
            bfw.write(mensagem + "\r\n");
            bfw.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    public void ouvirMensagem(ClienteGUI gui) {
        new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String mensagemRecebida;
                while ((mensagemRecebida = br.readLine()) != null) {
                    gui.adicionarMensagem(mensagemRecebida, false);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Conexão encerrada: " + e.getMessage());
            }
        }).start();
    }

    public void desconectar() {
        try {
            if (bfw != null) {
                bfw.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            System.err.println("Erro ao desconectar: " + ex.getMessage());
        }
    }
}
