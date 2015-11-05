package com.sam.l2type;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * The main menu page of the app
 */
public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView userNameDisplay = (TextView) this.findViewById(R.id.user_name_display);
        userNameDisplay.setText("Welcome: " + getIntent().getStringExtra("username"));

    }

    /**
     * Will eventually bring the user to the settings Activity
     * @param v
     */
    public void goToSettings(View v) {
        Toast.makeText(this, "Settings not yet implemented", Toast.LENGTH_SHORT).show();
    }

    /**
     * Brings the user to the LevelSelectScreen activity
     * @param v
     */
    public void goToPlay(View v) {
        Intent levelSelectScreen = new Intent(MainActivity.this, LevelSelectScreen.class);
        startActivity(levelSelectScreen);
    }

    /**
     * Downloads any new levels to the local level database
     * @param v
     */
    public void getNewLevels(View v) {
        LevelPackGetter getter = new LevelPackGetter(this);
        getter.downloadLevels();
    }

    /**
     * Will eventually bring the user to the stats activity
     * @param v
     */
    public void goToStats(View v) {
        Toast.makeText(this, "Stat tracking not yet implemented", Toast.LENGTH_SHORT).show();

    }

    /**
     * Will eventually allow for the user to share their stats
     * @param v
     */
    public void shareStats(View v) {
        Toast.makeText(this, "Stat sharing not yet implemented", Toast.LENGTH_SHORT).show();

    }

    /**
     * Logs out the user and returns them to the login page
     * @param v
     */
    public void logout(View v) {
        Intent logInScreen = new Intent(MainActivity.this, LoginPage.class);
        startActivity(logInScreen);

    }

}
