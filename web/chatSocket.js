/**
 * Created by TheKarlBrown on 6/25/15.
 */
var connection;
function onEntry(){
    connection = new WebSocket("ws://localhost:2426/Servlets/ChatServlet");
    connection.onmessage = function(e){
        document.getElementById("chatbox").value=document.getElementById("chatbox").value + e.data + "<br>";
    }
}
function onSendMessage(){
    var myRequest = new XMLHttpRequest();
    var postRequest = "message="+document.getElementById("usermsg").value;
    myRequest.open("POST","http://localhost:8080/Servlets/ChatServlet");
    myRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    myRequest.send(postRequest);
}
