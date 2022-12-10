package pt.ubi.di.pmd.play2learn_mobile;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    int friendSelected = 0;


    private Spinner spinnerFriends;
    TextView correctAnsMe;
    TextView wrongAnsMe;
    TextView timeToSolveAnsMe;
    TextView finalScoreMe;

    TextView correctAnsO;
    TextView wrongAnsO;
    TextView timeToSolveAnsO;
    TextView finalScoreO;



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

        Intent iCameFromMainNameUser = getIntent();
        nameuserlogged = iCameFromMainNameUser.getStringExtra("ulogged");
        difEsc = iCameFromMainNameUser.getIntExtra("dif", -1);
        temaID = iCameFromMainNameUser.getStringExtra("temaID");

        System.out.println("USER LOGADO: "+ nameuserlogged + "  DIF: "+ difEsc + "TemaID: " + temaID);

        ResultsActivity.friendsAssTest friendsTest = new ResultsActivity.friendsAssTest();
        friendsTest.execute();

        spinnerFriends.setOnItemSelectedListener(this);

        ResultsActivity.currentTestByUser currentTest = new ResultsActivity.currentTestByUser();
        currentTest.execute();

        correctAnsMe = findViewById(R.id.correctAnsMe);
        wrongAnsMe = findViewById(R.id.wrongAnsMe);
        timeToSolveAnsMe = findViewById(R.id.timeToSolveMe);
        finalScoreMe = findViewById(R.id.finalScoreMe);

        correctAnsO = findViewById(R.id.correctAnsOther);
        wrongAnsO = findViewById(R.id.wrongAnsOther);
        timeToSolveAnsO = findViewById(R.id.timeToSolveOther);
        finalScoreO = findViewById(R.id.finalScoreOther);


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
        friendSelected = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                    Toast.makeText(ResultsActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                } else {
                    String query1 = "SELECT id From users where users.Name='" + nameuserlogged + "'";
                    Statement statement = connectDB.createStatement();
                    ResultSet rs1 = statement.executeQuery(query1);

                    while (rs1.next()) {
                        idUserLogged = rs1.getString(1);
                    }

                    String query2 = "SELECT IDFriend FROM userfriends WHERE IDUser = " + idUserLogged;
                    ResultSet rs2 = statement.executeQuery(query2);

                    while (rs2.next()) {
                        friendsID.add(rs2.getString(1));
                    }

                    for(int i = 0; i < friendsID.size(); i++) {
                        String query3 = "SELECT id FROM userresults WHERE IDUser =" + friendsID.get(i) + " and IDSubject =" + temaID + " and Difficulty = " + difEsc;
                        ResultSet rs3 = statement.executeQuery(query3);

                        while (rs3.next()) {
                            testID.add(rs3.getString(1));
                        }
                    }

                    for(int i = 0; i < testID.size(); i++) {
                        String query4 = "SELECT idUser FROM userresults WHERE id = " + testID.get(i);
                        ResultSet rs4 = statement.executeQuery(query4);

                        while (rs4.next()) {
                            friendsIdSameTest.add(rs4.getString(1));
                        }
                    }

                    for(int i = 0; i < friendsIdSameTest.size(); i++) {
                        String query5 = "SELECT Name FROM users WHERE id = " + friendsIdSameTest.get(i);
                        ResultSet rs5 = statement.executeQuery(query5);

                        while (rs5.next()) {
                            friendsNameSameTest.add(rs5.getString(1));
                        }
                    }
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
                    Toast.makeText(ResultsActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
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
                        score = rs3.getString(8);;
                        break;
                    }

                    String query4 = "SELECT * FROM userresults WHERE IDUser = " + friendsIdSameTest.get(friendSelected) + " and IDSubject = " + temaID + " and Difficulty = " + difEsc + " Order by ID DESC";
                    ResultSet rs4 = statement.executeQuery(query4);

                    while (rs4.next()) {
                        correctAnsOther = rs4.getString(5);
                        wrongAnsOther = rs4.getString(6);;
                        timeOther = rs4.getString(7);;
                        scoreOther = rs4.getString(8);;
                        break;
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
        }
    }
}