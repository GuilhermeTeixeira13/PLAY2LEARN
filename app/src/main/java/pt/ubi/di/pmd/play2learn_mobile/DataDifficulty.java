package pt.ubi.di.pmd.play2learn_mobile;

import java.util.ArrayList;
import java.util.List;

public class DataDifficulty {

    public static List<Dificulty> getDifficultyList() {
        List<Dificulty> difficultyList = new ArrayList<>();

        Dificulty easyMode = new Dificulty();
        easyMode.setNomeDif("@string/difEasy");
        difficultyList.add(easyMode);

        Dificulty mediumMode = new Dificulty();
        mediumMode.setNomeDif("@string/difMedium");
        difficultyList.add(mediumMode);

        Dificulty hardMode = new Dificulty();
        hardMode.setNomeDif("@string/difHard");
        difficultyList.add(hardMode);

        return difficultyList;
    }
}
