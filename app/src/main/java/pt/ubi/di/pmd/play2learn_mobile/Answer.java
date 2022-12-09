package pt.ubi.di.pmd.play2learn_mobile;

public class Answer
{
    private final boolean rightAnswer;
    private final String answer;

    public Answer(boolean rightAnswer, String answer)
    {
        this.rightAnswer   = rightAnswer;
        this.answer = answer;
    }

    public boolean isRightAnswer()   {
        return rightAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "rightAnswer=" + rightAnswer +
                ", answer='" + answer + '\'' +
                '}';
    }
}