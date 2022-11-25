package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Intent;
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

        String nm,pss;
        String user = usr.getText().toString();
        String pass = password.getText().toString();
        Connection connection = connectionhelper.con();

        if (user.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "All fields Required", Toast.LENGTH_SHORT).show();
        }else {
            String query = "select * from users where Name='"+user+"' and Password='"+pass+"'";

            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                nm = rs.getString(1);
                pss = rs.getString(3);

                if(nm.equals(user) && pss.equals(pass)){
                    Toast.makeText(this, "Login successfull", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(this, BaseActivity.class);
                    myIntent.putExtra("name", user);
                    startActivity(myIntent);
                }else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                }
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