package by.fabric.kewbr.talking_crocodile.ViewModel;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameSettings {

    public int wordCount;
    public int timeOfRound;
    public boolean inAppSound;
    public boolean surcharge;

    GameSettings(){
        wordCount = 60;
        timeOfRound = 60;
        inAppSound = true;
        surcharge = false;
    }

    GameSettings(Context context){
        if(!loadFromJSON(context)) {
            wordCount = 60;
            timeOfRound = 60;
            inAppSound = true;
            surcharge = false;
        }
        else
            Log.i("Settings"," is loaded properly");
    }

    public GameSettings(int mWordCount, int mTimeOfRound, boolean soundEnable, boolean mSurcharge){
        wordCount = mWordCount;
        timeOfRound = mTimeOfRound;
        inAppSound = soundEnable;
        surcharge = mSurcharge;
    }

    public boolean loadFromJSON(Context context){
        //implementation////

        //File file = new File("roundSettings.json");
        //if(!file.exists())
        //    return  false;
        JSONObject obj = null;
        BufferedReader input = null;
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream in = context.openFileInput("roundSettings.json");
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line +"\n");
            }
            String result = sb.toString();
            Log.i("File contains",sb.toString());
            obj = new JSONObject(result);
        } catch (Exception e) {
            Log.i("Read File Error",e.getMessage());
        }
        if(obj != null) {
            try {
                wordCount = obj.getInt("wordCountForWin");
                timeOfRound = obj.getInt("timeOfRound");
                //And other, when we may need it
            } catch (JSONException e) {
                Log.i("JSONError",e.getMessage());
            }
        }
        return true;
    }

    public void saveToJSON(Context context)
    {
        JSONObject settings = new JSONObject();
        try {
            settings.put("wordCountForWin", wordCount);
            settings.put("timeOfRound", timeOfRound);
            settings.put("inAppSounds", inAppSound);
            settings.put("surcharge", surcharge);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        try {
            FileOutputStream outputStream;
            String filename = "roundSettings.json";
            String fileContents = settings.toString();
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();

        } catch (Exception e) {
            Log.i("Write File Error",e.getMessage());
        }
    }

    //Doesnt work, because this code is writing to the external storage. Today our external storage is read-only
    //I add the user permission to have the access to the writable external storage, but it doesnt work!
    //But I think it will be working, if we wiil be not debugging, but installing our app on the phone
    //Who want - may fix it properly, you need to remount adb shell on the virtual machine and on the adb bridge( for phone)
    //but
    //I made it with internal storage and it is perfectly fine (for settings, and also
    //we can write logs to the same directories , may be in internal files or in internal cache dir)
    public static Object objectFromFile(String path) throws IOException, ClassNotFoundException {
        Object object = null;
        File data = new File(path);
        if(data.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(data));
            object = objectInputStream.readObject();
            objectInputStream.close();
        }
        return object;
    }

    public static String objectToFile(Object object) throws IOException {
        String path = Environment.getExternalStorageDirectory() + File.separator + "/talking_crocodile/App_cache" + File.separator;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        path += "roundSettings.json";
        File data = new File(path);
        if (data.exists()) {
            data.delete();
            data.createNewFile();
        }
        else
            data.createNewFile();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(data));
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        return path;
    }
}
