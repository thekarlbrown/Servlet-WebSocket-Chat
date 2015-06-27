/**
 * Javascript handling the WebSocket client code and chat/userlist display
 * Created by Karl Brown ( TheKarlBrown ) on 6/25/15.
 */
var connection;
function onEntry(){
    // Create a new connection
    connection = new WebSocket("ws://localhost:8080/chat");

    // set onopen WebSocket action to send the login name to the Server
    connection.onopen = function (e){ connection.send("newUser"+document.getElementById("loginName").innerHTML) }

    // Determine the type of Server message and alter the display accordingly
    connection.onmessage = function(e){
        if (e.data.substring(0,11).localeCompare("---Users---")==0){
            document.getElementById("userList").innerHTML = e.data;
        }else {
            document.getElementById("chatBox").innerHTML = document.getElementById("chatBox").innerHTML + e.data + "<br>";
            overflowFix();
        }
    }
}

// Send a WebSocket message to the server upon form entry and clear the form
function onSendMessage(){
    connection.send(document.getElementById("loginName").innerHTML + ": " + document.getElementById("usermsg").value);
    document.getElementById("usermsg").value="";
}

// Automatically indent the Chat Window
function overflowFix(){
    var element = document.getElementById("chatBox");
    element.scrollTop = element.scrollHeight;
}
