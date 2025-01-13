package servidor;

// Classe principal que inicia o servidor.
public class Main {

    public static void main(String[] args) {
        ConfiguradorServidor configuradorServidor = new ConfiguradorServidor();
        int porta = configuradorServidor.obterPorta();

        if (porta != -1) {
            Servidor servidor = new Servidor(porta);
            servidor.iniciar();
        } else {
            System.out.println("Servidor não iniciado. A configuração foi cancelada.");
        }
    }
}