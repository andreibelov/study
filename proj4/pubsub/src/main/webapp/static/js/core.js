$(document).ready(function() {

    openSocket();

    var webSocket;
    var chat = document.getElementById("chat");
    var list = chat.getElementsByTagName("dl")[0];
    var sendButton = document.getElementById("send");
    var openButton = document.getElementById("open");
    var closeButton = document.getElementById("close");

    sendButton.addEventListener("click",send);
    openButton.addEventListener("click",openSocket);
    closeButton.addEventListener("click",closeSocket);

    function openSocket(){
        // Ensures only one connection is open at a time
        if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
            writeResponse("server", "WebSocket is already opened.");
            return;
        }
        // Create a new instance of the websocket
        webSocket = new WebSocket("ws://localhost:8080/pubsub/chat");

        /**
         * Binds functions to the listeners for the websocket.
         */
        webSocket.onopen = function(event){
            // For reasons I can't determine, onopen gets called twice
            // and the first time event.data is undefined.
            // Leave a comment if you know the answer.
            if(event.data === undefined) {
                return;
            }
            writeResponse("Welcome!", event.data);
        };

        webSocket.onmessage = function(event){
            var msg = JSON.parse(event.data);
            writeResponse(msg.sender, msg.text);
        };

        webSocket.onclose = function(event){
            writeResponse("server", "Connection closed");
            setTimeout(function(){openSocket();}, 500);
        };
    }

    /**
     * Sends the value of the text input to the server
     */
    function send(){
        var text = document.getElementById("msg").value;
        webSocket.send(text);
    }

    function closeSocket(){
        webSocket.close();
    }

    function writeResponse(user, text){
        list.innerHTML += "<dt>" +user+ "</dt>" +
            "<dd >" + text + "</dd>";
        var d = $('#chat');
        d.animate({scrollTop: d.prop("scrollHeight")}, 250);
    }

});