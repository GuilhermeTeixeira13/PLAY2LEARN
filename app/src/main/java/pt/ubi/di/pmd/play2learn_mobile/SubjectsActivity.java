package pt.ubi.di.pmd.play2learn_mobile;

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
import java.util.List;

public class SubjectsActivity extends AppCompatActivity implements CustomSpinnerDif.OnSpinnerEventsListener{

    // Spinner Variables
    private CustomSpinnerDif spinner_dif;
    private ListView listView;

    private DifficultyAdapter adapter;

    private String escolhaDifUser;
    private String temaEscolhido;

    private List<String> list;

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

        // Spinner
        spinner_dif = findViewById(R.id.spinnerDifficulty);
        spinner_dif.setSpinnerEventsListener(this);
        adapter = new DifficultyAdapter(    SubjectsActivity.this, DataDifficulty.getDifficultyList());
        spinner_dif.setAdapter(adapter);
        escolhaDifUser = spinner_dif.getSelectedItem().toString();

        // ListView
        listView = findViewById(R.id.listview);
        list = new ArrayList<>();

        // BD - Subjects
        acessSubjects accSubj = new acessSubjects();
        accSubj.execute();
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                temaEscolhido = list.get(i);
                System.out.println(temaEscolhido);
            }
        });
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
                    Toast.makeText(SubjectsActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getBaseContext(),""+z,Toast.LENGTH_LONG).show();

        }
    }

    public void GoToGamePage(View v){
        Intent myIntent = new Intent(this, GameActivity.class);
        startActivity(myIntent);
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

    // Spinner Methods
    @Override
    public void onPopupWindowOpened(Spinner spinner) {

    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {

    }
}