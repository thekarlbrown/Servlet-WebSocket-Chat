/**
 * Created by TheKarlBrown on 6/25/15.
 */
var connection;
function onEntry(){
    connection = new WebSocket("ws://localhost:8080/chat");
    connection.onmessage = function(e){
        document.getElementById("chatbox").value=document.getElementById("chatbox").value + e.data + "<br>";
    }
}
function onSendMessage(){
    connection.send(document.getElementById("usermsg").value);
}
