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
        String idUserAdicionar = "-1";
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
                    // Verifica se o nome escrito pertence à BD
                    String query = "SELECT id From users where users.Name='" + nomefriend + "'";

                    Statement statement = connectDB.createStatement();

                    ResultSet rs = statement.executeQuery(query);

                    while (rs.next()) {
                        idUserAdicionar = rs.getString(1);
                        break;
                    }

                    // Não existe -> Finalização da Thread , Contrário -> Continuação da Pesquisa
                    if(idUserAdicionar.equals("-1")) {
                        z = "Não existe nenhum user com esse nome!";
                        System.out.println("NÃO EXISTE NENHUM USER COM ESSE NOME");
                        return z;
                    }
                    else {
                        // Obter lista (Name) de amigos do user logado e se o nomefriend já lhe pertence
                        String query1 = "SELECT id From users where users.Name='" + userLogged + "'";

                        ResultSet rs1 = statement.executeQuery(query1);

                        while (rs1.next()) {
                            idUserLogged = rs1.getString(1);
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

                        if (friendsName.contains(nomefriend)) {
                            System.out.println("JÁ EXISTE NA SEU GRUPO DE AMIGOS");
                            z = "Esse user já está no grupo de amigos do user";
                            return z;
                        }
                        else {
                            isSuccess = true;
                            String query4 = "SELECT id From users where users.Name='" + nomefriend + "'";
                            ResultSet rs4 = statement.executeQuery(query4);

                            while (rs4.next()) {
                                idUserAmigo= rs4.getString(1);
                            }
                            System.out.println("ATÉ AQUI ESTÁ BOM");
                            // Inserir na BD
                            String query5 = "insert into userfriends (IDUser, IDFriend) values(" + idUserLogged + ", " + idUserAmigo + ")";
                            statement.executeUpdate(query5);
                        }
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
            if (isSuccess){
                s = "O user " + nomefriend + " foi adicionado com sucesso";
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
