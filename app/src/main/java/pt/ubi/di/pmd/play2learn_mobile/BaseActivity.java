package pt.ubi.di.pmd.play2learn_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    ImageView ProfileImage;
    TextView ProfileName;
    TextView ProfileEmail;
    private String nameuserlogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Change toolbar title
        setTitle(getResources().getString(R.string.BaseActivity));

        // Side menu
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.side_menu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set user profile Image and user name
        ProfileImage = navigationView.getHeaderView(0).findViewById(R.id.ProfileImage);
        ProfileName = navigationView.getHeaderView(0).findViewById(R.id.NavUserName);
        ProfileEmail = navigationView.getHeaderView(0).findViewById(R.id.NavUserEmail);

        // The main activity will start on the MainPageFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new GameFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_game);
        }

        // Check sharedPreferences and initialize objects
        SharedPreferences sp = getSharedPreferences("userLogged", MODE_PRIVATE);
        if (sp.contains("uname")) {
            nameuserlogged = sp.getString("uname", "");
            ProfileName.setText(nameuserlogged);

            Showeml showeml = new Showeml();
            showeml.execute();
        }
    }

    private class Showeml extends AsyncTask<String,String,String> {
        String eml;
        String exception;
        boolean isSuccess = false;

        @Override
        protected String doInBackground(String... strings) {
                try {
                    P2L_DbHelper connectNow = new P2L_DbHelper();
                    Connection connectDB = connectNow.getConnection();

                    if (connectDB == null) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                final Toast toast = Toast.makeText(BaseActivity.this, getResources().getString(R.string.InternetConnection), Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    } else {
                        String query = "SELECT * FROM users WHERE Name='" + nameuserlogged + "'";

                        Statement statement = connectDB.createStatement();
                        ResultSet rs = statement.executeQuery(query);

                        while (rs.next()) {
                            eml = rs.getString(3);
                            isSuccess = true;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            return eml;
        }

        @Override
        protected void onPostExecute(String s) {
            if (isSuccess){
                ProfileEmail.setText(eml);
            }
        }
    }

    public void GoToSubjectsPage(View v){
        Intent goToSubjectsActivity = new Intent(this, SubjectsActivity.class);
        goToSubjectsActivity.putExtra("flag", "FROM_BASE");
        startActivity(goToSubjectsActivity);
    }

    // Inflating the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_game:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GameFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;
            case R.id.nav_mygroup:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyGroupFragment()).commit();
                break;
            case R.id.nav_logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LogoutFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}