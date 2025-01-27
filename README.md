# ChatsZap - Projeto em Java

Este projeto implementa um sistema de chat utilizando sockets em Java. 
Ele demonstra a comunicação bidirecional entre um servidor e múltiplos clientes,
utilizando threads para o gerenciamento simultâneo de conexões.

## Descrição do Projeto

Este sistema de chat utiliza sockets para permitir que múltiplos clientes se conectem a um servidor central, possibilitando a troca de mensagens em tempo real.
Os sockets atuam como a "interface" de comunicação entre processos, oferecendo uma abstração de comunicação lógica entre os clientes conectados e o servidor. 
O servidor gerencia conexões, utiliza threads para garantir comunicação simultânea e implementa timeouts para tratar possíveis falhas.

## Tecnologias Utilizadas

- Linguagem: Java
- Sockets: Java Socket API
- Threads: Java Multithreading
- IDE recomendada: IntelliJ IDEA ou VSCode

## Estrutura do Projeto:
### cliente
- `Cliente.java`: Cria as instâncias das classes ClienteGUI, ClienteConexao e ClienteControlador.
- `ClienteConexao.java`: Responsável pela conexão com o servidor de chat.
- `ClienteControlador.java`: Responsável por controlar a interação entre a interface gráfica e a conexão com o servidor.
- `ClienteGUI.java`: Responsável pela interface gráfica do cliente de chat.

### servidor
- `Main.java`: Inicia o processo de configuração e inicialização do servidor.
- `ClienteHandler.java`: Responsável por gerenciar a comunicação entre o servidor e um cliente especifico.
- `ConfiguradorServidor.java`: Responsável por obter a porta do servidor por meio de uma interface gráfica simples.
- `Servidor.java`: Responsável por gerenciar as conexões e comunicações entre clientes em um servidor.

## Como Executar

### Pré-requisitos:

- JDK instalado (versão 11 ou superior).

- IDE de sua preferência (recomendado: IntelliJ IDEA ou VScode).

### Passos para execução:

- **Clone este repositório:**
```bash
git clone https://github.com/usuario/chat-socket-java.git
```
- **Navegue até o diretório do projeto:**
```bash
cd chat-socket-java
```
