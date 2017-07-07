var stompClient = null;
var name = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        
        name = $("#name").val();
        
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "http://localhost:8080/latest",
            dataType: 'json',
            success: function(result) {
            	for(i = 0; i < result.length; i++){
            		showGreeting(result[i]);
            	}
            }
        });
        
        stompClient.subscribe('/topic/greetings', function (greeting) {
        	var response = JSON.parse(greeting.body);
            showGreeting(response);
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
			name: $("#name").val(),
			message: "message"
	}
	
	stompClient.send("/app/hello", {}, JSON.stringify(message));
}

function showGreeting(message) {
	console.log(message.user);

    $("#greetings").append("<tr><td>[" + message.time + "] &lt;" + message.user + "&gt; " + message.content + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});