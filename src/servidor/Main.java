package servidor;

import javax.swing.*;

// Classe principal que inicia o servidor.
public class Main {

    public static void main(String[] args) {

        // Cria um novo objeto da classe ConfiguradorServidor.
        ConfiguradorServidor configuradorServidor = new ConfiguradorServidor();

        // Vai obter a porta que o responsável pelo servidor digitar.
        int porta = configuradorServidor.obterPorta();

        // Se a porta escolhida for uma porta válida:
        if (porta != -1) {

            // Cria um objeto da classe Servidor, passando a porta como parâmetro.
            Servidor servidor = new Servidor(porta);
            servidor.iniciar(); // Inicia o servidor.
        } else {    // Se a porta escolhida for inválida:
            JOptionPane.showMessageDialog(null, "Servidor não iniciado. A configuração foi " +
                    "cancelada.");
        }
    }
}