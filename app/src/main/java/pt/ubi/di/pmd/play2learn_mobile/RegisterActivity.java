package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RegisterActivity extends AppCompatActivity {

    EditText usr;
    EditText eml;
    EditText pass;
    P2L_DbHelper connectionhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Change toolbar title
        setTitle(getResources().getString(R.string.RegisterActivity));

        // Register
        usr = findViewById(R.id.LoginEdTextUserName);
        eml = findViewById(R.id.LoginEdTextEmail);
        pass = findViewById(R.id.LoginEdTextPassword);

        connectionhelper = new P2L_DbHelper();

    }

    public void Register(View v){
        Doregister doregister = new Doregister();
        doregister.execute();
    }

    public class Doregister extends AsyncTask<String,String,String>{
        String user = usr.getText().toString();
        String email = eml.getText().toString();
        String password = pass.getText().toString();
        String encryptPass, nm;

        {
            try {
                //encripta a pass
                encryptPass = Security.encrypt(password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String exception = "";
        boolean isSuccess = false;
        boolean userexists = false;
        int countu = 0;


        @Override
        protected String doInBackground(String... strings) {
            if (user.isEmpty() || email.isEmpty() || password.isEmpty()){
                runOnUiThread(new Runnable() {
                    public void run() {
                        final Toast toast = Toast.makeText(RegisterActivity.this, getResources().getString(R.string.AllFieldsRequired), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }else {
                try {
                    P2L_DbHelper connectNow = new P2L_DbHelper();
                    Connection connectDB = connectNow.getConnection();

                    if (connectDB == null){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                final Toast toast = Toast.makeText(RegisterActivity.this, getResources().getString(R.string.InternetConnection), Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    }else {
                        String query1 = "SELECT * FROM users";

                        Statement statement = connectDB.createStatement();
                        ResultSet rs = statement.executeQuery(query1);

                        while (rs.next()) {
                            nm = rs.getString(2);

                            if (nm.equals(user) && countu == 0){
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        final Toast toast = Toast.makeText(RegisterActivity.this, getResources().getString(R.string.UserUsed), Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });
                                userexists = true;
                                countu += 1;

                            }else if (countu == 0){
                                userexists = false;
                            }
                        }
                        if (!userexists) {
                            String query = "INSERT INTO users values (NULL,'"+user+"','"+email+"','"+encryptPass+"',NULL,NULL)";
                            //String query = "INSERT INTO users VALUES (NULL,'" + user + "','" + email + "','" + password + "',NULL,NULL)";

                            Statement statement2 = connectDB.createStatement();
                            statement2.executeUpdate(query);

                            exception = getResources().getString(R.string.RegisterSuccessfull);
                            isSuccess = true;
                        }

                    }
                } catch (Exception e) {
                    isSuccess = false;
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
                editor.putString("uname", user);
                editor.apply();

                Intent goToBaseActivity = new Intent(RegisterActivity.this, BaseActivity.class);
                goToBaseActivity.putExtra("flag", "FROM_REGISTER");
                startActivity(goToBaseActivity);
            }
        }
    }



    public void GoToLoginPage(View v){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    public void GoToBasePage(View v){
        Intent goToBaseActivity = new Intent(this, BaseActivity.class);
        goToBaseActivity.putExtra("flag", "FROM_REGISTER");
        startActivity(goToBaseActivity);
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

}