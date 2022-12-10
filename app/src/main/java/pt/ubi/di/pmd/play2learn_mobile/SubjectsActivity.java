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

public class SubjectsActivity extends AppCompatActivity implements CustomSpinnerDif.OnSpinnerEventsListener{

    // Spinner Variables
    private CustomSpinnerDif spinner_dif;
    private ListView listView;

    private DifficultyAdapter adapter;

    private String escolhaDifUser;
    private String temaEscolhido = "";

    private List<String> list;

    private String nameuserlogged;

    int count = 0;

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
        }

        // Spinner
        spinner_dif = findViewById(R.id.spinnerDifficulty);
        spinner_dif.setSpinnerEventsListener(this);
        adapter = new DifficultyAdapter(    SubjectsActivity.this, DataDifficulty.getDifficultyList());
        spinner_dif.setAdapter(adapter);

        // ListView
        listView = findViewById(R.id.listview);
        list = new ArrayList<>();

        // BD - Subjects
        acessSubjects accSubj = new acessSubjects();
        accSubj.execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                temaEscolhido = list.get(i);
                System.out.println(temaEscolhido);
            }
        });

        numDif = new String[3];
        numDif[0] = getResources().getString(R.string.difEasy);
        numDif[1] = getResources().getString(R.string.difMedium);
        numDif[2] = getResources().getString(R.string.difHard);
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
            escolhaDifUser = spinner_dif.getSelectedItem().toString();
            goToGameActivity.putExtra("difficulty", Integer.parseInt(escolhaDifUser)+1);
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

    // Spinner Methods
    @Override
    public void onPopupWindowOpened(Spinner spinner) {

    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {

    }
}