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

    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uname = getIntent().getStringExtra("username");
        TextView userNameDisplay = (TextView) this.findViewById(R.id.user_name_display);
        userNameDisplay.setText("Welcome: " + uname);
        LevelPackGetter getter = new LevelPackGetter(this);
        getter.downloadLevels();
    }

    /**
     * Will eventually bring the user to the settings Activity
     * @param v
     */
    public void goToSettings(View v) {
        if(uname.equals(LoginPage.GUEST_NAME)) {
            Toast.makeText(this, "Settings not available to guests", Toast.LENGTH_SHORT).show();
        } else {
            Intent settings = new Intent(MainActivity.this, Settings.class);
            settings.putExtra("username", uname);
            startActivity(settings);

        }
    }

    /**
     * Brings the user to the LevelSelectScreen activity
     * @param v
     */
    public void goToPlay(View v) {
        Intent levelSelectScreen = new Intent(MainActivity.this, LevelSelectScreen.class);
        levelSelectScreen.putExtra("username", uname);
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
        if(uname.equals(LoginPage.GUEST_NAME)) {
            Toast.makeText(this, "Stats not available for guests", Toast.LENGTH_SHORT).show();
        } else {
            Intent statsActivity = new Intent(MainActivity.this, StatsActivity.class);
            statsActivity.putExtra("username", uname);
            startActivity(statsActivity);
        }
    }

    /**
     * Will eventually allow for the user to share their stats
     * @param v
     */
    public void shareStats(View v) {
        if(uname.equals(LoginPage.GUEST_NAME)) {
            Toast.makeText(this, "Stat sharing not available for guests", Toast.LENGTH_SHORT).show();
        } else {
            ShareStats shareStats = new ShareStats();
            shareStats.show(getSupportFragmentManager(), "shareStats");
        }
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
