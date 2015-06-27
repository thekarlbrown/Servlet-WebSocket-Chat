/**
 * Created by TheKarlBrown on 6/25/15.
 */
var connection;
function onEntry(){
    connection = new WebSocket("ws://localhost:8080/chat");
    connection.onopen = function (e){
        connection.send("newUser"+document.getElementById("loginName").innerHTML)
    }
    connection.onmessage = function(e){
        if (e.data.substring(0,11).localeCompare("---Users---")==0){
            document.getElementById("userList").innerHTML = e.data;
        }else {
            document.getElementById("chatBox").innerHTML = document.getElementById("chatBox").innerHTML + e.data + "<br>";
        }
    }
}
function onSendMessage(){
    connection.send(document.getElementById("loginName").innerHTML + ": " + document.getElementById("usermsg").value);
    document.getElementById("usermsg").value="";
}
