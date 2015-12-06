package com.sam.l2type;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareStats extends DialogFragment {
    String uname;
    UserFile file;
    DecimalFormat df;
    Double accuracy;
    Double cpm;
    private String
            url = "http://cssgate.insttech.washington.edu/~samvecht/uploadstats.php";


    public ShareStats() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        uname = getActivity().getIntent().getStringExtra("username");
        df = new DecimalFormat("#.##");
        file = UserFileHelper.getFile(getContext(), uname);
        AlertDialog.Builder builder = new
                AlertDialog.Builder(getActivity());
        builder.setMessage("Share Stats?")
                .setPositiveButton("Yes", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                View v = getView();
                                cpm = file.getCpm();
                                accuracy = file.getAccuracy();
                                url += "?Uname=" + uname
                                        + "&Cpm=" + df.format(cpm)
                                        + "&Accuracy=" + df.format(accuracy);
                                new AddUserWebTask().execute(url);

                            }
                        })
                .setNegativeButton("No", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
        return builder.create();
    }


    private class AddUserWebTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "AddUserWebTask";

        @Override
        protected String doInBackground(String... urls) {
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
            // Only display the first 5000 characters of the retrieved
            // web page content.
            int len = 5000;

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
            } catch (Exception e) {
                Log.d(TAG, "Something happened" + e.getMessage());
            } finally {
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
                JSONObject jsonObject = new JSONObject(s);
                String status = jsonObject.getString("result");
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getActivity(), "User successfully inserted",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    String reason = jsonObject.getString("error");
                    Toast.makeText(getActivity(), "Failed :" + reason,
                            Toast.LENGTH_SHORT)
                            .show();
                }

                getFragmentManager().popBackStackImmediate();
            } catch (Exception e) {
                Log.d(TAG, "Parsing JSON Exception " + e.getMessage());
            }
        }
    }
}
