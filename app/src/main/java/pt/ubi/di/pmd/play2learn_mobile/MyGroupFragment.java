package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyGroupFragment extends Fragment {
    ImageButton btnAddFriend;
    EditText edittxtNameFriend;
    String userLogged = "";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        View view = inflater.inflate(R.layout.fragment_mygroup, container, false);

        btnAddFriend = view.findViewById(R.id.btn_add_player);
        edittxtNameFriend = view.findViewById(R.id.add_player_name);

        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseActivity BaseActivity = (BaseActivity) getActivity();
                userLogged = BaseActivity.getUserLogged();
                System.out.println("USER LOGGED: " + userLogged);
                MyGroupFragment.addFriendToGroup addingFriends = new MyGroupFragment.addFriendToGroup();
                addingFriends.execute();
            }
        });



        // Change toolbar title
        getActivity().setTitle(getResources().getString(R.string.MyGroupFragment));

        return view;
    }

    private class addFriendToGroup extends AsyncTask<String,String,String> {
        String idUserLogged;
        String nomefriend = edittxtNameFriend.getText().toString();
        String z = "";
        boolean isSuccess = false;

        @Override
        protected String doInBackground(String... strings) {
            if (nomefriend.isEmpty()){
                z= "You need to specify our name's friend";
            }else {
                try {
                    P2L_DbHelper connectNow = new P2L_DbHelper();
                    Connection connectDB = connectNow.getConnection();

                    if (connectDB== null){
                        z = "Please check your internet connection";
                    }else {
                        String query = "SELECT id From users where users.Name='"+userLogged;

                        Statement statement = connectDB.createStatement();

                        ResultSet rs = statement.executeQuery(query);

                        while (rs.next()){
                            idUserLogged = rs.getString(1);
                            break;

                            if(nameuserlogged.equals(nameuserlogged) && nameuserlogged.equals(nameuserlogged)){
                                isSuccess = true;
                                z = "Login successfull";
                                System.out.println("Login successfull");
                            }else {
                                isSuccess = false;
                                System.out.println("Login NOT successfull");
                            }
                        }

                    }

                } catch (SQLException e) {
                    isSuccess = false;
                    z = "Exceptions"+e;
                }

            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getMyGR(),""+z,Toast.LENGTH_LONG).show();

            if (isSuccess){
                Intent intent = new Intent(BaseActivity.this, BaseActivity.class);
                intent.putExtra("name", nameuserlogged);
                startActivity(intent);
            }
        }
    }
}
