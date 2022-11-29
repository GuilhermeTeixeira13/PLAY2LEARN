package pt.ubi.di.pmd.play2learn_mobile;

/*
import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class P2L_DbHelper {
    String classs = "com.mysql.jdbc.Driver";

    String dBName = "play2learndb";
    String url = "jdbc:mysql://localhost/play2learndb";
    String dBun = "root";
    String dBpass = "";

    public Connection con(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        //String ConnURL = null;
        try {
            Class.forName(classs);

            conn = DriverManager.getConnection(url, dBun, dBpass);

            //conn = DriverManager.getConnection(ConnURL);
        } catch (ClassNotFoundException e) {
            Log.e("Error", e.getMessage());
        } catch (SQLException e) {
            Log.e("Error", e.getMessage());
        } catch (Exception e){
            Log.e("Error", e.getMessage());
        }
        return conn;
    }
}*/

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;

public class P2L_DbHelper {
    public Connection databaseLink;

    public Connection getConnection(){
        String dataBaseName = "play2learndb";
        String dataBaseUser = "joao1";
        String dataBasePassword = "";
        String url= "jdbc:mysql://192.168.1.75/"+dataBaseName; // Mudar para o IP DO PC

        try{
            databaseLink = DriverManager.getConnection(url, dataBaseUser, dataBasePassword);
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }
}
