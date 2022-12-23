package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ProfileFragment extends Fragment {
    private final int PICK_IMAGE_REQUEST=99;
    private Uri imagepath;
    private Bitmap imageToStore;
    String username = "";
    ImageView ImgUser;
    EditText EdTextBio;
    Button BtnSave;
    ImageView BtnChangePhoto;
    byte[] bArray;

    public static final int PICK_IMAGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Change toolbar title
        getActivity().setTitle(getResources().getString(R.string.ProfileFragment));

        SharedPreferences sp = getContext().getSharedPreferences("userLogged", Context.MODE_PRIVATE);
        if (sp.contains("uname")) {
            username = sp.getString("uname", "");
        }

        EdTextBio = view.findViewById(R.id.edTextNameBibio);
        ImgUser = view.findViewById(R.id.profilePic);
        BtnSave = view.findViewById(R.id.BtnSave);
        BtnChangePhoto = view.findViewById(R.id.changePhoto);

        ProfileFragment.GetBio getBio = new ProfileFragment.GetBio();
        getBio.execute();

        ProfileFragment.GetImage getImage = new ProfileFragment.GetImage();
        getImage.execute();

        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment.UpdateBio updateBio = new ProfileFragment.UpdateBio();
                updateBio.execute();
            }
        });

        BtnChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(Intent.createChooser(chooserIntent, "Select Picture"), PICK_IMAGE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                ImgUser.setImageBitmap(myBitmap);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                bArray = bos.toByteArray();

                ProfileFragment.UpdateImage updateImage = new ProfileFragment.UpdateImage();
                updateImage.execute();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetBio extends AsyncTask<String,String,String> {
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
                    EdTextBio.setText(getBio(connectDB));
                    isSuccess = true;
                }
            } catch (Exception e) {
                exception = getResources().getString(R.string.Exceptions) + e;
            }
            return exception;
        }
    }

    public Blob getImage(Connection connectDB) throws SQLException {
        String query = "SELECT ProfilePic FROM users WHERE name= '"+username+"'";
        Statement statement = connectDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        Blob pic = null;

        while (rs.next()) {
            pic = rs.getBlob(1);
        }
        return pic;
    }

    private class GetImage extends AsyncTask<String,String,String> {
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
                    Blob img = getImage(connectDB);
                    System.out.println("img: "+img);
                    int blobLength = (int) img.length();
                    byte[] blobAsBytes = img.getBytes(1, blobLength);
                    System.out.println("blobAsBytes: "+blobAsBytes);

                    YuvImage yuvimage=new YuvImage(blobAsBytes, ImageFormat.NV21, 1000, 1000, null);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    yuvimage.compressToJpeg(new Rect(0, 0, 100, 100), 80, baos);

                    byte[] jdata = baos.toByteArray();
                    Bitmap bmp = BitmapFactory.decodeByteArray(jdata, 0, jdata.length);

                    System.out.println("bitmap: "+bmp);
                    ImgUser.setImageBitmap(bmp);
                    isSuccess = true;
                }
            } catch (Exception e) {
                exception = getResources().getString(R.string.Exceptions) + e;
            }
            return exception;
        }
    }

    public String getBio(Connection connectDB) throws SQLException {
        String query = "SELECT Biblio FROM users WHERE name= '"+username+"'";
        Statement statement = connectDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        String bio = "";

        while (rs.next()) {
            bio = rs.getString(1);
        }
        return bio;
    }

    private class UpdateBio extends AsyncTask<String,String,String> {
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
                    updateBio(connectDB);

                    isSuccess = true;
                }
            } catch (Exception e) {
                exception = getResources().getString(R.string.Exceptions) + e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(String s) {
            if(isSuccess){
                System.out.printf("sucesso!");
            }
        }
    }

    public void updateBio(Connection connectDB) throws SQLException {
        String query = "UPDATE users SET Biblio='" + EdTextBio.getText() + "' WHERE Name = '" + username + "'";
        System.out.println(query);
        Statement statement = connectDB.createStatement();
        statement.executeUpdate(query);
    }

    private class UpdateImage extends AsyncTask<String,String,String> {
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
                    updateImage(connectDB);

                    isSuccess = true;
                }
            } catch (Exception e) {
                exception = getResources().getString(R.string.Exceptions) + e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(String s) {
            if(isSuccess){
                System.out.printf("sucesso!");
            }
        }
    }

    public void updateImage(Connection connectDB) throws SQLException {
        Blob blobData = connectDB.createBlob();
        blobData.setBytes(1, bArray);

        String query = "UPDATE users SET ProfilePic='" + blobData + "' WHERE Name = '" + username + "'";
        System.out.println(query);
        Statement statement = connectDB.createStatement();
        statement.executeUpdate(query);
    }
}
