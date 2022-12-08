package pt.ubi.di.pmd.play2learn_mobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProfileFragment extends Fragment {
    private final int PICK_IMAGE_REQUEST=99;
    private Uri imagepath;
    private Bitmap imageTostore;
    String userLogged = "";
    ImageView picuser;
    EditText nameuser;
    EditText biogra;
    Button save;
    P2L_DbHelper connectionhelper;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Change toolbar title
        getActivity().setTitle(getResources().getString(R.string.ProfileFragment));

        BaseActivity BaseActivity = (BaseActivity) getActivity();
        System.out.println("base");
        System.out.println(BaseActivity);
        userLogged = BaseActivity.getUserLogged();
        System.out.println("userlogg");
        System.out.println(userLogged);
        // Change toolbar title
        getActivity().setTitle(getResources().getString(R.string.ProfileFragment));
        nameuser = view.findViewById(R.id.edTextName);
        biogra = view.findViewById(R.id.edTextNameBibio);
        picuser = view.findViewById(R.id.profilePic);
        save = view.findViewById(R.id.BtnSave);
        connectionhelper = new P2L_DbHelper();
        try {
            seebio(view);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        picuser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //choseimage();
            }
        });
        //save.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View v) {
        //        try {
        //            save(view);
        //        } catch (SQLException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //});

        return view;
    }

    public void seebio(View v) throws SQLException {
        printbio printbio = new printbio();
        printbio.execute();

    }

    private class printbio extends AsyncTask<String,String,String> {
        String bio;
        //Blob pic;
        String z = "";
        boolean isSuccess = false;
        @Override
        protected String doInBackground(String... strings) {

            try {
                P2L_DbHelper connectNow = new P2L_DbHelper();
                Connection connectDB = connectNow.getConnection();
                System.out.println(connectDB);

                if (connectDB== null){
                    z = "Please check your internet connection";
                }else {
                    String query = "select Biblio from users where Name='"+userLogged+"'";

                    Statement statement = connectDB.createStatement();

                    ResultSet rs = statement.executeQuery(query);

                    while (rs.next()){
                        bio = rs.getString(1);
                        System.out.println(bio);

                        if (bio != null){
                            isSuccess = true;
                        }
                        //pic =rs.getBlob(2);
                        //if (pic!=null) {
                        //    int blobLength = (int) pic.length();
                        //    byte[] blobAsBytes = pic.getBytes(1, blobLength);
                        //    Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
                        //    picuser.setImageBitmap(btm);
                        //}

                    }

                }

            } catch (SQLException e) {
                z = "Exceptions"+e;
            }
            return z;
        }
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(),""+z,Toast.LENGTH_LONG).show();

            if (isSuccess){
                nameuser.setText(userLogged);
                biogra.setText(bio);
            }
        }
    }
}
