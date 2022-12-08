package pt.ubi.di.pmd.play2learn_mobile;

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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuCompat;

import org.w3c.dom.ls.LSOutput;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    String user_name;
    int game_difficulty;
    String game_subject;
    private ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Change toolbar title
        setTitle(getResources().getString(R.string.SubjectsActivity));

        // Check flag and initialize objects
        Intent intent = getIntent();
        String checkFlag= intent.getStringExtra("flag");
        if(checkFlag.equals("FROM_SUBJECTS")){
            user_name = (String) intent.getSerializableExtra("name");
            game_subject = (String) intent.getSerializableExtra("subject");
            game_difficulty = (int) intent.getSerializableExtra("difficulty");
        }

        questions = new ArrayList<>();

        // Get questions
        GetQuestions getQuestions = new GetQuestions();
        getQuestions.execute();
    }

    // Get questions
    private class GetQuestions extends AsyncTask<String,String,String> {
        boolean isSuccess = false;
        String z = "";

        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    Toast.makeText(GameActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                } else {
                    String query = "SELECT id from subjects WHERE name= '"+game_subject+"'";
                    Statement statement = connectDB.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    String id_subject = "";

                    while (rs.next()) {
                        id_subject = rs.getString(1);
                    }

                    query = "SELECT * FROM questions_eng WHERE IDSubject = "+id_subject+" AND Difficulty = "+game_difficulty+";";
                    statement = connectDB.createStatement();
                    rs = statement.executeQuery(query);
                    while (rs.next()) {
                        Question q = new Question(Integer.valueOf(rs.getString(1)), Integer.valueOf(rs.getString(2)), rs.getString(3), Integer.valueOf(rs.getString(4)), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                        questions.add(q);
                    }
                }
            } catch (SQLException e) {
                isSuccess = false;
                z = "Exceptions" + e;
            }
            return z;
        }
    }

    public void GoToBasePage(View v){
        Intent myIntent = new Intent(this, BaseActivity.class);
        startActivity(myIntent);
    }

    // Inflating the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_game, menu);
        MenuCompat.setGroupDividerEnabled(menu, true);
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
            case R.id.homeButton:
                // Mostrar aviso

                GoToBasePage(getWindow().getDecorView());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}