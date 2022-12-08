package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
<<<<<<< Updated upstream
import android.widget.Adapter;
import android.widget.AdapterView;
=======
>>>>>>> Stashed changes
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuCompat;

<<<<<<< Updated upstream
public class ResultsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
=======
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {
>>>>>>> Stashed changes

    private Spinner spinnerFriends;

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

<<<<<<< Updated upstream

        // Spinner
        Spinner spinner = findViewById(R.id.spinnerCompareWith);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.friendsResult, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
=======
        List<String> nameFriendsAssocieatedTest = new ArrayList<>();

        // Spinner Friends Test
        spinnerFriends = findViewById(R.id.spinnerCompareWith);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, );
>>>>>>> Stashed changes
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


<<<<<<< Updated upstream
    // Spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

=======
    // BD LIGAÇÃO FRIENDS ASSOCIATED WITH TEST ID
    private class friendsAssTest extends AsyncTask<String,String,String> {
        boolean isSuccess = false;
        String z = "";
        String idUserLogged;
        ArrayList<String> friendsID = new ArrayList<>();
        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    Toast.makeText(ResultsActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                } else {
                    String query1 = "SELECT id From users where users.Name='" + userLogged + "'";
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

                    
                }
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
>>>>>>> Stashed changes
    }
}