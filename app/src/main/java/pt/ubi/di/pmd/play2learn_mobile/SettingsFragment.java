package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
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

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class SettingsFragment extends Fragment {
    Button btnPT;
    Button btnEN;
    Button btnDel;
    Button btnSave;

    EditText eml;
    EditText pass;

    String username;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Change toolbar title
        getActivity().setTitle(getResources().getString(R.string.SettingsFragment));

        btnPT = view.findViewById(R.id.changePT);
        btnEN = view.findViewById(R.id.changeENG);
        btnDel = view.findViewById(R.id.BtnDelUser);
        btnSave = view.findViewById(R.id.BtnSave);

        eml = view.findViewById(R.id.edTextEmail);
        pass = view.findViewById(R.id.edTextPass);

        SharedPreferences sp = getContext().getSharedPreferences("userLogged", Context.MODE_PRIVATE);
        if (sp.contains("uname")) {
            username = sp.getString("uname", "");
        }

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment.Updatedata updatedata = new SettingsFragment.Updatedata();
                updatedata.execute();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment.Deluser deluser = new SettingsFragment.Deluser();
                deluser.execute();
            }
        });

        return view;
    }

    private class Updatedata extends AsyncTask<String,String,String>{
        String usereml = eml.getText().toString();
        String userpass = pass.getText().toString();
        String exception = "";

        @Override
        protected String doInBackground(String... strings) {
            if (usereml.isEmpty() || userpass.isEmpty()){
                Toast.makeText(getContext(), getResources().getString(R.string.AllFieldsRequired), Toast.LENGTH_SHORT).show();
            }else {
                try {
                    P2L_DbHelper connectNow = new P2L_DbHelper();
                    Connection connectDB = connectNow.getConnection();

                    if (connectDB == null) {
                        Toast.makeText(getContext(), getResources().getString(R.string.InternetConnection), Toast.LENGTH_SHORT).show();
                    } else {
                        String query = "UPDATE users SET Email='" + usereml + "', Password ='"+userpass+"' WHERE Name = '" + username + "'";

                        Statement statement = connectDB.createStatement();
                        statement.executeUpdate(query);

                        Toast.makeText(getContext(), getResources().getString(R.string.UpdateSuccessfull), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    exception = getResources().getString(R.string.Exceptions) + e;
                }
            }
            return exception;
        }
    }

    private class Deluser extends AsyncTask<String,String,String> {
        String ueml,pss;
        String usereml = eml.getText().toString();
        String userpass = pass.getText().toString();
        String exception = "";
        boolean isSuccess = false;

        @Override
        protected String doInBackground(String... strings) {
            if (usereml.isEmpty() || userpass.isEmpty()){
                Toast.makeText(getContext(), getResources().getString(R.string.AllFieldsRequired), Toast.LENGTH_SHORT).show();
            }else {
                try {
                    P2L_DbHelper connectNow = new P2L_DbHelper();
                    Connection connectDB = connectNow.getConnection();

                    if (connectDB== null){
                        Toast.makeText(getContext(), getResources().getString(R.string.InternetConnection), Toast.LENGTH_SHORT).show();
                    }else {
                        String query = "SELECT * FROM users WHERE Email='"+usereml+"' AND Name='"+username+"' AND Password='"+userpass+"'";

                        Statement statement = connectDB.createStatement();
                        ResultSet rs = statement.executeQuery(query);

                        while (rs.next()){
                            ueml = rs.getString(3);
                            pss = rs.getString(4);

                            if((ueml.equals(usereml)) && pss.equals(userpass)){
                                String query1 = "DELETE FROM users WHERE Email='" + usereml + "' AND Password='" + userpass + "'";

                                Statement statement1 = connectDB.createStatement();
                                statement1.executeUpdate(query1);
                                isSuccess = true;
                            }else {
                                isSuccess = false;
                                Toast.makeText(getContext(), getResources().getString(R.string.EmailPassIncorrect), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                } catch (SQLException e) {
                    isSuccess = false;
                    exception = getResources().getString(R.string.Exceptions) + e;
                }catch (Exception e) {
                    isSuccess = false;
                    e.printStackTrace();
                    exception = getResources().getString(R.string.Exceptions) + e;
                }

            }
            return exception;
        }

        @Override
        protected void onPostExecute(String s) {
            if (isSuccess){
                SharedPreferences sp = getActivity().getSharedPreferences("userLogged", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("uname");
                editor.apply();

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
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
