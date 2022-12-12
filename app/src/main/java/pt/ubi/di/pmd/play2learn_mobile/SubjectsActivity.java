package pt.ubi.di.pmd.play2learn_mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubjectsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Spinner Variables
    private CustomSpinnerDif spinner_dif;
    private ListView listView;


    private DifficultyAdapter adapter;

    private int escolhaDifUser;
    private String temaEscolhido;
    private String temaEscolhidoId;

    private List<String> list;
    private List<String> listId;

    String nameuserlogged;

    String[] numDif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Change toolbar title
        setTitle(getResources().getString(R.string.GameActivity));

        // Check flag and initialize objects
        Intent intent = getIntent();
        String checkFlag= intent.getStringExtra("flag");
        if(checkFlag.equals("FROM_BASE")){
            nameuserlogged = intent.getStringExtra("name");
            System.out.println("name nos subjects --> "+nameuserlogged);
        }

        // Spinner
        spinner_dif = findViewById(R.id.spinnerDifficulty);
        adapter = new DifficultyAdapter(    SubjectsActivity.this, DataDifficulty.getDifficultyList());
        spinner_dif.setAdapter(adapter);

        spinner_dif.setOnItemSelectedListener(this);

        // ListView
        listView = findViewById(R.id.listview);
        list = new ArrayList<>();
        listId = new ArrayList<>();

        // BD - Subjects
        acessSubjects accSubj = new acessSubjects();
        accSubj.execute();
        temaEscolhido = "";
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                temaEscolhido = list.get(i);
                temaEscolhidoId = listId.get(i);
            }
        });

        numDif = new String[3];
        numDif[0] = getResources().getString(R.string.difEasy);
        numDif[1] = getResources().getString(R.string.difMedium);
        numDif[2] = getResources().getString(R.string.difHard);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == 0) {
            escolhaDifUser = 1;
        }
        if(i == 1) {
            escolhaDifUser = 2;
        }
        if(i == 2) {
            escolhaDifUser = 3;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // BD LIGAÇÃO TEMAS SUBJECTS
    private class acessSubjects extends AsyncTask<String,String,String> {
        boolean isSuccess = false;
        String z = "";
        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            final Toast toast = Toast.makeText(SubjectsActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                } else {
                    String query = "select Name from subjects";

                    Statement statement = connectDB.createStatement();

                    ResultSet rs = statement.executeQuery(query);
                    while (rs.next()) {
                        list.add(rs.getString(1));
                    }

                    String query1 = "select Id from subjects";
                    ResultSet rs1 = statement.executeQuery(query1);
                    while (rs1.next()) {
                        listId.add(rs1.getString(1));
                    }
                }
                System.out.println(list);
            } catch (SQLException e) {
                isSuccess = false;
                z = "Exceptions" + e;
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
            listView.setAdapter(arrayAdapter);
        }
    }

    public void GoToGamePage(View v){
        if(temaEscolhido != "") {
            Intent goToGameActivity = new Intent(this, GameActivity.class);
            goToGameActivity.putExtra("flag", "FROM_SUBJECTS");
            goToGameActivity.putExtra("name", nameuserlogged);
            goToGameActivity.putExtra("subject", temaEscolhido);
            goToGameActivity.putExtra("difficulty", escolhaDifUser);
            startActivity(goToGameActivity);
            overridePendingTransition(0, 0);
        }
        else {
            Toast.makeText(getApplicationContext(), "Selecione um tema antes de começar a jogar", Toast.LENGTH_SHORT).show();
        }
    }

    public void GoToBasePage(View v){
        Intent goToBaseActivity = new Intent(this, BaseActivity.class);
        goToBaseActivity.putExtra("flag", "FROM_SUBJECTS");
        goToBaseActivity.putExtra("name", nameuserlogged);
        startActivity(goToBaseActivity);
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
                new AlertDialog.Builder(SubjectsActivity.this)
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

}