var stompClient = null;
var users = [];
var selectedUser = null;

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
        
        stompClient.subscribe('/topic/messages', function (message) {
        	var response = JSON.parse(message.body);
        	renderMessage(response, false);
        });
        
        stompClient.subscribe('/user/queue/private', function (message) {
        	var response = JSON.parse(message.body);
        	renderMessage(response, true);
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
	
	if(selectedUser === null){
		stompClient.send("/app/chat", {}, JSON.stringify(message));
	}
	else{
		stompClient.send("/app/chat/private/" + users[selectedUser], {}, JSON.stringify(message));
	}
	
	$("#message").val('');
}

function renderMessage(message, private) {
	if(private){
		$("#conversation").append("<span class=\"privateMessage\">[" + message.time + "] &lt;" + message.user + "&gt; " + message.content + "</span><br />");
	}
	else{
		$("#conversation").append("<span>[" + message.time + "] &lt;" + message.user + "&gt; " + message.content + "</span><br />");
	}
}

function renderUserlist() {
	
	$("#users").html("");
	
	for(var i = 0; i < users.length; i++){
		$("#users").append("<span id=\"user_" + i + "\" onclick=\"clickUser(" + i + ")\">" + users[i] + "</span><br />");
	}
}

function clickUser(index){
	console.log('click user', index);
	
	if(index === selectedUser){
		selectedUser = null;
		$("#user_" + index).removeClass('selectedUser');
		
	}
	else{
		selectedUser = index;
		$("#user_" + index).addClass('selectedUser');
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