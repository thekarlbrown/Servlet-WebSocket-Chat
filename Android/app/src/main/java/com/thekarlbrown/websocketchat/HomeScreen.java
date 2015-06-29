package com.thekarlbrown.websocketchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;


public class HomeScreen extends Activity {
    private WebSocketClient webSocketClient;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Intent intent = getIntent();
        username=intent.getStringExtra("username");
        ((TextView)findViewById(R.id.welcome_text)).setText(getString(R.string.welcome_message) + " " + username);
        initiateWebSocket();
        if(webSocketClient.getConnection().isOpen()){
            ((TextView)findViewById(R.id.welcome_text)).setText("We are connected");
        }
    }

    /**
     * Connect to the server and instruct the WebSocket on our chat protocol
     */
    public void initiateWebSocket(){
        URI uri;
        try { uri = new URI("ws://98.169.82.159:8080/chat");}
        catch (URISyntaxException e){
            e.printStackTrace();
            return;
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                webSocketClient.send("newUser"+username);
            }

            @Override
            public void onMessage(String message) {
                if(message.substring(0,11).equals("---Users---")){
                    updateUserList(message.substring(0,11).split("<br>",0));
                }else{
                    updateChatWindow(message);
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        webSocketClient.connect();
        try{
            while(webSocketClient.getConnection().isConnecting()){
                wait();
                Log.d(null,"We aren't connected yet");
            }
        }catch (Exception e){}
    }

    /**
     * Update the chat
     * @param message Chat message
     */
    private void updateChatWindow(String message){

    }
    private void updateUserList(String users[]){

    }


}
