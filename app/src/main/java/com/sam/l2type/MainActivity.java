package com.sam.l2type;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public String url = "http://cssgate.insttech.washington.edu/~samvecht/levelpacks.php";

    LevelsDB levelDB = new LevelsDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToSettings(View v) {
        Toast.makeText(this, "Settings not yet implemented", Toast.LENGTH_SHORT).show();
    }

    public void goToPlay(View v) {
        Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();

    }

    public void getNewLevels(View v) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new UserWebTask().execute(url);
        } else {
            Toast.makeText(this
                    , "No network connection available.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void goToStats(View v) {
        Toast.makeText(this, "Stat tracking not yet implemented", Toast.LENGTH_SHORT).show();

    }

    public void shareStats(View v) {
        Toast.makeText(this, "Stat sharing not yet implemented", Toast.LENGTH_SHORT).show();

    }

    public void logout(View v) {
        Intent logInScreen = new Intent(MainActivity.this, LoginPage.class);
        startActivity(logInScreen);

    }
    private class UserWebTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "UserWebTask";

        @Override
        protected String doInBackground(String...urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.d(TAG, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                Log.d(TAG, "The string is: " + contentAsString);
                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } catch(Exception e ) {
                Log.d(TAG, "Something happened" + e.getMessage());
            }
            finally {
                if (is != null) {
                    is.close();
                }
            }
            return null;
        }

        // Reads an InputStream and converts it to a String.
        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // Parse JSON
            try {
                SQLiteDatabase db = levelDB.getWritableDatabase();
                int currentPacks = (int) DatabaseUtils.queryNumEntries(db, "LevelPacks");

                JSONArray jsonarray = new JSONArray(s);
                int onlinePacks = jsonarray.length();
                if(onlinePacks > currentPacks) {
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonarray.get(i);
                        int id = jsonObject.getInt("id");

                        String name = (String) jsonObject.get("name");
                        int numLevels = jsonObject.getInt("levels");
                        ContentValues values = new ContentValues();
                        values.put("id", id);
                        values.put("name", name);
                        values.put("numLevels", numLevels);

                        String[] levelsArray = new String[10];
                        for(int j = 1; j < 10; j++) {
                            levelsArray[j] = (String) jsonObject.get(("level" + (j + 1)));
                            values.put(("level" + j), levelsArray[j]);
                        }
                        db.insert("LevelPacks", null, values);

                    }
                    Toast.makeText(getApplicationContext(), "New packs downloaded: " + (onlinePacks - currentPacks),
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "No new packs to download", Toast.LENGTH_SHORT).show();

                }
            }
            catch(Exception e) {
                Log.d(TAG, "Parsing JSON Exception " + e.getMessage());
            }
        }
    }
}
