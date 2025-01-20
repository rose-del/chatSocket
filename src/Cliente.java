import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class Cliente {
    private JFrame frame;
    private JTextField txtIP;
    private JTextField txtPorta;
    private JPanel chatPanel; // Área que exibe as mensagens do chat
    private JScrollPane scrollPane; // Permite rolar o painel de mensagens
    private JTextField mensagemFiel;
    private JButton sendButton;
    private String userName = "Usuário";
    private Socket socket; // Representa a conexão com o servidor
    /**
     * OutputStream, Writer e BufferedWriter são utilizados
     * para enviar mensagens ao servidor.
     **/
    private OutputStream ou;
    private Writer ouw;
    private BufferedWriter bfw;


    /**
     * Configura e inicializa a interface gráfica da aplicação de chat
     *
     * Responsável por:
     * - Criação da janela principal do chat (frame)
     * - Configuração do layout e dos componentes principais da interface:
     *      - Painel de exibição de mensagens (chatPanel)
     *      - Barra de rolagem associada ao painel de mensagens (scrollPane)
     *      - Campo de entrada de mensagens e botão de envio na parte inferior
     * - Definição do comportamento ao fechar a janela
     **/
    public Cliente () {
        frame = new JFrame("CHAT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define que a aplicação será encerrada ao fechar a janela.
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        JLabel lblMessage = new JLabel("Verificar!");
        txtIP = new JTextField("127.0.0.1");
        txtPorta = new JTextField("12345");
        Object[] texts = {lblMessage, txtIP, txtPorta};
        JOptionPane.showMessageDialog(null, texts);

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS)); // Define o layout do painel para organizar as mensagens em uma pilha vertical.
        chatPanel.setBackground(new Color(245, 245, 245)); // Cor do fundo do painel.
        scrollPane = new JScrollPane(chatPanel); // Adiciona um painel de rolagem ao painel de mensagens.
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Configura para que apenas a barra de rolagem vertical seja usada.

        // Criação do painel inferior para entrada de mensagens
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mensagemFiel = new JTextField();
        sendButton = new JButton("Enviar");

        bottomPanel.add(mensagemFiel, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Define o comportamento do botão "Enviar" para enviar mensagens
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensagem();
            }
        });

        // Define o comportamento ao fechar a janela para desconectar o cliente do servidor
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                desconectar();
            }
        });

        solicitarNomeUsuario(); // Solicita o nome do usuário assim que o cliente é iniciado
    }

    /**
     * Gerencia as principais funcionalidades do cliente do chat.
     * Inclui a configuração do nome do usuário, conexão ao servidor, envio e recebimento de mensagens,
     * manipulação de interface, e o gerenciamento da desconexão.
     **/


    /**
     * Solicita o nome do usuário antes da conexão, através de uma caixa de diálogo.
     * Caso o usuário não insira um nome válido (nulo ou vazio), será utilizado o nome padrão "Usuário".
     */
    private void solicitarNomeUsuario() {
        userName = JOptionPane.showInputDialog(frame, "Nome:", "userName", JOptionPane.PLAIN_MESSAGE);
        if (userName == null || userName.trim().isEmpty()) {
            userName = "Usuário";
        }
    }

    /**
     * Realiza a conexão com o servidor de chat.
     *
     * @param ip    Endereço IP do servidor.
     * @param porta Porta utilizada para a conexão.
     *
     * Responsável por:
     * - Estabelecer a conexão com o servidor.
     * - Enviar o nome do usuário ao servidor.
     * - Iniciar o metodo de escuta para receber mensagens do servidor.
     * - Em caso de erro na conexão, uma mensagem será exibida ao usuário.
     */
    public void conectar() {
        try {
            socket = new Socket(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
            ou = socket.getOutputStream();
            ouw = new OutputStreamWriter(ou);
            bfw = new BufferedWriter(ouw);

            bfw.write(userName + "\r\n"); // Envia o nome do cliente ao servidor.
            bfw.flush();

            ouvirMensagens();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao conectar: " + e.getMessage());
        }
    }


    /**
     * Envia a mensagem digitada pelo usuário ao servidor de chat.
     *
     * Responsável por:
     * - Verificar se a mensagem não está vazia.
     * - Enviar a mensagem ao servidor.
     * - Adicionar a mensagem à interface do cliente.
     * - Limpar o campo de entrada após o envio.
     * - Em caso de erro, exibe uma mensagem ao usuário.
     */
    private void enviarMensagem() {
        String mensagem = mensagemFiel.getText().trim();
        if (!mensagem.isEmpty()) {
            try {
                bfw.write(mensagem + "\r\n");
                bfw.flush();
                adicionarMensagem(mensagem, true); // Mostra no chat do cliente.
                mensagemFiel.setText("");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Erro ao enviar mensagem: " + e.getMessage());
            }
        }
    }


    /**
     * Exibe uma mensagem na interface do chat.
     *
     * @param mensagem Mensagem a ser exibida.
     * @param isSelf   Indica se a mensagem foi enviada pelo próprio cliente (true) ou recebida de outro cliente (false).
     *
     * Configurações:
     * - Define o layout e o fundo das mensagens.
     * - Diferencia mensagens enviadas e recebidas.
     * - Atualiza o painel de mensagens e rola automaticamente para a última mensagem.
     */
    private void adicionarMensagem(String mensagem, boolean isSelf) {
        JPanel mensagemPanel = new JPanel();
        mensagemPanel.setLayout(new BorderLayout());
        mensagemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel mensagemLabel = new JLabel("<html><p style=\"width: 200px;\">" + mensagem + "</p></html>");
        mensagemLabel.setOpaque(true);
        mensagemLabel.setBackground(isSelf ? new Color(220, 248, 198) : Color.WHITE);
        mensagemLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mensagemPanel.add(mensagemLabel, isSelf ? BorderLayout.EAST : BorderLayout.WEST);
        chatPanel.add(mensagemPanel);
        chatPanel.revalidate();
        chatPanel.repaint();
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }


    /**
     * Finaliza a conexão com o servidor
     * Fecha os recursos utilizados na conexão (buffer e socket) ao desconectar do servidor.
     * Em caso de erro durante o encerramento, o erro será exibido no console.
     */
    private void desconectar() {
        try {
            if (bfw != null) bfw.close();
            if (socket != null) socket.close();
        } catch (IOException ex) {
            System.err.println("Erro ao desconectar: " + ex.getMessage());
        }
    }

    /**
     * Escuta mensagens recebidas do servidor
     * Inicia uma nova thread para escutar mensagens recebidas.
     * Cada mensagem recebida será exibida no painel do cliente.
     * Em caso de encerramento da conexão, será exibida uma mensagem ao usuário.
     */
    private void ouvirMensagens() {
        new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String mensagemRecebida;
                while ((mensagemRecebida = br.readLine()) != null) {
                    adicionarMensagem(mensagemRecebida, false); // Adiciona a mensagem de outro cliente.
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Conexão encerrada: " + e.getMessage());
            }
        }).start();
    }


    public void mostrar() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Cliente cli = new Cliente();
        cli.mostrar();
        cli.conectar(); // Conecta ao servidor no endereço e porta especificados.
    }
}
