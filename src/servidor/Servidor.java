package servidor;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


// "servidor.Servidor extends Thread" para permitir que cada conexão de cliente seja executada de forma independente.
public class Servidor extends Thread {

    // Armazena os fluxos de saída (BufferedWriter) de todos os clientes conectados, permitindo envio de mensagens a -
    // todos.
    private static ArrayList<BufferedWriter> clientes;
    private static ServerSocket server;                 // Representa o servidor que aceita conexões.
    private String nome;                                // Nome do cliente conectado.
    private final Socket conexao;                       // O socket de conexão especifico para o cliente conectado
    private BufferedReader bufferedReader;              // Para ler mensagens recebidas do cliente


    /**
     * Constructor recebe um Socket (conexão com o cliente) e inicializa o fluxo de entrada para ler mensagens.
     * @param con do Tipo Socket para receber a conexão do cliente
     * */
    public Servidor(Socket con) {
        this.conexao = con;     // Armazena o objeto Socket recebido pelo construtor na variável conexao.

        try {

            // Obtém o fluxo de entrada associado ao socket (con) do cliente.
            // Esse fluxo permite ler dados enviados pelo cliente.
            InputStream in = con.getInputStream();

            // Converte o fluxo de entrada bruto (InputStream) em um leitor de caracteres (InputStreamReader).
            // Importante, pois o InputStream trabalha com bytes, e o InputStreamReader os traduz para caracteres.
            InputStreamReader inr = new InputStreamReader(in);

            // Envolve InputStream inr em um BufferedReader.
            // O BufferedReader oferece métodos mais eficientes e convenientes para a leitura de textos.
            this.bufferedReader = new BufferedReader(inr);

            /* Trata qualquer erro (exceção) do tipo IOException que possa ocorrer durante a obtenção
            do fluxo de entrada ou durante a criação dos leitores.
            EX: Caso ocorra uma exceção (por exemplo, se não for possível abrir a conexão
            ou se ocorrer algum erro de leitura), a exceção será capturada e o erro será impresso no console.*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * O método run() da classe servidor.Servidor é o ponto de entrada da thread associada a cada cliente conectado.
     * Ele define o comportamento que será executado paralelamente para cada conexão de cliente.
     * O método run() é parte da classe Thread e precisa ser sobrescrito para especificar o que a thread deve executar.
     * Ao estender Thread, cada instância da classe servidor.Servidor representa uma thread separada.
     * Isso permite que o servidor lide com múltiplos clientes simultaneamente.
     * **/
    @Override
    public void run() {

        try {

            String msg; // Inicializa uma variável para armazenar as mensagens recebidas dos clientes.

            // Obtém o fluxo de saída para enviar dados ao cliente.
            OutputStream outputStream = this.conexao.getOutputStream();

            // Converte o fluxo de saída bruto em caracteres.
            Writer outputStreamWriter = new OutputStreamWriter(outputStream);

            // Melhora a eficiência do envio de dados e oferece métodos convenientes, como writer.
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            clientes.add(bufferedWriter); // Adiciona o BufferedWriter à lista de clientes conectados.
            nome = msg = bufferedReader.readLine(); // Lê o nome do cliente.

            // Enquanto a mensagem não for "sair" e não for null fica no loop
            while (!"Sair".equalsIgnoreCase(msg) && msg != null) {

                msg = bufferedReader.readLine();            // Lê a próxima linha de mensagem do cliente
                sendToAll(bufferedWriter, msg);             // Envia a mensagem para todos os clientes conectados

                // Exibe a mensagem no console do servidor.
                System.out.println(conexao.getInetAddress().getHostAddress() + " (" +  nome + ") -> " + msg);
            }

            // Captura exceções que possam ocorrer durante a comunicação com o cliente como alguém desconectado abruptamente.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método usado para enviar mensagem para todos os clientes conectados ao servidor
     * @param bwSaida  O fluxo de saída do cliente que enviou a mensagem (quem não deve receber a mensagem)
     * @param msg A mensagem que será enviada para os outros clientes
     * @throws IOException Declara que o método pode lançar uma exceção do tipo IOException, geralmente relacionada a
     * problemas de escrita nos fluxos.
     */
    public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException {

        // Declara uma variável local bwS do tipo BufferedWriter.
        // Usada dentro do loop para referenciar os clientes conectados.
        BufferedWriter bwS;

        // Clientes: É a lista que contém os fluxos de saída (BufferedWriter) de todos os clientes conectados ao servidor.
        // bw: Representa o fluxo de saída de um cliente específico durante cada iteração.
        for (BufferedWriter bw : clientes) {
            bwS = bw;                                       // Atribui o fluxo do cliente atual (bw) à variável bwS.

            // Condição que garante que a mensagem não seja enviada de volta ao cliente que a enviou.
            if (!(bwSaida == bwS)) {

                // Escreve a mensagem para o cliente atual.
                bw.write(nome + " -> " + msg + "\r\n");

                // Garante que o conteúdo armazenado no buffer seja enviado imediatamente ao cliente.
                // Sem o flush os dados podem permanecer no buffer, atrasando o envio.
                bw.flush();
            }
        }
    }

    /***
     * Método Main
     * @param args
     */
    public static void main(String[] args) {

        // Criação do JTextField para receber a porta
        JTextField textPorta = new JTextField("12345");
        Object[] texts = {new JLabel("Porta do servidor: "), textPorta};

        // Exibe o JOptionPane para o usuário escolher a porta
        int resposta = JOptionPane.showConfirmDialog(null, texts, "Configurar porta",
                JOptionPane.OK_CANCEL_OPTION);

        // Se o usuário pressionar OK, continua com a execução
        if (resposta == JOptionPane.OK_OPTION) {

            try {
                // Obtém a porta fornecida pelo usuário
                int porta = Integer.parseInt(textPorta.getText());

                // Criação do ServerSocket
                server = new ServerSocket(porta);
                clientes = new ArrayList<BufferedWriter>();
                JOptionPane.showMessageDialog(null, "servidor.Servidor na porta: " + porta);

                // Aguardando conexões infinitamente a menos que o código seja interrompido
                while (true) {
                    System.out.println("Aguardando conexão...");
                    Socket con = server.accept();
                    System.out.println("cliente.Cliente conectado...");
                    Thread t = new Servidor(con);
                    t.start();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao iniciar servidor.Servidor: " + e
                        .getMessage());
            }
        } else {
            // Se o usuário cancelar ou fechar a janela, o servidor não será iniciado
            System.out.println("servidor.Servidor não iniciado. A janela foi fechada.");
        }
    }
}
