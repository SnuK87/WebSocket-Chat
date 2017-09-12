var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    $("#name").prop("disabled", connected);
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        
        stompClient.subscribe('/topic/messages', function (greeting) {
        	var response = JSON.parse(greeting.body);
            showGreeting(response);
        });
        
        stompClient.subscribe('/app/chat/users', function (greeting) {
        	console.log(greeting.body);
        });
        
        stompClient.subscribe('/topic/chat/login', function (greeting) {
        	console.log(greeting.body, 'joined');
        });
        
        stompClient.subscribe('/topic/chat/logout', function (greeting) {
        	console.log(greeting.body, 'left');
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
	var message = {
		message: $("#message").val()
	}
	
	stompClient.send("/app/chat", {}, JSON.stringify(message));
}

function showGreeting(message) {
    $("#conversation").append("[" + message.time + "] &lt;" + message.user + "&gt; " + message.content + "<br />");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendMessage(); });
});

$(document).ready(function() {
	connect();
});