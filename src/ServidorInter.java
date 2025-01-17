import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorInter extends Thread{

    private static ArrayList<BufferedWriter> clientes;
    private static ServerSocket server;
    private String nome;
    private final Socket conexao;
    private BufferedReader bufferedReader;

    public ServidorInter(Socket con) {
        this.conexao = con;

        try {
            InputStream in = con.getInputStream();
            InputStreamReader inr = new InputStreamReader(in);
            this.bufferedReader = new BufferedReader(inr);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        try {
            OutputStream outputStream = this.conexao.getOutputStream();
            Writer outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            clientes.add(bufferedWriter);
            String msg;
            this.nome = msg = this.bufferedReader.readLine();

            while (!"Sair".equalsIgnoreCase(msg) && msg != null) {
                msg = this.bufferedReader.readLine();
                this.sendToAll(bufferedWriter, msg);
                System.out.println(this.conexao.getInetAddress().getHostAddress() + " (" + this.nome + ") -> " + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException {
        for (BufferedWriter bw : clientes) {
            if (bwSaida != bw) {
                bw.write(this.nome + " -> " + msg + "\r\n");
                bw.flush();
            }
        }

    }

    public static void main(String[] args) {
        JTextField textPorta = new JTextField("12345");
        Object[] texts = new Object[]{new JLabel("Porta do servidor: "), textPorta};
        int resposta = JOptionPane.showConfirmDialog((Component) null, texts, "Configurar porta", 2);
        if (resposta == 0) {
            try {
                int porta = Integer.parseInt(textPorta.getText());
                server = new ServerSocket(porta);
                clientes = new ArrayList();
                JOptionPane.showMessageDialog((Component) null, "Servidor na porta: " + porta);

                while (true) {
                    System.out.println("Aguardando conexão...");
                    Socket con = server.accept();
                    System.out.println("Cliente conectado...");
                    Thread t = new Servidor(con);
                    t.start();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog((Component) null, "Erro ao iniciar Servidor: " + e.getMessage());
            }
        } else {
            System.out.println("Servidor não iniciado. A janela foi fechada.");
        }

    }
}


