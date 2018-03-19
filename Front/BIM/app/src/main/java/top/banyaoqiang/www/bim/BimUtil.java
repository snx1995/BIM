package top.banyaoqiang.www.bim;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static top.banyaoqiang.www.bim.MainActivity.TAG;

/**
 * Created by byq on 18-2-28.
 */

public class BimUtil {
}

abstract class SendGetRequest{
    static final String SUCCESS = "success";
    static final String ERROR = "error";


    private String url;

    public SendGetRequest(String url) {
        this.url = url;
    }

    public void send(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    onResponse(responseString);
                }catch (Exception e){
                    onError(e);
                }
            }
        }).start();
    }

    abstract void onResponse(String response);
    abstract void onError(Exception e);
}

class SendPostRequest{

}

class BimSafe{
    static String passwordEncode(String password){
        return password;
    }
}

class BimFile{
    static Boolean fileExists(String filename){
        try{
            File f = new File(filename);
            if(!f.exists()) return  false;
        }catch (Exception e){
            Log.d(TAG, "fileExists: " + e.getMessage());
            return false;
        }
        return true;
    }

    static void saveToFile(Context context, String filename, BimData data, int mode){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = context.openFileOutput(filename, mode);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data.toJSONString());
        }catch (Exception e){
            Log.d(TAG, "BimFile.saveToFile: " + e.getMessage());
        }finally {
            try{
                if (writer != null) writer.close();
            }catch (Exception e){
                Log.d(TAG, "BimFile.saveToFile: " + e.getMessage());
            }
        }
    }

    static void saveToFile(Context context, String filename, BimData data){
        saveToFile(context, filename, data, Context.MODE_PRIVATE);
    }

    static void saveToFile(Context context, String filename, String data, int mode){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = context.openFileOutput(filename, mode);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        }catch (Exception e){
            Log.d(TAG, "BimFile.saveToFile: " + e.getMessage());
        }finally {
            try{
                if (writer != null) writer.close();
            }catch (Exception e){
                Log.d(TAG, "BimFile.saveToFile: " + e.getMessage());
            }
        }
    }

    static void saveToFile(Context context, String filename, String data){
        saveToFile(context, filename, data, Context.MODE_PRIVATE);
    }

    static String readFromFile(Context context, String filename){
        FileInputStream input = null;
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try{
            input = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line);

        }catch (Exception e){
            Log.d(TAG, "BimFile.readFromFile: " + e.getMessage());
        }finally {
            if(reader != null) {
                try{
                    reader.close();
                } catch (Exception e) {
                    Log.d(TAG, "BimFile.readFromFile: " + e.getMessage());
                }
            }
        }
        return builder.toString();
    }

    static String readFromFile(Context context, String filename, int lines){
        return "";
    }

    static String readFronFile(Context context, String filename, int beginLine, int endLine){
        return "";
    }
}

class BimDB extends SQLiteOpenHelper {

    private static final String CREATE_MESSAGE_TABLE_SQL = "create table message(" +
            "id integer primary key autoincrement," +
            "host integer," +
            "friend integer," +
            "text text," +
            "read integer," +
            "date text," +
            "type integer)";
    private static final String CREATE_USER_TABLE_SQL = "create table user(" +
            "id integer primary key," +
            "email text," +
            "phone text," +
            "password text," +
            "last_use text," +
            "default_login integer)";
    private static final String CREATE_ADDRESS_BOOK_SQL = "create table addressbook(" +
            "id integer primary key autoincrement default 0," +
            "host integer" +
            "friend integer" +
            "friendid integer" +
            "name text" +
            "image integer)";


    private Context context;


    public BimDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_MESSAGE_TABLE_SQL);
        sqLiteDatabase.execSQL(CREATE_USER_TABLE_SQL);
        sqLiteDatabase.execSQL(CREATE_ADDRESS_BOOK_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
