package com.sam.l2type;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_page);
        EditText uname = (EditText) findViewById(R.id.uname_box);
        EditText pass = (EditText) findViewById(R.id.pass_box);
        uname.setText(getIntent().getStringExtra("uname"));
        pass.setText(getIntent().getStringExtra("pass"));
    }

    /**
     * Creates a new local user account from the data provided in the loginpages textviews
     * Displays a toast if account create failed/succeeded
     * @param v
     */
    public void createAccount(View v) {
        final String uname = ((EditText) findViewById(R.id.uname_box)).getText().toString();
        final String pass = ((EditText) findViewById(R.id.pass_box)).getText().toString();
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.loginFile), Context.MODE_PRIVATE);
        if(checkAccountNameAvailable(uname)
                && !pass.equals("")) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(uname, pass);
            editor.commit();

            Toast.makeText(this, "Account Created: " + uname, Toast.LENGTH_SHORT).show();
            goToLogin(uname);
        } else {
            Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
        }
    }


    public void goBack(View v) {
        goToLogin("");
    }
    /**
     * Starts the LoginActivity, passing it the username of the newly created account
     * * @param uname the username of the new account
     */
    private void goToLogin(String uname) {
        Intent mainActivity = new Intent(CreateAccountPage.this, LoginPage.class);
        mainActivity.putExtra("username", uname);
        startActivity(mainActivity);

    }

    /**
     * Checks if an account name is valid
     * An account name can't be the same as a user already in the system
     * and can't be "guest" or empty
     * @param uname the user name being checked
     * @return
     */
    private boolean checkAccountNameAvailable(String uname) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        String check = sharedPref.getString(uname, defaultValue);
        return check.equals(defaultValue) && !uname.equals(LoginPage.GUEST_NAME) && !uname.equals("");
    }
}
