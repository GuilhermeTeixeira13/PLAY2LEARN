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
import java.sql.Statement;

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

    public void Register(){
        String user = usr.getText().toString();
        String email = eml.getText().toString();
        String password = pass.getText().toString();

        if (user.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "All fields Required", Toast.LENGTH_SHORT).show();
        }else {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null){
                    Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }else {
                    String query = "INSERT INTO users values ('"+user+"','"+email+"','"+password+"')";

                    Statement statement = connectDB.createStatement();
                    statement.executeUpdate(query);

                    Toast.makeText(this, "Register successfull", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(this, BaseActivity.class);
                    myIntent.putExtra("name", user);
                    startActivity(myIntent);
                }
            } catch (Exception e) {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
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