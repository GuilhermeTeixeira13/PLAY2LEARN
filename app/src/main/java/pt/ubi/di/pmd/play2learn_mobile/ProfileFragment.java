package pt.ubi.di.pmd.play2learn_mobile;

import android.graphics.Bitmap;
import android.net.Uri;
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

        return view;
    }
}
