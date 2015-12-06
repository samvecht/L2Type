package com.sam.l2type;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class StatsActivity extends AppCompatActivity {
    String uname;
    DecimalFormat df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        df = new DecimalFormat("#.##");
        uname = getIntent().getStringExtra("username");
        setContentView(R.layout.activity_stats);

        UserFile file = UserFileHelper.getFile(this, uname);

        TextView welcome = (TextView) findViewById(R.id.stats_welcome_text);
        TextView cpmView = (TextView) findViewById(R.id.stats_cpm_text);
        TextView accuracyView = (TextView) findViewById(R.id.stats_accuracy_text);

        welcome.setText("Here are your numbers, " + uname + "!");
        if (file.getSpeed() == 0) {
            cpmView.setText("CPM: " + df.format(file.getCpm()));
        } else {
            cpmView.setText("WPM: " + df.format(file.getCpm() / 5));
        }
        accuracyView.setText("Accuracy: " + df.format(file.getAccuracy()));
    }


    public void goBack(View v) {
        Intent main = new Intent(StatsActivity.this, MainActivity.class);
        main.putExtra("username", uname);
        startActivity(main);

    }
}
