package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogoutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        // Change toolbar title
        getActivity().setTitle(getResources().getString(R.string.LogoutFragment));

        SharedPreferences sp = getActivity().getSharedPreferences("userLogged", Context.MODE_PRIVATE);
        if (sp.contains("uname")){
            SharedPreferences.Editor editor = sp.edit();
            editor.remove("uname");
            editor.apply();
            System.out.println("removi o user da shp");

            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);

        }

        return view;
    }
}
