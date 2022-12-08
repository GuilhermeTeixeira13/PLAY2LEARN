package pt.ubi.di.pmd.play2learn_mobile;

public class Question {
    Integer id;
    Integer idSubject;
    String question;
    Integer difficulty;
    String rightAnswer;
    String wrongAnswer1;
    String wrongAnswer2;
    String wrongAnswer3;

    public Question(Integer id, Integer idSubject, String question, Integer difficulty, String rightAnswer, String wrongAnswer1, String wrongAnswer2, String wrongAnswer3) {
        this.id = id;
        this.idSubject = idSubject;
        this.question = question;
        this.difficulty = difficulty;
        this.rightAnswer = rightAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdSubject() {
        return idSubject;
    }

    public String getQuestion() {
        return question;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return wrongAnswer3;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdSubject(Integer idSubject) {
        this.idSubject = idSubject;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }

    public void setWrongAnswer3(String wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", idSubject=" + idSubject +
                ", question='" + question + '\'' +
                ", difficulty=" + difficulty +
                ", rightAnswer='" + rightAnswer + '\'' +
                ", wrongAnswer1='" + wrongAnswer1 + '\'' +
                ", wrongAnswer2='" + wrongAnswer2 + '\'' +
                ", wrongAnswer3='" + wrongAnswer3 + '\'' +
                '}';
    }
}
