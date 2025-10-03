public class Quizzes {
    private String quizName;

    public String getQuizFile() {
        return quizFile;
    }

    public void setQuizFile(String quizFile) {
        this.quizFile = quizFile;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    private String quizFile;

    @Override
    public String toString(){
        return quizName;
    }

}
