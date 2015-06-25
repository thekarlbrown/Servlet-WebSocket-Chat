/**
 * Created by TheKarlBrown on 6/24/15.
 */
function enterChatroom(){
    var currentText= document.getElementById("nameField").value;
    if (currentText.length>0 && currentText.length<30 ){
        document.getElementById("loginForm").style.display = "none";
        document.getElementById("loginName").innerHTML= currentText;
        document.getElementById("backdrop").style.display = "block";
        onEntry();
    }else{
        document.getElementById("nameField").value = "Type a name with 30 characters or less that is not null";
        document.getElementById("nameField").select();
    }
}