package pt.ubi.di.pmd.play2learn_mobile;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class SettingsFragment extends Fragment {
    public BaseActivity baseacty;
    EditText email;
    EditText password;
    Button save;
    P2L_DbHelper connectionhelper;
    String userLogged = "";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        BaseActivity BaseActivity = (BaseActivity) getActivity();
        userLogged = BaseActivity.getUserLogged();
        System.out.println(userLogged);
        // Change toolbar title
        getActivity().setTitle(getResources().getString(R.string.SettingsFragment));
        email = view.findViewById(R.id.edTextName);
        password = view.findViewById(R.id.edTextNameBibio);
        save = view.findViewById(R.id.BtnSave);
        connectionhelper = new P2L_DbHelper();
        try {
            seeEmail(view);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    save(view);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
    public void seeEmail(View v) throws SQLException {
        getemail getemail = new getemail();
        getemail.execute();

    }
    public void save(View v) throws SQLException {
        savereg savereg = new savereg();
        savereg.execute();

    }
    private class savereg extends AsyncTask<String,String,String>{
        String eml = email.getText().toString();
        String pass= password.getText().toString();
        String z;
        @Override
        protected String doInBackground(String... strings) {
            if (pass.isEmpty()){

                try {
                    P2L_DbHelper connectNow = new P2L_DbHelper();
                    Connection connectDB = connectNow.getConnection();

                    if (connectDB == null){
                        z="Please check your internet connection";
                    }else {
                        String query = "UPDATE users Set Email='"+eml+"'where Name = '"+userLogged+"'";

                        Statement statement = connectDB.createStatement();
                        statement.executeUpdate(query);

                        z="Update is successfull";
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }else {
                try {
                    P2L_DbHelper connectNow = new P2L_DbHelper();
                    Connection connectDB = connectNow.getConnection();

                    if (connectDB == null){
                        z="Please check your internet connection";
                    }else {
                        String query = "UPDATE users Set Email='"+eml+"',Password='"+pass+"' where Name = '"+userLogged+"'";

                        Statement statement = connectDB.createStatement();
                        statement.executeUpdate(query);

                        z="Update is successfull";
                    }
                } catch (Exception e) {
                    z="error";
                }
            }
            return z;
        }
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(),""+z,Toast.LENGTH_LONG).show();
        }
    }

    private class getemail extends AsyncTask<String,String,String> {
        String em;
        String z = "";
        boolean isSuccess = false;

        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();
                System.out.println(connectDB);

                if (connectDB == null) {
                    z = "Please check your internet connection";
                } else {
                    String query = "select Email from users where Name='" + userLogged + "'";

                    Statement statement = connectDB.createStatement();

                    ResultSet rs = statement.executeQuery(query);

                    while (rs.next()) {
                        em = rs.getString(1);
                        System.out.println(em);
                        isSuccess=true;

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
            Toast.makeText(getContext(),""+z,Toast.LENGTH_LONG).show();

            if (isSuccess){
                email.setText(em);
            }
        }
    }
}
