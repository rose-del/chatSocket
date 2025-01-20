package cliente;

public class Cliente {

    public static void main(String[] args) {
        ClienteGUI gui = new ClienteGUI();
        ClienteConexao conexao = new ClienteConexao();
        ClienteControlador controlador = new ClienteControlador(gui, conexao);
        controlador.iniciar();
    }
}
