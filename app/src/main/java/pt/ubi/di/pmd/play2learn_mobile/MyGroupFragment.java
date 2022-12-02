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
import java.util.ArrayList;

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
                if (edittxtNameFriend.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "You need to specify your friend's name", Toast.LENGTH_SHORT).show();
                }
                else {
                    MyGroupFragment.AddFriendToGroup addingFriends = new MyGroupFragment.AddFriendToGroup();
                    addingFriends.execute();
                }
            }
        });



        // Change toolbar title
        getActivity().setTitle(getResources().getString(R.string.MyGroupFragment));

        return view;
    }

    private class AddFriendToGroup extends AsyncTask<String,String,String> {
        ArrayList<String> friendsID = new ArrayList<>();
        ArrayList<String> friendsName = new ArrayList<>();
        String idUserLogged;
        String idUserAmigo;
        String nomefriend = edittxtNameFriend.getText().toString();
        String idUserAdicionar = "";
        String z = "";
        boolean isSuccess = false;

        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    z = "Please check your internet connection";
                } else {

                    // Obter lista (Name) de amigos do user logado
                    String query = "SELECT id From users where users.Name='" + userLogged + "'";

                    Statement statement = connectDB.createStatement();

                    ResultSet rs = statement.executeQuery(query);

                    while (rs.next()) {
                        idUserLogged = rs.getString(1);
                        break;
                    }

                    System.out.println("ID DO USER LOGGED: " + idUserLogged);

                    String query2 = "SELECT IDFriend FROM userfriends WHERE IDUser = " + idUserLogged;
                    ResultSet rs2 = statement.executeQuery(query2);

                    while (rs2.next()) {
                        friendsID.add(rs2.getString(1));
                    }

                    System.out.println("FRIENDS ID: " + friendsID);

                    for (int i = 0; i < friendsID.size(); i++) {
                        String query3 = "SELECT Name FROM users WHERE id ='" + friendsID.get(i) + "'";
                        ResultSet rs3 = statement.executeQuery(query3);

                        while (rs3.next()) {
                            friendsName.add(rs3.getString(1));
                        }
                    }

                    System.out.println("FRIENDS Name: " + friendsName);


                    // Verifica se o nome do amigo em questão já pertence à sua lista de amigos
                    if (friendsName.contains(nomefriend)) {
                        z = "Ocorreu um erro, verifique se o amigo já está na sua lista de amigos!";
                        task.c
                    } else {
                        // ID do amigo a adicionar
                        isSuccess = true;
                        String query4 = "SELECT id From users where users.Name='" + nomefriend + "'";
                        ResultSet rs4 = statement.executeQuery(query4);

                        while (rs4.next()) {
                            idUserAmigo= rs4.getString(1);
                        }

                        // Inserir na BD
                        String query5 = "INSERT INTO userfriends values ('"+idUserLogged+"','"+idUserAmigo+"')";
                        statement.executeUpdate(query5);
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
            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT);
            if (isSuccess){
                System.out.println("ADICIONADO AMIGO COM ID: " + idUserAmigo + " COM O NOME: " + nomefriend);
            }
        }
    }
}
