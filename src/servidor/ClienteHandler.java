package servidor;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * <b>ClienteHandler</b>
 * <p>
 * Classe responsável por gerenciar a comunicação entre o servidor e um cliente especifico.
 * <p>
 * Implementa a interface {@code Runnable} para que cada instância possa ser executada em uma thread separada.
 *
 * <h3>Responsabilidades principais:</h3>
 * <ul>
 *     <li> Gerenciar a conexão com um cliente por meio de um {@code Socket}.</li>
 *     <li> Receber mensagens do cliente e envia-las para todos os outros clientes conectados.</li>
 *     <li> Encerrar a conexão e limpar os recursos quando o cliente se desconectar.</li>
 * </ul>*/
public class ClienteHandler implements Runnable {

    /**
     * Socket que representa a conexão com o cliente.
     */
    private Socket conexao;

    /**
     * Lista compartilhada de {@code BufferedWriter} representa os clientes conectados.
     * Cada {@code BufferedWriter} é usado para enviar mensagens para o respectivo cliente
     */
    private ArrayList<BufferedWriter> clientes;

    /**
     * Fluxo de leitura de dados enviados pelo cliente.
     */
    private BufferedReader leitorDados;

    /**
     * Fluxo de escrita para enviar dados ao cliente.
     */
    private BufferedWriter escreveDados;

    /**
     * Nome do cliente, fornecido quando ele se conecta ao servidor.
     */
    private String nome;

    /**
     * Contrutor da classe {@code ClienteHandler}.
     * <p>
     * Inicializa os fluxos de entrada e saída para o cliente e adiciona o fluxo de escrita à lista de clientes
     * conectados.
     *
     * @param conexao Socket representando a conexão com o cliente.
     * @param clientes Lista compartilhada de {@code BufferedWriter} dos clientes conectados.
     */
    public ClienteHandler(Socket conexao, ArrayList<BufferedWriter> clientes) {
        this.conexao = conexao;
        this.clientes = clientes;

        try {

            // Inicializa os fluxos de entrada e saída do cliente.
            this.leitorDados = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            this.escreveDados = new BufferedWriter(new OutputStreamWriter(conexao.getOutputStream()));

            // Adiciona o fluxo de escrita do cliente a lista de clientes conectados.
            synchronized (clientes) {
                this.clientes.add(escreveDados);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método executado quando a thread associada a esta instância é iniciada.
     * <p>
     * Gerencia a comunicação com o cliente:
     * <ul>
     *     <li> Lê o nome do cliente.</li>
     *     <li> Processa mensagens para todos os outros clientes conectados.</li>
     *     <li> Envia as mensagens para todos os outros clientes conectados.</li>
     *     <li> Remove o cliente da lista e encerra a conexão quando ele se desconectar.</li>
     * </ul>
     */
    @Override
    public void run() {
        try {

            // Lê o nome do cliente.
            this.nome = leitorDados.readLine();
            String msg;

            // Processa mensagens enviadas pelo cliente.
            while ((msg = leitorDados.readLine()) != null && !msg.equalsIgnoreCase("Sair")) {
                enviarParaTodos(nome + " -> " + msg);
                System.out.println(conexao.getInetAddress().getHostAddress() + " (" + nome + ") -> " + msg);
            }

            // Remove o cliente da lista de clientes conectados.
            synchronized (clientes) {
                clientes.remove(escreveDados);
            }

            // Encerra fluxos e a conexão com o cliente.
            try {
                leitorDados.close();
                escreveDados.close();
                conexao.close();
            } catch (IOException e) {
                System.out.println("Erro ao fechar a conexão com o cliente: " + e.getMessage());
            }

        }   catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envia mensagens para todos os clientes conectados, exceto o cliente que enviou a mensagem.
     *
     * @param mensagem A mensagem a ser enviada.
     */
    private void enviarParaTodos(String mensagem) {

        synchronized (clientes) {
            for (BufferedWriter cliente : clientes) {
                try {

                    // Não envia a mensagem para o próprio cliente.
                    if (cliente != escreveDados) {
                        cliente.write(mensagem + "\r\n");
                        cliente.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
