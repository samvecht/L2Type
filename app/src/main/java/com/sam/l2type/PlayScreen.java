package com.sam.l2type;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class PlayScreen extends AppCompatActivity {
    LevelPack levels;
    String uname;
    TextView levelDisplay;
    Button continueButton;
    int currentLevel;
    String totalInput;
    DecimalFormat df;
    String expectedTotalInput;
    long startTime;
    long endTime;
    UserFile file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        uname = getIntent().getStringExtra("username");
        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        super.onCreate(savedInstanceState);
        file = UserFileHelper.getFile(this, uname);
        currentLevel = -1;
        totalInput = "";
        expectedTotalInput = "";
        continueButton = (Button) findViewById(R.id.continue_button);
        levelDisplay = (TextView) findViewById(R.id.level_display);
        setContentView(R.layout.activity_play_screen);
        String levelPack = getIntent().getStringExtra("levelPack");
        LevelsDB dbHelper = new LevelsDB(this);
        List<LevelPack> allLevels = dbHelper.getAllLevelPacks();
        int i = 0;
        while(!levelPack.equals(allLevels.get(i).getName())) {
            i++;
        }
        levels = allLevels.get(i);
        TextView levelPackName = (TextView) findViewById(R.id.level_pack_name);
        levelPackName.setText(levelPack);
    }
    public void nextLevel(View v) {
        EditText playInput = (EditText) findViewById(R.id.play_input);

        if(file.getAutocorrect() == 0) {
            playInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        continueButton = (Button) findViewById(R.id.continue_button);
        levelDisplay = (TextView) findViewById(R.id.level_display);


        if(currentLevel == -1) {
            continueButton.setText("Next");
            playInput.setVisibility(View.VISIBLE);
            startTime = System.currentTimeMillis();
        }
        if(currentLevel < levels.getNumLevels() - 1) {
            String inputText = playInput.getText().toString();
            totalInput += inputText;
            expectedTotalInput += levelDisplay.getText().toString();
            playInput.setText("");
            playInput.requestFocusFromTouch();
            InputMethodManager lManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            lManager.showSoftInput(playInput, 0);
            currentLevel++;
            levelDisplay.setText(levels.getLevel(currentLevel));
        } else if(currentLevel == levels.getNumLevels() - 1){

            String inputText = playInput.getText().toString();
            totalInput += inputText;
            expectedTotalInput += levelDisplay.getText().toString();
            endTime = System.currentTimeMillis();
            double totalTime = (double)(endTime - startTime) / 1000;
            double cpm = 60 * totalInput.length()/totalTime;
            playInput.setVisibility(View.GONE);
            TextView scoreBoard = (TextView) findViewById(R.id.score_board);
            scoreBoard.setVisibility(View.VISIBLE);
            double accuracy = similarity(totalInput, expectedTotalInput) * 100;
            if(file.getSpeed() == 0) {
                scoreBoard.setText("WPM: " + df.format(cpm / 5) + "\n" +
                        "Accuracy: " + df.format(accuracy) + "%\n" +
                        "Total Time: " + df.format(totalTime) + "s");
            } else {
                scoreBoard.setText("CPM: " + df.format(cpm) + "\n" +
                        "Accuracy: " + df.format(accuracy) + "%\n" +
                        "Total Time: " + df.format(totalTime));
            }
            levelDisplay.setText("Good work!");
            continueButton.setText("Done");
            if(!uname.equals(LoginPage.GUEST_NAME)) {
                updateStats(cpm, accuracy);
            }
            currentLevel++;
        } else {
            returnToMain();
        }
    }

    private void updateStats(double cpm, double accuracy) {
        double oldCpm = file.getCpm();
        double oldAccuracy = file.getAccuracy();
        int oldLevels = file.getLevels();
        file.setCpm((oldCpm * oldLevels + cpm)/(oldLevels + 1));
        file.setAccuracy((oldAccuracy * oldLevels + accuracy) / (oldLevels + 1));
        file.setLevels(oldLevels + 1);
        UserFileHelper.saveFile(this, file);
    }

    private void returnToMain() {
        Intent main = new Intent(PlayScreen.this, MainActivity.class);
        main.putExtra("username", uname);
        startActivity(main);

    }

    /**
     * Calculates the similarity (a number within 0 and 1) between two strings.
     */
    public double similarity(String s1, String s2) {
        if(file.getCaps() == 1) {
            s1 = s1.toLowerCase();
            s2 = s2.toLowerCase();
        }
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
    /* // If you have StringUtils, you can use it to calculate the edit distance:
    return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) /
                               (double) longerLength; */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }

    //implementation of the Levenshtein Edit Distance
    public static int editDistance(String s1, String s2) {
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
}
