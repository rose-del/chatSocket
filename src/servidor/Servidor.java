package servidor;

import cliente.Cliente;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

// Classe que representa o servidor e gerencia as conexões.
public class Servidor {

    private int porta;
    private ServerSocket servidorFinal;
    private ArrayList<BufferedWriter> clientes;

    public Servidor(int porta) {
        this.porta = porta;
        this.clientes = new ArrayList<>();
    }

    public void iniciar() {
        try {
            servidorFinal = new ServerSocket(porta);
            JOptionPane.showMessageDialog(null, "Servidor iniciado na porta: " + porta);

            while (true) {
                System.out.println("Aguardando conexão...");
                Socket conexao = servidorFinal.accept();
                System.out.println("Cliente conectado...");

                Thread clienteThread = new Thread(new ClienteHandler(conexao, clientes));
                clienteThread.start();
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao iniciar o servidor: " + e.getMessage());
        }
    }
}
