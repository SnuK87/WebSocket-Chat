var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        
        stompClient.subscribe('/topic/greetings', function (greeting) {
        	console.log('message from subscribed topic:', greeting.body);
        });
        
        stompClient.subscribe('/user/queue/private', function (greeting) {
        	console.log('got private message', greeting.body);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
}

function sendMessage() {
	stompClient.send("/app/hello", {}, 'message from the client');
//	stompClient.send("/user/snuk/queue/private", {}, "Hi there!");
}


$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});