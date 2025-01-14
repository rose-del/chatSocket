package servidor;

import javax.swing.*;

// Classe principal que inicia o servidor.
public class Main {

    public static void main(String[] args) {

        ConfiguradorServidor configuradorServidor = new ConfiguradorServidor();
        int porta = configuradorServidor.obterPorta();

        if (porta != -1) {
            Servidor servidor = new Servidor(porta);
            servidor.iniciar();
        } else {
            JOptionPane.showMessageDialog(null, "Servidor não iniciado. A configuração foi " +
                    "cancelada.");
        }
    }
}