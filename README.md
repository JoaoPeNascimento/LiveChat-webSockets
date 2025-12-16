# 💬 LiveChat - Spring Boot WebSockets

Um sistema de chat em tempo real desenvolvido com **Java (Spring Boot)** e **WebSockets (STOMP)**, demonstrando comunicação full-duplex, roteamento de mensagens privadas e gerenciamento de sessões.

## 🚀 Funcionalidades

* **Chat Público (Broadcast):** Mensagens enviadas para todos os usuários conectados em tempo real.
* **Mensagens Privadas (1-para-1):** Envio de mensagens diretas para um usuário específico utilizando filas de usuário (`/user/queue`).
* **Notificações de Presença:** Monitoramento de eventos de conexão e desconexão para avisar quando usuários entram ou saem da sala.
* **Identificação de Sessão:** Implementação de um *Handshake Handler* customizado para atribuir identidades (`Principal`) aos usuários WebSocket sem a necessidade de um banco de dados ou Spring Security complexo.

## 🛠️ Tecnologias Utilizadas

**Backend:**
* Java 21
* Spring Boot 3+
* Spring WebSocket (STOMP protocol)
* Maven

**Frontend:**
* HTML5 & CSS3
* JavaScript (ES6)
* jQuery
* Stomp.js (Cliente STOMP)
* Bootstrap 3 (Estilização)

## ⚙️ Como Executar

### Pré-requisitos
* Java JDK 21 instalado.
* Maven (ou use o wrapper incluído).

### Passos
1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/JoaoPeNascimento/LiveChat-webSockets](https://github.com/JoaoPeNascimento/LiveChat-webSockets)
    cd livechat-websockets
    ```

2.  **Execute a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```

3.  **Acesse no navegador:**
    Abra `http://localhost:8080` em seu navegador.

4.  **Teste o Chat:**
    * Abra duas abas diferentes (ou uma aba anônima).
    * Conecte com nomes de usuários diferentes (ex: "Joao" e "Maria").
    * Troque mensagens no chat público.
    * Use a seção "Mensagem Privada" para enviar algo de "Joao" para "Maria".
    * Feche uma aba para ver a notificação de saída na outra.

## 📂 Estrutura do Projeto

```text
src/
├── main/
│   ├── java/com/joaopenascimento/livechat/
│   │   ├── config/          # Configuração do WebSocket e Handshake Handler
│   │   ├── controller/      # Endpoints de mensagens (Público e Privado)
│   │   ├── domain/          # Records (DTOs) de Input/Output
│   │   ├── listener/        # Ouvinte de eventos de conexão/desconexão
│   │   └── LivechatApplication.java
│   └── resources/
│       └── static/          # Frontend (HTML, CSS, JS)