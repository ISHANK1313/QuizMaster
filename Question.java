public class Question {
    private String problem;
    private String [] options;
    private int rightIndex;
    private String imageFileName;
    private boolean isImagePresent=false;

    public boolean isImagePresent() {
        return isImagePresent;
    }

    public void setImagePresent(boolean imagePresent) {
        isImagePresent = imagePresent;
    }



    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }


    public int getRightIndex() {
        return rightIndex;
    }

    public void setRightIndex(int rightIndex) {
        this.rightIndex = rightIndex;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }



    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }




}
