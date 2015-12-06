package com.sam.l2type;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {
    private String uname;
    private UserFile file;
    private RadioGroup rgAutocorrect;
    private RadioGroup rgCpm;
    private RadioGroup rgCaps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        uname = getIntent().getStringExtra("username");
        file = UserFileHelper.getFile(this, uname);
        rgAutocorrect = (RadioGroup) findViewById(R.id.rg_autocorrect);
        rgCpm = (RadioGroup) findViewById(R.id.rg_cpm);
        rgCaps = (RadioGroup) findViewById(R.id.rg_caps);

        if(file.getAutocorrect() == 0) {
            rgAutocorrect.check(R.id.autocorrect_no);
        } else {
            rgAutocorrect.check(R.id.autocorrect_yes);
        }

        if(file.getSpeed() == 0) {
            rgCpm.check(R.id.cpm_no);
        } else {
            rgCpm.check(R.id.cpm_yes);
        }

        if(file.getCaps() == 0) {
            rgCaps.check(R.id.caps_yes);
        } else {
            rgCaps.check(R.id.caps_no);
        }
    }

    public void autocorrectNo(View v) {
        file.setAutocorrect(0);
        saveSettings();
    }
    public void autocorrectYes(View v) {
        file.setAutocorrect(1);
        saveSettings();
    }
    public void speedWpm(View v) {
        file.setSpeed(0);
        saveSettings();
    }
    public void speedCpm(View v) {
        file.setSpeed(1);
        saveSettings();
    }
    public void capsYes(View v) {
        file.setCaps(0);
        saveSettings();
    }
    public void capsNo(View v) {
        file.setCaps(1);
        saveSettings();
    }
    public void saveSettings() {
        UserFileHelper.saveFile(this, file);
    }
    public void goBack(View v) {
        Intent main = new Intent(Settings.this, MainActivity.class);
        main.putExtra("username", uname);
        startActivity(main);

    }
}
