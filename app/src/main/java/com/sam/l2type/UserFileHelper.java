package com.sam.l2type;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Sam on 12/4/2015.
 */
public class UserFileHelper {
    public static void createFile(Context c, String uname) {
        try {
            Log.e("L2Type", "CreateFile started");
            File path = new File(c.getFilesDir(), uname);
            FileOutputStream stream = c.openFileOutput(uname, c.MODE_PRIVATE);
            UserFile file = new UserFile(uname);
            stream.write(file.toString().getBytes());
            stream.close();
            Log.e("L2Type", "CreateFile finished");

        } catch (FileNotFoundException e) {
            Log.e("L2Type", "CreateFile Can't find file: " + uname + ". " + e);
        } catch (IOException e) {
            Log.e("L2Type", "CreateFile IOException: " + uname + ". " + e);
        }
    }
    public static void saveFile(Context c, UserFile file) {
        try {
            Log.e("L2Type", "SaveFile started");
            FileOutputStream stream = c.openFileOutput(file.getUname(), c.MODE_PRIVATE);
            stream.write(file.toString().getBytes());
            stream.close();
            Log.e("L2Type", "SaveFile finished");

        } catch (IOException e) {
            Log.e("L2Type", "SaveFile IOException: " + file + ", " + e);
        }
    }
    public static UserFile getFile(Context c, String uname) {
        Log.e("L2Type", "GetFile started");

        String fileAsString = "";

        try {
            InputStream inputStream = c.openFileInput(uname);
            int iterator;
            while((iterator = inputStream.read()) != -1) {
                fileAsString = fileAsString + Character.toString((char) iterator);
            }
            inputStream.close();
            Log.e("L2Type", "GetFile finished");
        } catch(FileNotFoundException e) {
            Log.e("L2Type", "GetFile Can't find file: " + uname + ", " + e);
        } catch(IOException e) {
            Log.e("L2Type", "GetFile IO exception: " + uname + ", " + e);
        }
        return new UserFile(fileAsString);
    }
}
