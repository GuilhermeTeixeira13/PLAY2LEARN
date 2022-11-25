package pt.ubi.di.pmd.play2learn_mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class P2L_DbHelper {
    String classs = "com.mysql.jdbc.Driver";

    String dBName = "play2learndb";
    String url = "jdbc:mysql://localhost/"+dBName;
    String dBun = "root";
    String dBpass = "";

    public Connection con(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName(classs);

            conn = DriverManager.getConnection(url, dBun, dBpass);

            conn = DriverManager.getConnection(ConnURL);
        } catch (ClassNotFoundException e) {
            Log.e("Error", e.getMessage());
        } catch (SQLException e) {
            Log.e("Error", e.getMessage());
        } catch (Exception e){
            Log.e("Error", e.getMessage());
        }
        return conn;
    }
}
