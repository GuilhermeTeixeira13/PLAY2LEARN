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
            //System.out.println("dei auto login pelas shp");
            autolog = sp.getString("uname", "");

            Intent goToBaseActivity = new Intent(this, BaseActivity.class);
            goToBaseActivity.putExtra("flag", "FROM_MAIN");
            goToBaseActivity.putExtra("name", autolog);
            System.out.println("Da main para Base 0 --> "+autolog);
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
        String nm, eml,pss, dpss;
        String user = usr.getText().toString();
        String pass = password.getText().toString();
        String z = "";
        boolean isSuccess = false;

        @Override
        protected String doInBackground(String... strings) {
            if (user.isEmpty() || pass.isEmpty()){
                z= "All fields Required";
            }else {
                try {
                    P2L_DbHelper connectNow = new P2L_DbHelper();
                    Connection connectDB = connectNow.getConnection();
                    System.out.println(connectDB);

                    if (connectDB== null){
                        z = "Please check your internet connection";
                    }else {
                        String query = "select * from users where Name='"+user+"' or Email='"+user+"' and Password='"+pass+"' ";

                        Statement statement = connectDB.createStatement();

                        ResultSet rs = statement.executeQuery(query);
                        System.out.println(rs);

                        while (rs.next()){
                            System.out.println("entrei aqui1");
                            nm = rs.getString(2);
                            eml = rs.getString(3);
                            System.out.println(nm);
                            pss = rs.getString(4);
                            //obter pass desencriptada
                            //dpss = Security.decrypt(pss);

                            //System.out.println(dpss);

                            if((nm.equals(user) || eml.equals(user)) && pss.equals(pass)){
                                isSuccess = true;
                                z = "Login successfull";
                                System.out.println("Login successfull");
                            }else {
                                isSuccess = false;
                                z = "user does not exist";
                                System.out.println("Login NOT successfull");
                            }
                        }

                    }

                } catch (SQLException e) {
                    isSuccess = false;
                    z = "Exceptions"+e;
                }catch (Exception e) {
                    isSuccess = false;
                    e.printStackTrace();
                    z = "Exceptions"+e;
                }

            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(),""+z,Toast.LENGTH_LONG).show();
            //System.out.println("cheguei aqui");

            if (isSuccess){
                SharedPreferences sp = getSharedPreferences("userLogged", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("uname", nm);
                editor.apply();

                Intent goToBaseActivity = new Intent(MainActivity.this, BaseActivity.class);
                goToBaseActivity.putExtra("flag", "FROM_MAIN");
                System.out.println("Da main para Base 1 --> "+user);
                goToBaseActivity.putExtra("name", user);
                startActivity(goToBaseActivity);
            }
        }
    }

    public void GoToBasePage(View v){
        Intent goToBaseActivity = new Intent(this, BaseActivity.class);
        goToBaseActivity.putExtra("flag", "FROM_MAIN");
        System.out.println("Da main para Base 2 --> "+usr.getText());
        goToBaseActivity.putExtra("name", usr.getText());
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