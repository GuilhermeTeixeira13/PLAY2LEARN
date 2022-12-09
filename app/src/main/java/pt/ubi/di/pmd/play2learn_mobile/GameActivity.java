package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuCompat;

public class GameActivity extends AppCompatActivity {

    String nameuserlogged;
    int difEscolhida;
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

        Intent iCameFromMainNameUser = getIntent();
        nameuserlogged = iCameFromMainNameUser.getStringExtra("userlogged");
        System.out.println("game activy NAME: " + nameuserlogged);

        difEscolhida = iCameFromMainNameUser.getIntExtra("difEsc", -1);
        System.out.println("Dif: " + difEscolhida);
    }

    public void GoToResultsPage(View v){
        Intent myIntent = new Intent(this, ResultsActivity.class);
        myIntent.putExtra("ulogged", nameuserlogged);
        myIntent.putExtra("dif", difEscolhida);
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
}