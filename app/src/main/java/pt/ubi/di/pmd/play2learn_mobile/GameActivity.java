package pt.ubi.di.pmd.play2learn_mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.stream.IntStream;

public class GameActivity extends AppCompatActivity {
    TextView TxtViewGameDifficulty;
    TextView TxtViewQuestion;
    TextView TextViewCorrectOrWrong;
    TextView TxtViewTimer;
    TextView TxtViewAdvice;
    Button BtnSubmit;
    CheckBox CheckBox1;
    CheckBox CheckBox2;
    CheckBox CheckBox3;
    CheckBox CheckBox4;

    String user_name;
    int game_difficulty;
    String game_subject;
    String id_subject;

    private ArrayList<Question> selected_questions;
    int num_of_questions = 10;
    private static final Random RANDOM = new Random();
    private String[] difs;
    private ArrayList<Answer> answers;
    private int num_right_answers = 0;
    private int num_wrong_answers = 0;
    private int test_time = 0;
    private double final_score = 0;
    int question_num = 0;

    CountDownTimer timer;

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
        SharedPreferences sp = getSharedPreferences("userLogged", MODE_PRIVATE);
        if (sp.contains("uname")) {
            user_name = sp.getString("uname", "");
        }

        Intent intent = getIntent();
        String checkFlag= intent.getStringExtra("flag");

        if(checkFlag.equals("FROM_SUBJECTS")){
            game_subject = (String) intent.getSerializableExtra("subject");
            game_difficulty = (int) intent.getSerializableExtra("difficulty");
        }

        TxtViewGameDifficulty = findViewById(R.id.questionDificulty);
        TxtViewQuestion = findViewById(R.id.question);
        TextViewCorrectOrWrong = findViewById(R.id.correctWrong);
        TxtViewTimer = findViewById(R.id.backgroundTimer);
        TxtViewAdvice = findViewById(R.id.advice);
        BtnSubmit = findViewById(R.id.BtnSubmit);
        CheckBox1 = findViewById(R.id.checkBox1);
        CheckBox2 = findViewById(R.id.checkBox2);
        CheckBox3 = findViewById(R.id.checkBox3);
        CheckBox4 = findViewById(R.id.checkBox4);

        selected_questions = new ArrayList<>();
        difs = new String[] {
                getResources().getString(R.string.difEasy),
                getResources().getString(R.string.difMedium),
                getResources().getString(R.string.difHard)
        };

