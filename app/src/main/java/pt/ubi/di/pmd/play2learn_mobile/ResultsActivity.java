package pt.ubi.di.pmd.play2learn_mobile;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuCompat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ResultsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    ArrayList<String> friendsName = new ArrayList<>();
    ArrayList<String> friendsIdSameTest = new ArrayList<>();
    String nameuserlogged;

    int difEsc;
    String temaID;
    int friendSelected = -1;

    private Spinner spinnerFriends;
    TextView correctAnswers;
    TextView points;

    TextView correctAnsMe;
    TextView wrongAnsMe;
    TextView timeToSolveAnsMe;
    TextView finalScoreMe;

    TextView correctAnsO;
    TextView wrongAnsO;
    TextView timeToSolveAnsO;
    TextView finalScoreO;

    TextView otherFriend;
    TextView txtViewMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Change toolbar title
        setTitle(getResources().getString(R.string.ResultsActivity));


        // Spinner Friends Test
        spinnerFriends = findViewById(R.id.spinnerCompareWith);

        SharedPreferences sp = getSharedPreferences("userLogged", MODE_PRIVATE);
        if (sp.contains("uname")) {
            //System.out.println("dei auto login pelas shp");
            nameuserlogged = sp.getString("uname", "");
        }


        // Check flag and initialize objects
        Intent intent = getIntent();
        String checkFlag= intent.getStringExtra("flag");
        if(checkFlag.equals("FROM_GAME")){
            temaID = (String) intent.getSerializableExtra("temaID");
            difEsc = (int) intent.getSerializableExtra("dif");
        }

        System.out.println("USER LOGADO: "+ nameuserlogged + "  DIF: "+ difEsc + "TemaID: " + temaID);

        ResultsActivity.friendsAssTest friendsTest = new ResultsActivity.friendsAssTest();
        friendsTest.execute();

        spinnerFriends.setOnItemSelectedListener(this);

        ResultsActivity.yourscore yourscore = new ResultsActivity.yourscore();
        yourscore.execute();

        ResultsActivity.currentTestByUser currentTest = new ResultsActivity.currentTestByUser();
        currentTest.execute();

        points = findViewById(R.id.points);
        correctAnswers = findViewById(R.id.correctAnswers);

        correctAnsMe = findViewById(R.id.correctAnsMe);
        wrongAnsMe = findViewById(R.id.wrongAnsMe);
        timeToSolveAnsMe = findViewById(R.id.timeToSolveMe);
        finalScoreMe = findViewById(R.id.finalScoreMe);

        correctAnsO = findViewById(R.id.correctAnsOther);
        wrongAnsO = findViewById(R.id.wrongAnsOther);
        timeToSolveAnsO = findViewById(R.id.timeToSolveOther);
        finalScoreO = findViewById(R.id.finalScoreOther);

        otherFriend = findViewById(R.id.otherFriendID);
        txtViewMe = findViewById(R.id.txtViewMe);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getSelectedItem().equals("My last test") || adapterView.getSelectedItem().equals("Último teste")) {
            friendSelected = -1;
            ResultsActivity.currentTestByUser currentTest = new ResultsActivity.currentTestByUser();
            currentTest.execute();

        }
        else {
            friendSelected = i;
            ResultsActivity.currentTestByUser currentTest = new ResultsActivity.currentTestByUser();
            currentTest.execute();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private  class yourscore extends AsyncTask<String,String,String> {
        boolean isSuccess = false;
        String msg = "";
        String idUserLogged;
        String correctAns;
        String score;


        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    Toast.makeText(ResultsActivity.this, getResources().getString(R.string.InternetConnection), Toast.LENGTH_SHORT).show();
                } else {
                    String query1 = "SELECT id From users where users.Name='" + nameuserlogged + "'";
                    Statement statement = connectDB.createStatement();
                    ResultSet rs1 = statement.executeQuery(query1);

                    while (rs1.next()) {
                        idUserLogged = rs1.getString(1);
                    }

                    String query3 = "SELECT * FROM userresults WHERE IDUser = " + idUserLogged + " and IDSubject = " + temaID + " and Difficulty = " + difEsc + " Order by ID DESC";
                    ResultSet rs3 = statement.executeQuery(query3);

                    while (rs3.next()) {
                        correctAns = rs3.getString(5);
                        score = rs3.getString(4);;
                        break;
                    }


                }
            } catch (SQLException e) {
                isSuccess = false;
                msg = "Exceptions" + e;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            points.setText(score + getResources().getString(R.string.Points));
            correctAnswers.setText(getResources().getString(R.string.CorrectAnswer)+correctAns+"\\10");


        }
    }


    // BD LIGAÇÃO FRIENDS ASSOCIATED WITH TEST ID
    private class friendsAssTest extends AsyncTask<String,String,String> {
        boolean isSuccess = false;
        String z = "";
        String idUserLogged;
        ArrayList<String> friendsID = new ArrayList<>();
        ArrayList<String> testID = new ArrayList<>();
        ArrayList<String> friendsNameSameTest = new ArrayList<>();
        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    Toast.makeText(ResultsActivity.this, getResources().getString(R.string.InternetConnection), Toast.LENGTH_SHORT).show();
                } else {
                    String query1 = "SELECT id From users where users.Name='" + nameuserlogged + "'";
                    Statement statement = connectDB.createStatement();
                    ResultSet rs1 = statement.executeQuery(query1);

                    while (rs1.next()) {
                        idUserLogged = rs1.getString(1);
                    }
                    System.out.println("USER LOGGED: " + idUserLogged);

                    String query2 = "SELECT IDFriend FROM userfriends WHERE IDUser = " + idUserLogged;
                    ResultSet rs2 = statement.executeQuery(query2);

                    while (rs2.next()) {
                        friendsID.add(rs2.getString(1));
                    }
                    System.out.println("FRIENDS ID: " + friendsID);

                    for(int i = 0; i < friendsID.size(); i++) {
                        String query3 = "SELECT id FROM userresults WHERE IDUser =" + friendsID.get(i) + " and IDSubject =" + temaID + " and Difficulty = " + difEsc;
                        ResultSet rs3 = statement.executeQuery(query3);

                        while (rs3.next()) {
                            testID.add(rs3.getString(1));
                        }
                    }
                    System.out.println("TEST FRIENDS ID: " + testID);

                    for(int i = 0; i < testID.size(); i++) {
                        String query4 = "SELECT idUser FROM userresults WHERE id = " + testID.get(i);
                        ResultSet rs4 = statement.executeQuery(query4);

                        while (rs4.next()) {
                            friendsIdSameTest.add(rs4.getString(1));
                        }
                    }

                    System.out.println("SAME TEST FRIENDS ID: " + friendsID);

                    for(int i = 0; i < friendsIdSameTest.size(); i++) {
                        String query5 = "SELECT Name FROM users WHERE id = " + friendsIdSameTest.get(i);
                        ResultSet rs5 = statement.executeQuery(query5);

                        while (rs5.next()) {
                            if(!friendsNameSameTest.contains(rs5.getString(1))){
                                friendsNameSameTest.add(rs5.getString(1));
                            }
                        }
                    }

                    System.out.println("SAME TEST FRIENDS NAME: " + friendsID);

                    for(int i = 0; i < friendsNameSameTest.size(); i++) {
                        friendsName.add(friendsNameSameTest.get(i));
                    }
                    System.out.println("FRIENDS ID: "+ friendsName);
                }
            } catch (SQLException e) {
                isSuccess = false;
                z = "Exceptions" + e;
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, friendsName);
            friendsName.add("My last test");
            spinnerFriends.setAdapter(spinnerAdapter);
        }
    }

    // BD LIGAÇÃO Search Current Test by User
    private class currentTestByUser extends AsyncTask<String,String,String> {
        boolean isSuccess = false;
        String z = "";
        String idUserLogged;
        String correctAns;
        String wrongAns;
        String time;
        String score;

        String correctAnsOther;
        String wrongAnsOther;
        String timeOther;
        String scoreOther;
        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    Toast.makeText(ResultsActivity.this, getResources().getString(R.string.InternetConnection), Toast.LENGTH_SHORT).show();
                } else {
                    String query1 = "SELECT id From users where users.Name='" + nameuserlogged + "'";
                    Statement statement = connectDB.createStatement();
                    ResultSet rs1 = statement.executeQuery(query1);

                    while (rs1.next()) {
                        idUserLogged = rs1.getString(1);
                    }

                    String query3 = "SELECT * FROM userresults WHERE IDUser = " + idUserLogged + " and IDSubject = " + temaID + " and Difficulty = " + difEsc + " Order by ID DESC";
                    ResultSet rs3 = statement.executeQuery(query3);

                    while (rs3.next()) {
                        correctAns = rs3.getString(5);
                        wrongAns = rs3.getString(6);;
                        time = rs3.getString(7);;
                        score = rs3.getString(4);;
                        break;
                    }

                    if(friendSelected == -1) {
                        int count = 0;
                        String query4 = "SELECT * FROM userresults WHERE IDUser = " + idUserLogged + " and IDSubject = " + temaID + " and Difficulty = " + difEsc + " Order by ID DESC";
                        ResultSet rs4 = statement.executeQuery(query4);

                        while (rs4.next()) {
                            if(count == 1) {
                                correctAnsOther = rs4.getString(5);
                                wrongAnsOther = rs4.getString(6);
                                timeOther = rs4.getString(7);
                                scoreOther = rs4.getString(4);
                            }
                            count++;
                        }
                    }
                    else {
                        String query4 = "SELECT * FROM userresults WHERE IDUser = " + friendsIdSameTest.get(friendSelected) + " and IDSubject = " + temaID + " and Difficulty = " + difEsc + " Order by ID DESC";
                        ResultSet rs4 = statement.executeQuery(query4);

                        while (rs4.next()) {
                            correctAnsOther = rs4.getString(5);
                            wrongAnsOther = rs4.getString(6);;
                            timeOther = rs4.getString(7);;
                            scoreOther = rs4.getString(4);;
                            break;
                        }
                    }
                }
            } catch (SQLException e) {
                isSuccess = false;
                z = "Exceptions" + e;
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            correctAnsMe.setText(correctAns);
            wrongAnsMe.setText(wrongAns);
            timeToSolveAnsMe.setText(time);
            finalScoreMe.setText(score);

            correctAnsO.setText(correctAnsOther);
            wrongAnsO.setText(wrongAnsOther);
            timeToSolveAnsO.setText(timeOther);
            finalScoreO.setText(scoreOther);


            // Compare Corret Answers
            if(Integer.compare((Integer.parseInt(correctAnsMe.getText().toString())),(Integer.parseInt(correctAnsO.getText().toString()))) > 0) {
                System.out.println("1 - 1");
                correctAnsMe.setTypeface(correctAnsMe.getTypeface(), Typeface.BOLD_ITALIC);
                correctAnsO.setTypeface(correctAnsO.getTypeface(), Typeface.NORMAL);
            } else if(Integer.compare((Integer.parseInt(correctAnsMe.getText().toString())),(Integer.parseInt(correctAnsO.getText().toString()))) == 0) {
                System.out.println("1 - 2");
                correctAnsMe.setTypeface(correctAnsMe.getTypeface(), Typeface.NORMAL);
                correctAnsO.setTypeface(correctAnsO.getTypeface(), Typeface.NORMAL);
            } else {
                System.out.println("1 - 3");
                correctAnsO.setTypeface(correctAnsO.getTypeface(), Typeface.BOLD_ITALIC);
                correctAnsMe.setTypeface(correctAnsMe.getTypeface(), Typeface.NORMAL);
            }

            // Compare Wrong Answers
            if(Integer.compare((Integer.parseInt(wrongAnsMe.getText().toString())),(Integer.parseInt(wrongAnsO.getText().toString()))) < 0) {
                System.out.println("2 - 1");
                wrongAnsMe.setTypeface(wrongAnsMe.getTypeface(), Typeface.BOLD_ITALIC);
                wrongAnsO.setTypeface(wrongAnsO.getTypeface(), Typeface.NORMAL);
            } else if(Integer.compare((Integer.parseInt(wrongAnsMe.getText().toString())),(Integer.parseInt(wrongAnsO.getText().toString()))) == 0) {
                System.out.println("2 - 2");
                wrongAnsMe.setTypeface(wrongAnsMe.getTypeface(), Typeface.NORMAL);
                wrongAnsO.setTypeface(wrongAnsO.getTypeface(), Typeface.NORMAL);
            } else {
                System.out.println("2 - 3");
                wrongAnsO.setTypeface(wrongAnsO.getTypeface(), Typeface.BOLD_ITALIC);
                wrongAnsMe.setTypeface(wrongAnsMe.getTypeface(), Typeface.NORMAL);
            }

            // Compare Time_To_Solve
            if(Integer.compare((Integer.parseInt(timeToSolveAnsMe.getText().toString())),(Integer.parseInt(timeToSolveAnsO.getText().toString()))) < 0) {
                System.out.println("3 - 1");
                timeToSolveAnsMe.setTypeface(timeToSolveAnsMe.getTypeface(), Typeface.BOLD_ITALIC);
                timeToSolveAnsO.setTypeface(timeToSolveAnsO.getTypeface(), Typeface.NORMAL);
            } else if(Integer.compare((Integer.parseInt(timeToSolveAnsMe.getText().toString())),(Integer.parseInt(timeToSolveAnsO.getText().toString()))) == 0) {
                System.out.println("3 - 2");
                timeToSolveAnsMe.setTypeface(timeToSolveAnsMe.getTypeface(), Typeface.NORMAL);
                timeToSolveAnsO.setTypeface(timeToSolveAnsO.getTypeface(), Typeface.NORMAL);
            } else {
                System.out.println("3 - 3");
                timeToSolveAnsMe.setTypeface(timeToSolveAnsMe.getTypeface(), Typeface.NORMAL);
                timeToSolveAnsO.setTypeface(timeToSolveAnsO.getTypeface(), Typeface.BOLD_ITALIC);
            }

            // Compare Final Score
            if(Integer.compare((Integer.parseInt(finalScoreMe.getText().toString())),(Integer.parseInt(finalScoreO.getText().toString()))) > 0) {
                System.out.println("4 - 1");
                finalScoreMe.setTypeface(finalScoreMe.getTypeface(), Typeface.BOLD_ITALIC);
                finalScoreO.setTypeface(finalScoreO.getTypeface(), Typeface.NORMAL);
                txtViewMe.setTypeface(txtViewMe.getTypeface(), Typeface.BOLD_ITALIC);
                otherFriend.setTypeface(otherFriend.getTypeface(), Typeface.NORMAL);
            } else if(Integer.compare((Integer.parseInt(finalScoreMe.getText().toString())),(Integer.parseInt(finalScoreO.getText().toString()))) == 0){
                System.out.println("4 - 2");
                finalScoreMe.setTypeface(finalScoreMe.getTypeface(), Typeface.NORMAL);
                txtViewMe.setTypeface(txtViewMe.getTypeface(), Typeface.NORMAL);
                finalScoreO.setTypeface(finalScoreO.getTypeface(), Typeface.NORMAL);
                otherFriend.setTypeface(otherFriend.getTypeface(), Typeface.NORMAL);
            } else {
                System.out.println("4 - 3");
                finalScoreO.setTypeface(finalScoreO.getTypeface(), Typeface.BOLD_ITALIC);
                finalScoreMe.setTypeface(finalScoreMe.getTypeface(), Typeface.NORMAL);
                otherFriend.setTypeface(otherFriend.getTypeface(), Typeface.BOLD_ITALIC);
                txtViewMe.setTypeface(txtViewMe.getTypeface(), Typeface.NORMAL);
            }

            if (friendSelected == -1) {
                otherFriend.setText("My last test");
            } else {
                otherFriend.setText(friendsName.get(friendSelected));
            }
        }
    }
}