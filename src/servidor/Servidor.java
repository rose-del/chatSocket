package servidor;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Servidor {

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

            while (!servidorFinal.isClosed()) { // Verifica se o servidor não foi fechado
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