        // Build game flow
        BuildGame buildGame = new BuildGame();
        buildGame.execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        cheat();
    }

    // Build game flow
    private class BuildGame extends AsyncTask<String,String,String> {
        String exception = "";

        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            final Toast toast = Toast.makeText(GameActivity.this, getResources().getString(R.string.InternetConnection), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                } else {
                    ArrayList<Question> all_questions = getQuestions(connectDB, game_subject);

                    System.out.println("all_questions -> "+all_questions);

                    List<Integer> random_selection = new ArrayList<Integer>(pickRandom(num_of_questions, all_questions.size()));


                    System.out.println(random_selection);

                    for(int i = 0 ; i < random_selection.size(); i++){
                        selected_questions.add(all_questions.get(random_selection.get(i)));
                    }
                    Collections.shuffle(selected_questions);

                    System.out.println("selected_questions -> "+selected_questions);

                    game(selected_questions);
                }
            } catch (SQLException e) {
                exception = getResources().getString(R.string.Exceptions) + e;
            }
            return exception;
        }
    }

    // Save Results
    private class SaveResults extends AsyncTask<String,String,String> {
        String exception = "";

        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            final Toast toast = Toast.makeText(GameActivity.this, getResources().getString(R.string.InternetConnection), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                } else {
                    final_score = ((num_right_answers / num_of_questions) * 500) + ((6000-test_time)/12);

                    String query = "INSERT INTO userresults (`id`, `IDSubject`, `IDUser`, `Score`, `NumCorrectAns`, `NumWrongAns`, `TimeToSolve`, `Difficulty`) " +
                            "values (NULL," + game_subject + "," + getUserID(connectDB) + "," + final_score + "," + num_right_answers + "," + num_wrong_answers  + ",'" + test_time + "'," + game_difficulty +")";

                    Statement statement = connectDB.createStatement();
                    statement.executeUpdate(query);
                }
            } catch (SQLException e) {
                exception = getResources().getString(R.string.Exceptions) + e;
            }
            return exception;
        }
    }

    public String getUserID(Connection connectDB) throws SQLException {
        String query = "SELECT id FROM users WHERE name= '"+user_name+"'";
        Statement statement = connectDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        String id_user = "";

        while (rs.next()) {
            id_user = rs.getString(1);
        }

        return id_user;
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

    public void game (ArrayList<Question> questions){
        prepareForNewQuestion(question_num);

        Question question = questions.get(question_num);
        answers = shuffleAnswers(question);

        runOnUiThread(() -> {
            setTimer(60000, 1000);

            setQuestionAndAnswers(question, answers);

            BtnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(BtnSubmit.getText().equals(getResources().getString(R.string.Submit))){
                        if(CheckBox1.isChecked() && !CheckBox2.isChecked() && !CheckBox3.isChecked() && !CheckBox4.isChecked()){
                            if(answers.get(0).isRightAnswer())
                                rightAnswer();
                            else
                                wrongAnswer(answers);
                            afterSubmission();
                        }
                        else if(CheckBox2.isChecked() && !CheckBox1.isChecked() && !CheckBox3.isChecked() && !CheckBox4.isChecked()){
                            if(answers.get(1).isRightAnswer())
                                rightAnswer();
                            else
                                wrongAnswer(answers);
                            afterSubmission();
                        }
                        else if(CheckBox3.isChecked() && !CheckBox1.isChecked() && !CheckBox2.isChecked() && !CheckBox4.isChecked()){
                            if(answers.get(2).isRightAnswer())
                                rightAnswer();
                            else
                                wrongAnswer(answers);
                            afterSubmission();
                        }
                        else if(CheckBox4.isChecked() && !CheckBox1.isChecked() && !CheckBox2.isChecked() && !CheckBox3.isChecked()){
                            if(answers.get(3).isRightAnswer())
                                rightAnswer();
                            else
                                wrongAnswer(answers);
                            afterSubmission();
                        }
                        else {
                            Toast.makeText(GameActivity.this, getResources().getString(R.string.VerifyAnswers), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if(question_num + 1 < num_of_questions){
                            question_num += 1;
                            game(questions);
                        }
                        else {
                            SaveResults saveResults = new SaveResults();
                            saveResults.execute();
                            GoToResultsPage(getWindow().getDecorView());
                        }
                    }
                }
            });
        });
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

    public void colorCheckBoxes (ArrayList<Answer> answers) {
        CheckBox[] checkBoxes = {CheckBox1,CheckBox2,CheckBox3,CheckBox4};
        for(int i=0; i<answers.size(); i++){
            if(answers.get(i).isRightAnswer())
                checkBoxes[i].setTextColor(Color.parseColor("#00FF00"));
             else
                checkBoxes[i].setTextColor(Color.parseColor("#FF3030"));
        }
    }

    public void setTimer(long milisec, long countDown){
        timer = new CountDownTimer( milisec,countDown) {
            String min, sec, mil;
            public void onTick(long millisUntilFinished) {
                min = String.valueOf(millisUntilFinished / (60 * 1000) % 60);
                sec = String.valueOf(millisUntilFinished / 1000 % 60);

                if (Long.parseLong(min) < 10)
                    min = "0"+ millisUntilFinished / (60 * 1000) % 60;
                if (Long.parseLong(sec) < 10)
                    sec = "0"+ millisUntilFinished / 1000 % 60;

                TxtViewTimer.setText(min+":"+sec);
            }

            public void onFinish() {
                wrongAnswer(answers);
                afterSubmission();
            }

        }.start();
    }

    public void afterSubmission(){
        timer.cancel();
        CheckBox1.setEnabled(false);
        CheckBox2.setEnabled(false);
        CheckBox3.setEnabled(false);
        CheckBox4.setEnabled(false);

        if(question_num + 1 == num_of_questions)
            BtnSubmit.setText(getResources().getString(R.string.FinishGame));
        else
            BtnSubmit.setText(getResources().getString(R.string.NextQuestion));
    }

    public void rightAnswer(){
        num_right_answers++;
        test_time += (60 - Integer.parseInt(TxtViewTimer.getText().toString().substring(3)));

        colorCheckBoxes(answers);
        TextViewCorrectOrWrong.setText(getResources().getString(R.string.right));
        TextViewCorrectOrWrong.setTextColor(Color.parseColor("#00FF00"));
    }

    public void wrongAnswer(ArrayList<Answer> answers){
        num_wrong_answers++;
        test_time += (60 - Integer.parseInt(TxtViewTimer.getText().toString().substring(3)));

        colorCheckBoxes(answers);
        TextViewCorrectOrWrong.setText(getResources().getString(R.string.wrong));
        TextViewCorrectOrWrong.setTextColor(Color.parseColor("#FF3030"));
    }

    public void cheat(){
        if(BtnSubmit.getText().equals(getResources().getString(R.string.Submit))){
            TxtViewAdvice.setText(getResources().getString(R.string.minimizeApp));
            wrongAnswer(answers);
            afterSubmission();
            timer.cancel();
        }
    }

    public void prepareForNewQuestion(int question_num){
        String languange = getCurrentLanguage();
        String txt = (question_num+1) + " - ";

        if (languange.equals("en")){
            txt = txt + difs[game_difficulty-1] + " " + getResources().getString(R.string.question);
            TxtViewGameDifficulty.setText(txt);
        }
        else if (languange.equals("pt")){
            txt = txt + (getResources().getString(R.string.question)).substring(0, 1).toUpperCase() + (getResources().getString(R.string.question)).substring(1) + " " + difs[game_difficulty-1];
            TxtViewGameDifficulty.setText(txt);
        }

        runOnUiThread(() -> {
            BtnSubmit.setEnabled(true);
            BtnSubmit.setText(getResources().getString(R.string.Submit));
        });

        TextViewCorrectOrWrong.setText("");
        TxtViewAdvice.setText("");

        CheckBox1.setEnabled(true);
        CheckBox1.setTextColor(Color.parseColor("#000000"));
        CheckBox2.setEnabled(true);
        CheckBox2.setTextColor(Color.parseColor("#000000"));
        CheckBox3.setEnabled(true);
        CheckBox3.setTextColor(Color.parseColor("#000000"));
        CheckBox4.setEnabled(true);
        CheckBox4.setTextColor(Color.parseColor("#000000"));
    }

    public String getCurrentLanguage(){
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", MODE_PRIVATE);
        String locale = prefs.getString("Language", "en");

        return locale;
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
                new AlertDialog.Builder(GameActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getResources().getString(R.string.GoHome1))
                        .setMessage(getResources().getString(R.string.GoHome2))
                        .setPositiveButton(getResources().getString(R.string.YES), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                GoToBasePage(getWindow().getDecorView());
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.NO), null)
                        .show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void GoToResultsPage(View v){
        Intent goToResultsActivity = new Intent(this, ResultsActivity.class);
        goToResultsActivity.putExtra("flag", "FROM_GAME");
        goToResultsActivity.putExtra("dif", game_difficulty);
        goToResultsActivity.putExtra("temaID", game_subject);
        startActivity(goToResultsActivity);
    }

    public void GoToBasePage(View v){
        Intent goToBaseActivity = new Intent(this, BaseActivity.class);
        goToBaseActivity.putExtra("flag", "FROM_GAME");
        startActivity(goToBaseActivity);
    }
}