package com.thekarlbrown.websocketchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


public class SplashScreen extends Activity implements UsernameDialogue.UsernameSetListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //new WebSocketConnection().execute();
        onUsernameSetPositiveClick();
    }

    @Override
    public void onUsernameSetPositiveClick(String username) {
        Intent i = new Intent(SplashScreen.this,HomeScreen.class);
        i.putExtra("username",username);
        startActivity(i);
    }

    @Override
    public void onUsernameSetPositiveClick() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UsernameDialogue usernameDialogue = new UsernameDialogue();
        fragmentTransaction.add(usernameDialogue,"usernameDialogue");
        fragmentTransaction.commit();
    }
/**
    private class WebSocketConnection extends AsyncTask<Void, Void, Void> {
        HomeScreen homeScreen = new HomeScreen();

        @Override
        protected Void doInBackground(Void... arg0){
            homeScreen.initiateWebSocket();
            return null;
        }

        protected void onPostExecute(Void... params){
            Intent i = new Intent (SplashScreen.this,HomeScreen.class);
            startActivity(i);
            finish();
        }

    }
        */
}
