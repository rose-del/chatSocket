package servidor;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

// Classe que gerencia a comunicação com um cliente
public class ClienteHandler implements Runnable {

    private Socket conexao;
    private ArrayList<BufferedWriter> clientes;
    private BufferedReader leitorDados;
    private BufferedWriter escreveDados;
    private String nome;

    public ClienteHandler(Socket conexao, ArrayList<BufferedWriter> clientes) {
        this.conexao = conexao;
        this.clientes = clientes;

        try {
            this.leitorDados = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            this.escreveDados = new BufferedWriter(new OutputStreamWriter(conexao.getOutputStream()));

            synchronized (clientes) {
                this.clientes.add(escreveDados);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            nome = leitorDados.readLine();
            String msg;

            while ((msg = leitorDados.readLine()) != null && !msg.equalsIgnoreCase("Sair")) {
                enviarParaTodos(nome + " -> " + msg);
                System.out.println(conexao.getInetAddress().getHostAddress() + " (" + nome + ") -> " + msg);
            }

            synchronized (clientes) {
                clientes.remove(escreveDados);
            }

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

    private void enviarParaTodos(String mensagem) {

        synchronized (clientes) {
            for (BufferedWriter cliente : clientes) {
                try {
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
