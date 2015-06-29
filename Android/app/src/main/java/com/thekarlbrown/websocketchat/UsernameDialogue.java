package com.thekarlbrown.websocketchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/*
 * Created by TheKarlBrown on 6/29/15.
 */
public class UsernameDialogue extends DialogFragment {

    UsernameSetListener usernameSetListener;
    public interface UsernameSetListener {
        void onUsernameSetPositiveClick(String username);
        void onUsernameSetPositiveClick();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{ usernameSetListener= (UsernameSetListener) activity; }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement UsernameSetListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Activity currentActivity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        final View view = currentActivity.getLayoutInflater().inflate(R.layout.username_dialogue,null);
        return builder.setView(view).setPositiveButton(R.string.username_dialog_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String usernameEntry = ((EditText)view.findViewById(R.id.username_dialog_entry)).getText().toString();
                int entryLength = usernameEntry.length();
                if(entryLength<18&&entryLength>0){  usernameSetListener.onUsernameSetPositiveClick(usernameEntry); }
                else{ usernameSetListener.onUsernameSetPositiveClick(); }
            }
        }).setNegativeButton(R.string.username_dialog_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentActivity.finish();
                System.exit(0);
            }
        }).setCancelable(false).create();

    }
}
