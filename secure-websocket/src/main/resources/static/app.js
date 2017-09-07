var stompClient = null;
var name = null;

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
        
        name = $("#name").val();
        
        stompClient.subscribe('/topic/greetings', function (greeting) {
        	var response = JSON.parse(greeting.body);
            showGreeting(response);
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

function sendName() {
	var message = {
		name: name,
		message: $("#message").val()
	}
	
	stompClient.send("/app/hello", {}, JSON.stringify(message));
	
	stompClient.send("/user/snuk/queue/private", {}, "Hi there!");
}

function showGreeting(message) {
    $("#conversation").append("[" + message.time + "] &lt;" + message.user + "&gt; " + message.content + "<br />");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});