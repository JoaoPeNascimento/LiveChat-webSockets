const stompClient = new StompJs.Client({
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);

    // Tópico Público
    stompClient.subscribe('/topics/livechat', (message) => {
        updateLiveChat(JSON.parse(message.body).content);
    });

    // Tópico de Status (da etapa anterior)
    stompClient.subscribe('/topics/status', (message) => updateStatus(message.body));

    // NOVA INSCRIÇÃO: Fila Privada do Usuário
    // O destino "/user/queue/private" é mapeado automaticamente pelo Spring
    // para a fila exclusiva deste usuário logado
    stompClient.subscribe('/user/queue/private', (message) => {
        updateLiveChatPrivate(JSON.parse(message.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
}

function connect() {
    const username = $("#user").val();
    if(!username) { alert("Digite um usuário!"); return; }

    // Configuramos a URL dinamicamente para incluir o query param user=...
    // Isso permite que o UserHandshakeHandler no backend identifique o Principal
    stompClient.brokerURL = 'ws://' + window.location.host + '/livechat?user=' + encodeURIComponent(username);

    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.publish({
        destination: "/app/new-message",
        body: JSON.stringify({'user': $("#user").val(), 'message': $("#message").val()})
    });
    $("#message").val("");
}

function sendPrivateMessage() {
    const recipient = $("#private-recipient").val();
    const message = $("#private-message").val();

    if(!recipient || !message) return;

    stompClient.publish({
        destination: "/app/private-message",
        body: JSON.stringify({'recipient': recipient, 'message': message})
    });
    $("#private-message").val("");
}

function updateLiveChatPrivate(message) {
    // Adiciona classe CSS diferente para destacar mensagens privadas
    $("#livechat").append("<tr><td class='private-message'>" + message + "</td></tr>");
}

function updateStatus(message) {
    $("#livechat").append("<tr><td class='status-message'>" + message + "</td></tr>");
}

function updateLiveChat(message) {
    $("#livechat").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendMessage());
    $( "#send-private" ).click(() => sendPrivateMessage()); // Novo botão
});