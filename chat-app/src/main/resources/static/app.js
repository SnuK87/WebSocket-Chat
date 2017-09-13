var stompClient = null;
var users = [];

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
        	renderMessage(response);
        });
        
        stompClient.subscribe('/app/chat/users', function (userList) {
        	users = JSON.parse(userList.body);
        	renderUserlist();
        });
        
        stompClient.subscribe('/topic/chat/login', function (user) {
        	users.unshift(user.body);
        	renderUserlist();
        });
        
        stompClient.subscribe('/topic/chat/logout', function (user) {
        	for(var i = 0; i < users.length; i++){
        		if(users[i] == user.body){
        			users.splice(i, 1);
        			break;
        		}
        	}
        	
        	renderUserlist();
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

function renderMessage(message) {
    $("#conversation").append("[" + message.time + "] &lt;" + message.user + "&gt; " + message.content + "<br />");
}

function renderUserlist() {
	
	$("#users").html("");
	
	for(var i = 0; i < users.length; i++){
		$("#users").append(users[i] + "<br />");
	}
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