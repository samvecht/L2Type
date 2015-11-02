package com.sam.l2type;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


    }

    private void goToMain(String uname) {
        Intent mainActivity = new Intent(LoginPage.this, MainActivity.class);
        startActivity(mainActivity);

    }
    private boolean checkAccountNameAvailable(String uname) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        String check = sharedPref.getString(uname, defaultValue);
        return check.equals(defaultValue);
    }

    public void createAccount(View v) {
        final String uname = ((EditText) findViewById(R.id.uname_box)).getText().toString();
        final String pass = ((EditText) findViewById(R.id.pass_box)).getText().toString();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        if(checkAccountNameAvailable(uname) && !uname.equals("") && !pass.equals("") && !uname.equals("guest")) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(uname, pass);
            editor.commit();

            Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();
            goToMain(uname);
        } else {
            Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show();

        }
    }
    public void logIn(View v) {
        final EditText uname = (EditText) findViewById(R.id.uname_box);
        final EditText pass = (EditText) findViewById(R.id.pass_box);

        if (checkPassword(uname.getText().toString(), pass.getText().toString())) {
            goToMain(uname.getText().toString());
        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();

            uname.setText("");
            pass.setText("");

        }
    }
    public void logInAsGuest(View v) {
        goToMain("guest");
    }
    private boolean checkPassword(String uname, String pass) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        String password = sharedPref.getString(uname, defaultValue);
        return password.equals(pass);
    }
}
