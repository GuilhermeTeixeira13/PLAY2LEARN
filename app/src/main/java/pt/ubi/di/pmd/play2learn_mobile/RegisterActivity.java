package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Intent;
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

    String AES = "AES";

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
                //encryptPass = Security.encrypt(password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String z = "";
        boolean isSuccess = false;


        @Override
        protected String doInBackground(String... strings) {
            //                                       encryptPass
            if (user.isEmpty() || email.isEmpty() || password.isEmpty()){
                z= "All fields Required";
            }else {
                try {
                    P2L_DbHelper connectNow = new P2L_DbHelper();
                    Connection connectDB = connectNow.getConnection();

                    if (connectDB == null){
                        z = "Please check your internet connection";
                    }else {
                        String query1 = "select * from users where Name='"+user+"'";

                        Statement statement = connectDB.createStatement();

                        ResultSet rs = statement.executeQuery(query1);
                        System.out.println(rs);

                        while (rs.next()) {
                            System.out.println("entrei aqui1");
                            nm = rs.getString(2);

                            if (nm.equals(user)){
                                z = "User Already used";
                            }else {
                                //String query = "INSERT INTO users values (NULL,'"+user+"','"+email+"','"+encryptPass+"',NULL,NULL)";
                                String query = "INSERT INTO users values (NULL,'"+user+"','"+email+"','"+password+"',NULL,NULL)";

                                Statement statement2 = connectDB.createStatement();
                                statement2.executeUpdate(query);

                                z = "Register successfull";
                                isSuccess = true;
                            }
                        }

                    }
                } catch (Exception e) {
                    isSuccess = false;
                    z = "Exceptions"+e;
                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(),""+z,Toast.LENGTH_LONG).show();
            if (isSuccess){
                Intent intent = new Intent(RegisterActivity.this, BaseActivity.class);
                intent.putExtra("name", user);
                startActivity(intent);
            }
        }
    }



    public void GoToLoginPage(View v){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    public void GoToBasePage(View v){
        Intent myIntent = new Intent(this, BaseActivity.class);
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

}