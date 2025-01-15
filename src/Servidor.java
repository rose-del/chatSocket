import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor extends Thread {
    private static ArrayList<BufferedWriter> clientes;
    private static ServerSocket server; // Representa o servidor que aceita conexões.
    private String nome; // Nome do cliente conectado.
    private final Socket conexao; // O socket de conexão especifico para o cliente conectado
    private BufferedReader bufferedReader; // Para ler mensagens recebidas do client
    private BufferedWriter bufferedWriter;

    public Servidor(Socket con) {
        this.conexao = con;// Armazena o objeto Socket recebido pelo construtor na variável conexao.
        try {
            InputStream in = con.getInputStream();
            InputStreamReader inr = new InputStreamReader(in);
            this.bufferedReader = new BufferedReader(inr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String msg;
            OutputStream outputStream = this.conexao.getOutputStream();
            Writer outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            clientes.add(bufferedWriter); // Adiciona o cliente à lista.
            nome = bufferedReader.readLine(); // Lê o nome do cliente ao se conectar.
            sendToAll(null, nome + " entrou no chat."); // Notifica todos os clientes sobre a entrada.

            while ((msg = bufferedReader.readLine()) != null && !"Sair".equalsIgnoreCase(msg)) {
                sendToAll(bufferedWriter, msg); // Envia a mensagem para todos os clientes.
                System.out.println(nome + " -> " + msg); // Log no servidor.
            }

            sendToAll(null, nome + " saiu do chat."); // Notifica todos sobre a saída.
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Remove o cliente da lista e fecha os recursos.
                clientes.removeIf(bw -> bw.equals(bufferedWriter));
                if (bufferedReader != null) bufferedReader.close();
                if (conexao != null) conexao.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException {
        BufferedWriter bwS;
        for (BufferedWriter bw : clientes) {
            bwS = bw; // Atribui o fluxo do cliente atual (bw) à variável bwS.
            if (!(bwSaida == bwS)) {
                bw.write(nome + " -> " + msg + "\r\n");
                bw.flush();
            }
        }
    }

    public static void main(String[] args) {
        JTextField textPorta = new JTextField("12345");
        Object[] texts = {new JLabel("Porta do servidor: "), textPorta};

        int resposta = JOptionPane.showConfirmDialog(null, texts, "Configurar porta",
                JOptionPane.OK_CANCEL_OPTION);
        if (resposta == JOptionPane.OK_OPTION) {
            try {
                int porta = Integer.parseInt(textPorta.getText());
                server = new ServerSocket(porta);
                clientes = new ArrayList<BufferedWriter>();
                JOptionPane.showMessageDialog(null, "Servidor na porta: " + porta);

                while (true) {
                    System.out.println("Aguardando conexão...");
                    Socket con = server.accept();
                    System.out.println("Cliente conectado...");
                    Thread t = new Servidor(con);
                    t.start();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao iniciar Servidor: " + e
                        .getMessage());
            }
        } else {
            System.out.println("Servidor não iniciado. A janela foi fechada.");
        }
    }
}

