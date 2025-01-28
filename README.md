# ChatsZap - Projeto em Java

O ChatsZap é um sistema de chat desenvolvido em Java, utilizando sockets para comunicação e Java Swing para a interface gráfica. 
Ele demonstra a comunicação bidirecional entre um servidor e múltiplos clientes,
utilizando threads para o gerenciamento simultâneo de conexões.

## Descrição do Projeto

O ChatsZap permite que vários clientes se conectem a um servidor central, 
possibilitando o envio e recebimento de mensagens em tempo real. 
A arquitetura do sistema utiliza sockets para estabelecer uma comunicação lógica entre os processos dos clientes e o servidor.


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
git clone https://github.com/rose-del/chatSocket-java.git
```
- **Navegue até o diretório do projeto:**
```bash
cd chatSocket
```
- **Compile o código fonte:**
```bash
javac src/Servidor.java  src/Cliente.java
```
- **Execute o Servidor:**
```bash
java src.Main
```
- **Execute o Cliente:**
```bah
java src.Cliente
```
