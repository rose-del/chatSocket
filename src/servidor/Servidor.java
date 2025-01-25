package servidor;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * <b>Servidor</b>
 * <p>
 * Classe responsável por gerenciar as conexões e comunicações entre clientes em um servidor de chat simples.
 * <p>
 * <i>Responsabilidades:</i>
 * <ul>
 *     <li> Inicializar e configurar o servidor na porta especificada.</li>
 *     <li> Aguardar conexões de clientes.</li>
 *     <li> Gerenciar uma lista de clientes conectados.</li>
 *     <li> Delegar o tratamento das conexões para uma thread separada.</li>
 * </ul>
 * <p>
 * Esta classe utiliza a classe auxiliar {@link ClienteHandler} para gerenciar as iterações individuais com
 * cada cliente conectado.*/
class Servidor {

    /**
     * Porta na qual o servidor será iniciado.
     */
    private final int porta;

    /**
     * Socket principal do servidor, responsável por aceitar conexões.
     * */
    private ServerSocket servidorFinal;

    /**
     * Lista de buffers para gerenciar os clientes conectados e facilitar a comunicação.
     * */
    private ArrayList<BufferedWriter> clientes;

    /**
     * Construtor da classe Servidor.
     * @param porta Porta na qual o servidor será iniciado.*/
    public Servidor(int porta) {
        this.porta = porta;
        this.clientes = new ArrayList<>();
    }

    /**
     * Inicia o servidor, permitindo conexões de clientes e gerenciando a comunicação.
     * <p>
     * O método:
     * <ul>
     *     <li> Inicializa o socket do servidor na porta especificada.</li>
     *     <li> Exibe uma mensagem indicando que o servidor foi iniciado com sucesso.</li>
     *     <li> Aguarda conexões de clientes de forma contínua.</li>
     *     <li> Cria uma nova thread para cada cliente conectado, delegando a interação ao {@link ClienteHandler}.</li>
     * </ul>*/
    public void iniciar() {
        try {

            // Inicializa o socket do servidor na porta especificada.
            servidorFinal = new ServerSocket(porta);
            JOptionPane.showMessageDialog(null, "Servidor iniciado na porta: " + porta);

            // Aguarda conexões de clientes enquanto o servidor estiver ativo.
            while (!servidorFinal.isClosed()) { // Verifica se o servidor não foi fechado
                System.out.println("Aguardando conexão...\n");

                // Aceita a conexão do cliente.
                Socket conexao = servidorFinal.accept();
                System.out.println("\nCliente conectado...");

                // Cria e inicia uma thread para lidar com o cliente conectado.
                Thread clienteThread = new Thread(new ClienteHandler(conexao, clientes));
                clienteThread.start();
            }
        } catch (IOException e) {

            // trata erros ao inicializar o servidor ou aceitar conexões.
            JOptionPane.showMessageDialog(null, "Erro ao iniciar o servidor: " + e.getMessage());
        }
    }
}
