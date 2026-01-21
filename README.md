# 💬 ChatsZap - Projeto em Java

![Status](https://img.shields.io/badge/status-estável-brightgreen)
![Licença](https://img.shields.io/github/license/rose-del/chatSocket)

O ChatsZap é um sistema de chat desenvolvido em Java, utilizando sockets para comunicação e Java Swing para a interface gráfica. 
Ele demonstra a comunicação bidirecional entre um servidor e múltiplos clientes,
utilizando threads para o gerenciamento simultâneo de conexões.

---

## 📑 Sumário

1. [Descrição do Projeto](#-descrição-do-projeto)
2. [Tecnologias ultilizadas](#️-tecnologias-utilizadas)
3. [Estrutura do Projeto](#-estrutura-do-projeto)
    - [Cliente](#-cliente)
    - [Servidor](#️-servidor)
4. [Como Executar](#️-como-executar)
5. [Iterface gráfica com Java Swing](#️-interface-gráfica-com-java-swing)

## 💡 Descrição do Projeto

O ChatsZap permite que vários clientes se conectem a um servidor central, 
possibilitando o envio e recebimento de mensagens em tempo real. 
A arquitetura do sistema utiliza sockets para estabelecer uma comunicação lógica entre os processos dos clientes e o servidor.

---

## 💻⚙️ Tecnologias Utilizadas

- ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white)
- ![Sockets](https://img.shields.io/badge/Java%20Sockets-007396?style=for-the-badge&logo=java&logoColor=white)
- ![Java Multithreading](https://img.shields.io/badge/Java%20Multithreading-ED8B00?style=for-the-badge&logo=java&logoColor=white)

- ![IntelliJ IDEA](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)
![VSCode](https://img.shields.io/badge/IDE-VSCode-007ACC?style=for-the-badge&logo=visual-studio-code&logoColor=white)

---

## 📂 Estrutura do Projeto

### 🧑‍💻 cliente

- `Cliente.java`: Cria as instâncias das classes ClienteGUI, ClienteConexao e ClienteControlador.
- `ClienteConexao.java`: Responsável pela conexão com o servidor de chat.
- `ClienteControlador.java`: Responsável por controlar a interação entre a interface gráfica e a conexão com o servidor.
- `ClienteGUI.java`: Responsável pela interface gráfica do cliente de chat.

### 🗄️ servidor

- `Main.java`: Inicia o processo de configuração e inicialização do servidor.
- `ClienteHandler.java`: Responsável por gerenciar a comunicação entre o servidor e um cliente especifico.
- `ConfiguradorServidor.java`: Responsável por obter a porta do servidor por meio de uma interface gráfica simples.
- `Servidor.java`: Responsável por gerenciar as conexões e comunicações entre clientes em um servidor.

---

## ▶️ Como Executar

### Pré-requisitos

- JDK instalado (versão 11 ou superior).
- IDE de sua preferência (recomendado: IntelliJ IDEA ou VScode).

### Passos para execução

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

    ```bash
    java src.Cliente
    ```

## 🖼️ Interface gráfica com Java Swing

![Image](https://github.com/user-attachments/assets/d725486e-52cf-4933-a6bf-6403880b3dba)
