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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText usr, password;
    P2L_DbHelper connectionhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Change toolbar title
        setTitle(getResources().getString(R.string.MainActivity));

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
        String nm,pss;
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
                        String query = "select * from users where Name='"+user+"' and Password='"+pass+"'";

                        Statement statement = connectDB.createStatement();

                        ResultSet rs = statement.executeQuery(query);

                        while (rs.next()){
                            nm = rs.getString(2);
                            System.out.println(nm);
                            pss = rs.getString(4);
                            System.out.println(pss);

                            if(nm.equals(user) && pss.equals(pass)){
                                isSuccess = true;
                                z = "Login successfull";
                                System.out.println("Login successfull");
                            }else {
                                isSuccess = false;
                                System.out.println("Login NOT successfull");
                            }
                        }

                    }

                } catch (SQLException e) {
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
                Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                intent.putExtra("name", user);
                startActivity(intent);
            }
        }
    }

    public void GoToBasePage(View v){
        Intent myIntent = new Intent(this, BaseActivity.class);
        startActivity(myIntent);
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
}