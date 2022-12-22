package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText usr, password;
    P2L_DbHelper connectionhelper;
    static boolean isInit = true;
    String autolog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If it is the first time loading the view, it loads the last language used by the user
        if(isInit) {
            isInit = false;
            loadPreviousLanguage();
        }

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Change toolbar title
        setTitle(getResources().getString(R.string.MainActivity));

        //auto-login
        SharedPreferences sp = getSharedPreferences("userLogged", MODE_PRIVATE);
        if (sp.contains("uname")){
            autolog = sp.getString("uname", "");

            Intent goToBaseActivity = new Intent(this, BaseActivity.class);
            goToBaseActivity.putExtra("flag", "FROM_MAIN");
            startActivity(goToBaseActivity);
        }

        //login
        usr = findViewById(R.id.LoginEdTextUserName);
        password = findViewById(R.id.LoginEdTextPassword);

        connectionhelper = new P2L_DbHelper();
    }

    public void Login(View v) throws SQLException {
        Dologin dologin = new Dologin();
        dologin.execute();
    }

    private class Dologin extends AsyncTask<String,String,String>{
        String nm, eml,pss, dpss, exception;
        String user = usr.getText().toString();
        String pass = password.getText().toString();
        boolean isSuccess = false;

        @Override
        protected String doInBackground(String... strings) {
            if (user.isEmpty() || pass.isEmpty()){
                runOnUiThread(new Runnable() {
                    public void run() {
                        final Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.AllFieldsRequired), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }else {
                try {
                    P2L_DbHelper connectNow = new P2L_DbHelper();
                    Connection connectDB = connectNow.getConnection();

                    if (connectDB== null){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                final Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.InternetConnection), Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    }else {
                        String query = "SELECT * FROM users WHERE Name='"+user+"' OR Email='"+user+"' AND Password='"+pass+"'";

                        Statement statement = connectDB.createStatement();
                        ResultSet rs = statement.executeQuery(query);

                        while (rs.next()){
                            nm = rs.getString(2);
                            eml = rs.getString(3);
                            pss = rs.getString(4);
                            //obter pass desencriptada
                            dpss = Security.decrypt(pss);

                            if((nm.equals(user) || eml.equals(user)) && dpss.equals(pass)){
                                isSuccess = true;
                                exception = getResources().getString(R.string.LoginSuccessfull);
                            }else {
                                isSuccess = false;
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        final Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.UserNotExist), Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });
                            }
                        }

                    }

                } catch (SQLException e) {
                    isSuccess = false;
                    exception = getResources().getString(R.string.Exceptions) + e;
                }catch (Exception e) {
                    isSuccess = false;
                    e.printStackTrace();
                    exception = getResources().getString(R.string.Exceptions) + e;
                }

            }
            return exception;
        }

        @Override
        protected void onPostExecute(String s) {
            if (isSuccess){
                Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences("userLogged", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("uname", nm);
                editor.apply();

                Intent goToBaseActivity = new Intent(MainActivity.this, BaseActivity.class);
                goToBaseActivity.putExtra("flag", "FROM_MAIN");
                startActivity(goToBaseActivity);
            }
        }
    }

    public void GoToBasePage(View v){
        Intent goToBaseActivity = new Intent(this, BaseActivity.class);
        goToBaseActivity.putExtra("flag", "FROM_MAIN");
        startActivity(goToBaseActivity);
    }

    public void GoToRegisterPage(View v){
        Intent myIntent = new Intent(this, RegisterActivity.class);
        startActivity(myIntent);
    }

    // Inflating the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Toolbar button clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shareButton:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = getResources().getString(R.string.Share1);
                String shareSubject = "Play2learn @ UBI";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.Share2)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // When the language is changed, it is saved using Shared Preferences
    public void saveLanguage(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }

    // Loading the language that was previously saved in Shared Preferences
    public void loadPreviousLanguage() {
        // Loading
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLanguage(language);
    }

    // Change the app language to other one
    public void changeLanguage(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
        saveLanguage(lang);
        recreate();
    }
}