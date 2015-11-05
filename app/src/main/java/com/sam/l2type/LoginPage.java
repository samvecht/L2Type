package com.sam.l2type;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The initial Activity that the app opens to.
 * The user may select to login, create a new account, or enter the app as a guest
 */
public class LoginPage extends AppCompatActivity {

    //Reserved name, for use by guest login
    public static final String GUEST_NAME = "guest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
    }

    /**
     * Starts the MainActivity, passing it the username of whomever is logging in
     * @param uname the username of the user, or 'guest' if they are entering as a guest
     */
    private void goToMain(String uname) {
        Intent mainActivity = new Intent(LoginPage.this, MainActivity.class);
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
        return check.equals(defaultValue) && !uname.equals(GUEST_NAME) && !uname.equals("");
    }

    /**
     * Creates a new local user account from the data provided in the loginpages textviews
     * Displays a toast if account create failed/succeeded
     * @param v
     */
    public void createAccount(View v) {
        final String uname = ((EditText) findViewById(R.id.uname_box)).getText().toString();
        final String pass = ((EditText) findViewById(R.id.pass_box)).getText().toString();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        if(checkAccountNameAvailable(uname)
                && !pass.equals("")
                && pass.length() == 4) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(uname, pass);
            editor.commit();

            Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();
            goToMain(uname);
        } else {
            Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Logs in a user using the information provided in the LoginPages textviews
     * Displays a toast if login fails
     * @param v
     */
    public void logIn(View v) {
        final EditText uname = (EditText) findViewById(R.id.uname_box);
        final EditText pass = (EditText) findViewById(R.id.pass_box);

        if (checkPassword(uname.getText().toString(), pass.getText().toString())
                && !pass.getText().toString().equals("")) {
            goToMain(uname.getText().toString());
        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();

            pass.setText("");

        }
    }

    /**
     * Allows the user to login as a guest
     * @param v
     */
    public void logInAsGuest(View v) {
        goToMain(GUEST_NAME);
    }

    /**
     * Checks if the user entered the correct password for their username
     * @param uname
     * @param pass
     * @return
     */
    private boolean checkPassword(String uname, String pass) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        String password = sharedPref.getString(uname, defaultValue);
        return password.equals(pass);
    }
}
