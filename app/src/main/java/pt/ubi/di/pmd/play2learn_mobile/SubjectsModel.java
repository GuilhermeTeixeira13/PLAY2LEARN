package pt.ubi.di.pmd.play2learn_mobile;

import android.graphics.Bitmap;

public class SubjectsModel {
    private String name_Subject;
    private Bitmap image;

    public SubjectsModel(String name_Subject, Bitmap image){
        this.name_Subject = name_Subject;
        this.image = image;
    }

    public String getName_Subject() {
        return name_Subject;
    }

    public void setName_Subject(String name_Subject) {
        this.name_Subject = name_Subject;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
