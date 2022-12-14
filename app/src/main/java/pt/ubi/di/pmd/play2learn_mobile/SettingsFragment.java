package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class SettingsFragment extends Fragment {
    Button btnPT;
    Button btnEN;
    Button btnDel;

    EditText eml;
    EditText pass;

    static boolean isInit = true;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Change toolbar title
        getActivity().setTitle(getResources().getString(R.string.SettingsFragment));

        btnPT = view.findViewById(R.id.changePT);
        btnEN = view.findViewById(R.id.changeENG);
        btnDel = view.findViewById(R.id.BtnDelUser);

        eml = view.findViewById(R.id.edTextEmail);
        pass = view.findViewById(R.id.edTextPass);

        // Change toolbar title
        getActivity().setTitle(getResources().getString(R.string.app_name));

        // Change languages by button clicks
        btnPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage("pt");
            }
        });

        btnEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage("en");
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.Deluser deluser = new MainActivity.Deluser();
                deluser.execute();
            }
        });

        return view;
    }

    private class Deluser extends AsyncTask<String,String,String> {
        String ueml,pss;
        String usereml = eml.getText().toString();
        String userpass = pass.getText().toString();
        String z = "";
        boolean isSuccess = false;

        @Override
        protected String doInBackground(String... strings) {
            if (usereml.isEmpty() || userpass.isEmpty()){
                z= "All fields Required";
            }else {
                try {
                    P2L_DbHelper connectNow = new P2L_DbHelper();
                    Connection connectDB = connectNow.getConnection();
                    System.out.println(connectDB);

                    if (connectDB== null){
                        z = "Please check your internet connection";
                    }else {
                        String query = "select * from users where Email='"+usereml+"' and Password='"+userpass+"' ";

                        Statement statement = connectDB.createStatement();

                        ResultSet rs = statement.executeQuery(query);
                        System.out.println(rs);

                        while (rs.next()){
                            System.out.println("entrei aqui1");
                            ueml = rs.getString(3);
                            pss = rs.getString(4);

                            if((ueml.equals(usereml)) && pss.equals(userpass)){
                                isSuccess = true;
                            }else {
                                isSuccess = false;
                                z = "email or password does not exist";
                            }
                        }

                    }

                } catch (SQLException e) {
                    isSuccess = false;
                    z = "Exceptions"+e;
                }catch (Exception e) {
                    isSuccess = false;
                    e.printStackTrace();
                    z = "Exceptions"+e;
                }

            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(),""+z,Toast.LENGTH_LONG).show();
            //System.out.println("cheguei aqui");

            if (isSuccess){

            }
        }
    }

    // When the language is changed, it is saved using Shared Preferences
    public void saveLanguage(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getActivity().getSharedPreferences("CommonPrefs", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }

    // Change the app language to other one
    public void changeLanguage(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        getActivity().getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
        saveLanguage(lang);
        getActivity().recreate();
    }
}
