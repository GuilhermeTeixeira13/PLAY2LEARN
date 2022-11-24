package pt.ubi.di.pmd.play2learn_mobile;

import android.graphics.Bitmap;

public class UserModel {
    private String name;
    private String email;
    private String password;
    private Bitmap image;
    private String biblio;

    public UserModel(String name, String email, String password, Bitmap image, String biblio){
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.biblio = biblio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getBiblio() {
        return biblio;
    }

    public void setBiblio(String biblio) {
        this.biblio = biblio;
    }




}
