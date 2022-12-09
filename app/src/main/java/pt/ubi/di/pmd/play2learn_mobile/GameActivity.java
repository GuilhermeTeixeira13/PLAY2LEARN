package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

    TextView TxtViewGameDifficulty;
    TextView TxtViewQuestion;
    TextView TextViewCorrectOrWrong;
    Button BtnNextQuestion;
    Button BtnSubmit;
    CheckBox CheckBox1;
    CheckBox CheckBox2;
    CheckBox CheckBox3;
    CheckBox CheckBox4;

    String user_name;
    int game_difficulty;
    String game_subject;
    boolean submitButtonClicked;

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

        TxtViewGameDifficulty = (TextView) findViewById(R.id.questionDificulty);
        TxtViewQuestion = (TextView) findViewById(R.id.question);
        TextViewCorrectOrWrong = (TextView) findViewById(R.id.correctWrong);
        BtnNextQuestion = (Button) findViewById(R.id.BtnNextQuestion);
        BtnSubmit = (Button) findViewById(R.id.BtnSubmit);
        CheckBox1 = (CheckBox) findViewById(R.id.checkBox1);
        CheckBox2 = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox3 = (CheckBox) findViewById(R.id.checkBox3);
        CheckBox4 = (CheckBox) findViewById(R.id.checkBox4);

        selected_questions = new ArrayList<>();
        difs = new String[] {getResources().getString(R.string.difEasy), getResources().getString(R.string.difMedium), getResources().getString(R.string.difHard)};
        submitButtonClicked = false;

        // Get questions
        BuildGame buildGame = new BuildGame();
        buildGame.execute();
    }

    // Get questions
    private class BuildGame extends AsyncTask<String,String,String> {
        boolean isSuccess = false;
        String exeption = "";

        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    Toast.makeText(GameActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                } else {
                    String id_subject = getSubjectID(connectDB);
                    ArrayList<Question> all_questions = getQuestions(connectDB, id_subject);

                    List<Integer> random_selection = new ArrayList<Integer>(pickRandom(num_of_questions, all_questions.size()));
                    for(int i = 0 ; i < random_selection.size(); i++)
                        selected_questions.add(all_questions.get(random_selection.get(i)));
                    Collections.shuffle(selected_questions);

                    game(selected_questions, 0);
                }
            } catch (SQLException e) {
                isSuccess = false;
                exeption = "Exceptions" + e;
            }
            return exeption;
        }
    }

    public String getSubjectID(Connection connectDB) throws SQLException {
        String query = "SELECT id from subjects WHERE name= '"+game_subject+"'";
        Statement statement = connectDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        String id_subject = "";

        while (rs.next()) {
            id_subject = rs.getString(1);
        }

        return id_subject;
    }

    public ArrayList<Question> getQuestions(Connection connectDB, String id_subject) throws SQLException {
        String languange = getCurrentLanguage();
        String query = "";
        if(languange.equals("pt")){
            query = "SELECT * FROM questions_pt WHERE IDSubject = "+id_subject+" AND Difficulty = " + game_difficulty + ";";
        }
        else if(languange.equals("en")){
            query = "SELECT * FROM questions_eng WHERE IDSubject = "+id_subject+" AND Difficulty = " + game_difficulty + ";";
        }

        Statement statement = connectDB.createStatement();
        ResultSet rs = statement.executeQuery(query);

        ArrayList<Question> all_questions = new ArrayList<>();
        while (rs.next()) {
            Question q = new Question(Integer.valueOf(rs.getString(1)), Integer.valueOf(rs.getString(2)), rs.getString(3), Integer.valueOf(rs.getString(4)), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            all_questions.add(q);
        }

        return all_questions;
    }

    public void game (ArrayList<Question> questions, int question_num){
        prepareForNewQuestion();

        Question question = questions.get(question_num);
        ArrayList<Answer> answers = shuffleAnswers(question);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setQuestionAndAnswers(question, answers);

                BtnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitButtonClicked = true;

                        if(CheckBox1.isChecked() && !CheckBox2.isChecked() && !CheckBox3.isChecked() && !CheckBox4.isChecked()){
                            if(answers.get(0).isRightAnswer())
                                rightAnswer();
                            else
                                wrongAnswer();
                        }
                        else if(CheckBox2.isChecked() && !CheckBox1.isChecked() && !CheckBox3.isChecked() && !CheckBox4.isChecked()){
                            if(answers.get(1).isRightAnswer())
                                rightAnswer();

                            else
                                wrongAnswer();
                        }
                        else if(CheckBox3.isChecked() && !CheckBox1.isChecked() && !CheckBox2.isChecked() && !CheckBox4.isChecked()){
                            if(answers.get(2).isRightAnswer())
                                rightAnswer();
                            else
                                wrongAnswer();
                        }
                        else if(CheckBox4.isChecked() && !CheckBox1.isChecked() && !CheckBox2.isChecked() && !CheckBox3.isChecked()){
                            if(answers.get(3).isRightAnswer())
                                rightAnswer();
                            else
                                wrongAnswer();
                        }

                        afterSubmission();
                    }
                });

                BtnNextQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(submitButtonClicked){
                            if(question_num+1 < num_of_questions)
                                game(questions, question_num+1);
                            else
                                System.out.println("leave");
                        }
                    }
                });

            }
        });
    }

    public void setQuestionAndAnswers(Question question, ArrayList<Answer> answers){
        TxtViewQuestion.setText(question.getQuestion());

        CheckBox1.setText(answers.get(0).getAnswer());
        CheckBox1.setChecked(false);
        CheckBox2.setText(answers.get(1).getAnswer());
        CheckBox2.setChecked(false);
        CheckBox3.setText(answers.get(2).getAnswer());
        CheckBox3.setChecked(false);
        CheckBox4.setText(answers.get(3).getAnswer());
        CheckBox4.setChecked(false);
    }

    public ArrayList<Answer> shuffleAnswers(Question question){
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(new Answer(true, question.getRightAnswer()));
        answers.add(new Answer(false, question.getWrongAnswer1()));
        answers.add(new Answer(false, question.getWrongAnswer2()));
        answers.add(new Answer(false, question.getWrongAnswer3()));
        Collections.shuffle(answers);

        return answers;
    }

    public void rightAnswer(){
        TextViewCorrectOrWrong.setText(getResources().getString(R.string.right));
        TextViewCorrectOrWrong.setTextColor(Color.parseColor("#00FF00"));
    }

    public void wrongAnswer(){
        TextViewCorrectOrWrong.setText(getResources().getString(R.string.wrong));
        TextViewCorrectOrWrong.setTextColor(Color.parseColor("#FF3030"));
    }

    public void afterSubmission(){
        BtnSubmit.setText(getResources().getString(R.string.submited));
        BtnSubmit.setEnabled(false);

        CheckBox1.setEnabled(false);
        CheckBox2.setEnabled(false);
        CheckBox3.setEnabled(false);
        CheckBox4.setEnabled(false);
    }

    public void prepareForNewQuestion(){
        String languange = getCurrentLanguage();
        String txt = "";

        if (languange.equals("en")){
            txt = difs[game_difficulty]+getResources().getString(R.string.question);
            TxtViewGameDifficulty.setText(txt);
        }
        else if (languange.equals("pt")){
            txt = (getResources().getString(R.string.question)).substring(0, 1).toUpperCase() + (getResources().getString(R.string.question)).substring(1) + " " + difs[game_difficulty-1];
            TxtViewGameDifficulty.setText(txt);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BtnSubmit.setEnabled(true);
                BtnSubmit.setText(getResources().getString(R.string.Submit));
            }
        });

        TextViewCorrectOrWrong.setText("");
        CheckBox1.setEnabled(true);
        CheckBox2.setEnabled(true);
        CheckBox3.setEnabled(true);
        CheckBox4.setEnabled(true);

        submitButtonClicked = false;
    }

    public String getCurrentLanguage(){
        return getApplicationContext().getResources().getConfiguration().locale.getLanguage();
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