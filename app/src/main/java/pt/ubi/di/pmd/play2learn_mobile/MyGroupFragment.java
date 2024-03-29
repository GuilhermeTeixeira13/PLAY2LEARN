package pt.ubi.di.pmd.play2learn_mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyGroupFragment extends Fragment {
    ListView listView;
    ImageButton btnAddFriend;
    EditText edittxtNameFriend;
    String userLogged = "";
    List<String> listaAmigos;
    String itemSelecionadoName = "";
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        view = inflater.inflate(R.layout.fragment_mygroup, container, false);

        btnAddFriend = view.findViewById(R.id.btn_add_player);
        edittxtNameFriend = view.findViewById(R.id.add_player_name);

        SharedPreferences sp = getContext().getSharedPreferences("userLogged", Context.MODE_PRIVATE);
        if (sp.contains("uname")) {
            userLogged = sp.getString("uname", "");
        }

        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edittxtNameFriend.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.SpecFriendName), Toast.LENGTH_SHORT).show();
                }
                else {
                    MyGroupFragment.AddFriendToGroup addingFriends = new MyGroupFragment.AddFriendToGroup();
                    addingFriends.execute();
                }
            }
        });

        // ListView
        listView = view.findViewById(R.id.listview);
        listView.setLongClickable(true);
        listaAmigos = new ArrayList<>();
        // BD - Subjects
        acessListFriends accF = new acessListFriends();
        accF.execute();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int il, long l) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());

                // Title
                alertDialogBuilder.setTitle(getResources().getString(R.string.Alert));

                alertDialogBuilder.setMessage(getResources().getString(R.string.Eliminate))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.YES), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent iResult = new Intent();
                                itemSelecionadoName = listView.getItemAtPosition(il).toString();
                                deleteFriend delF = new deleteFriend();
                                delF.execute();
                            }
                        }).setNegativeButton(getResources().getString(R.string.NO), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                alertDialogBuilder.show();
                return false;
            }
        });

        // Change toolbar title
        getActivity().setTitle(getResources().getString(R.string.MyGroupFragment));

        return view;
    }


    public void onRefreshList() {
        // ListView
        listaAmigos = new ArrayList<>();
        // BD - Subjects
        acessListFriends accF = new acessListFriends();
        accF.execute();
    }

    private class AddFriendToGroup extends AsyncTask<String,String,String> {
        ArrayList<String> friendsID = new ArrayList<>();
        ArrayList<String> friendsName = new ArrayList<>();
        String idUserLogged;
        String nomefriend = edittxtNameFriend.getText().toString();
        String idUserAdicionar = "-1";
        String exception = "";
        boolean isSuccess = false;


        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    exception = getResources().getString(R.string.InternetConnection);
                } else {
                    // Verifica se o nome escrito pertence à BD
                    String query = "SELECT id FROM users WHERE users.Name='" + nomefriend + "'";

                    Statement statement = connectDB.createStatement();
                    ResultSet rs = statement.executeQuery(query);

                    while (rs.next()) {
                        idUserAdicionar = rs.getString(1);
                        break;
                    }

                    // Não existe -> Finalização da Thread , Contrário -> Continuação da Pesquisa
                    if(idUserAdicionar.equals("-1")) {
                        exception = getResources().getString(R.string.UserNotExist);
                        return exception;
                    }
                    else {
                        // Obter lista (Name) de amigos do user logado e se o nomefriend já lhe pertence
                        String query1 = "SELECT id FROM users WHERE users.Name='" + userLogged + "'";

                        ResultSet rs1 = statement.executeQuery(query1);

                        while (rs1.next()) {
                            idUserLogged = rs1.getString(1);
                            break;
                        }

                        String query2 = "SELECT IDFriend FROM userfriends WHERE IDUser = " + idUserLogged;
                        ResultSet rs2 = statement.executeQuery(query2);

                        while (rs2.next()) {
                            friendsID.add(rs2.getString(1));
                        }


                        for (int i = 0; i < friendsID.size(); i++) {
                            String query3 = "SELECT Name FROM users WHERE id ='" + friendsID.get(i) + "'";
                            ResultSet rs3 = statement.executeQuery(query3);

                            while (rs3.next()) {
                                friendsName.add(rs3.getString(1));
                            }
                        }

                        if (friendsName.contains(nomefriend)) {
                            exception = getResources().getString(R.string.UserinFriendGroup);
                            return exception;
                        }
                        else {
                            isSuccess = true;
                            // Inserir na BD
                            String query5 = "INSERT INTO userfriends (IDUser, IDFriend) VALUES(" + idUserLogged + ", " + idUserAdicionar + ")";
                            statement.executeUpdate(query5);
                        }
                    }
                }

            } catch (SQLException e) {
                isSuccess = false;
                exception = getResources().getString(R.string.Exceptions) + e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(String s) {
            if (isSuccess){
                s = nomefriend + getResources().getString(R.string.AddedSuccess);
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                onRefreshList();
            }
            else {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        }
    }


    // Consultar na BD a lista de amigos do UserLogado
    private class acessListFriends extends AsyncTask<String,String,String> {
        boolean isSuccess = false;
        String exception = "";
        ArrayList<String> friendsID = new ArrayList<>();
        String idUserLogged;
        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    exception = getResources().getString(R.string.InternetConnection);
                } else {
                    String query1 = "SELECT id FROM users WHERE users.Name='" + userLogged + "'";
                    Statement statement = connectDB.createStatement();
                    ResultSet rs1 = statement.executeQuery(query1);

                    while (rs1.next()) {
                        idUserLogged = rs1.getString(1);
                        break;
                    }

                    String query2 = "SELECT IDFriend FROM userfriends WHERE IDUser = " + idUserLogged;
                    ResultSet rs2 = statement.executeQuery(query2);

                    while (rs2.next()) {
                        friendsID.add(rs2.getString(1));
                    }


                    for (int i = 0; i < friendsID.size(); i++) {
                        String query3 = "SELECT Name FROM users WHERE id ='" + friendsID.get(i) + "'";
                        ResultSet rs3 = statement.executeQuery(query3);

                        while (rs3.next()) {
                            listaAmigos.add(rs3.getString(1));
                        }
                    }

                }
            } catch (SQLException e) {
                isSuccess = false;
                exception = getResources().getString(R.string.Exceptions) + e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listaAmigos);
            listView.setAdapter(arrayAdapter);
        }
    }

    // Eliminar determinado amigo da Lista de Amigos
    private class deleteFriend extends AsyncTask<String,String,String> {
        boolean isSuccess = false;
        String exception = "";
        String friendToRemoveID = "";
        String idUserLogged;
        @Override
        protected String doInBackground(String... strings) {
            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();

                if (connectDB == null) {
                    exception = getResources().getString(R.string.InternetConnection);
                } else {
                    String query1 = "SELECT id FROM users WHERE users.Name='" + userLogged + "'";
                    Statement statement = connectDB.createStatement();
                    ResultSet rs1 = statement.executeQuery(query1);

                    while (rs1.next()) {
                        idUserLogged = rs1.getString(1);
                    }

                    String query2 = "SELECT id FROM users WHERE Name = '" + itemSelecionadoName + "'";
                    ResultSet rs2 = statement.executeQuery(query2);

                    while (rs2.next()) {
                        friendToRemoveID = rs2.getString(1);
                    }

                    String query3 = "DELETE FROM userfriends WHERE IDFriend = " + friendToRemoveID;
                    statement.executeUpdate(query3);

                }
            } catch (SQLException e) {
                isSuccess = false;
                exception = getResources().getString(R.string.Exceptions) + e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(String s) {
            onRefreshList();
        }
    }



}
