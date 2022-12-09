package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuCompat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

    TextView txt_game_difficulty;
    String user_name;
    int game_difficulty;
    String game_subject;
    private ArrayList<Question> selected_questions;
    int num_of_questions = 3;
    private static final Random RANDOM = new Random();
    private String[] difs;

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

        txt_game_difficulty = (TextView) findViewById(R.id.questionDificulty);

        selected_questions = new ArrayList<>();
        difs = new String[] {getResources().getString(R.string.difEasy), getResources().getString(R.string.difMedium), getResources().getString(R.string.difHard)};

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

                    ArrayList<Question> all_questions = new ArrayList<>();
                    query = "SELECT * FROM questions_eng WHERE IDSubject = "+id_subject+" AND Difficulty = "+game_difficulty+";";
                    statement = connectDB.createStatement();
                    rs = statement.executeQuery(query);
                    while (rs.next()) {
                        Question q = new Question(Integer.valueOf(rs.getString(1)), Integer.valueOf(rs.getString(2)), rs.getString(3), Integer.valueOf(rs.getString(4)), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                        all_questions.add(q);
                    }

                    Set<Integer> randoms = pickRandom(num_of_questions, all_questions.size());
                    List<Integer> random_selection = new ArrayList<Integer>(randoms);

                    for(int i = 0 ; i < random_selection.size(); i++)
                        selected_questions.add(all_questions.get(random_selection.get(i)));

                    System.out.println(all_questions);
                    System.out.println(selected_questions);

                    game(selected_questions);
                }
            } catch (SQLException e) {
                isSuccess = false;
                z = "Exceptions" + e;
            }
            return z;
        }
    }

    public void game (ArrayList<Question> questions){
        setDifficultyText();

    }

    public void setDifficultyText(){
        String locale = getApplicationContext().getResources().getConfiguration().locale.getLanguage();
        String txt = "";

        if (locale.equals("en")){
            txt = difs[game_difficulty]+getResources().getString(R.string.question);
            txt_game_difficulty.setText(txt);
        }
        else if (locale.equals("pt")){
            txt = (getResources().getString(R.string.question)).substring(0, 1).toUpperCase() + (getResources().getString(R.string.question)).substring(1) + " " + difs[game_difficulty-1];
            txt_game_difficulty.setText(txt);
        }
    }

    public void GoToBasePage(View v){
        Intent myIntent = new Intent(this, BaseActivity.class);
        startActivity(myIntent);
    }

    public Set<Integer> pickRandom(int n, int k) {
        final Set<Integer> picked = new HashSet<>();
        while (picked.size() < n) {
            picked.add(RANDOM.nextInt(k));
        }
        return picked;
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