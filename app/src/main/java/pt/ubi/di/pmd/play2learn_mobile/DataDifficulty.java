package pt.ubi.di.pmd.play2learn_mobile;


import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DataDifficulty {

    public static List<Dificulty> getDifficultyList() {
        List<Dificulty> difficultyList = new ArrayList<>();

        Dificulty easyMode = new Dificulty();
        easyMode.setNomeDif("Easy");
        difficultyList.add(easyMode);

        Dificulty mediumMode = new Dificulty();
        mediumMode.setNomeDif("Medium");
        difficultyList.add(mediumMode);

        Dificulty hardMode = new Dificulty();
        hardMode.setNomeDif("Hard");
        difficultyList.add(hardMode);

        return difficultyList;
    }


}
