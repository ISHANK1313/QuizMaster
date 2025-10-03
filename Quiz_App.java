import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class  Quiz_App {
    private static JFrame frame= new JFrame();
    private static JLabel label= new JLabel();
    private static int questionIndex;
    private static  JButton [] buttons = new JButton[4];
    private static int score;
    private static String currentFileName=null;
    private static List<Question> questionList=null;
    private static String[] quizNames={"java basics","Math","History"};
    private static String[] quizFileNames={"java_basics.txt","math.txt","history.txt"};

    public static List<Question> LoadQuestion(String fileName) {

        List<Question> questions = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                Question question = new Question();
                if (parts.length == 6) {
                    question.setProblem(parts[0]);
                    String temp[] = {parts[1], parts[2], parts[3], parts[4]};
                    question.setOptions(temp);
                    question.setRightIndex(Integer.parseInt(parts[5]));
                    questions.add(question);
                }

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static void createAndDisplay() {
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(null);
        label.setBounds(50, 30, 400, 30);
        buttons[0]= new JButton();
        buttons[1]= new JButton();
        buttons[2]= new JButton();
        buttons[3] = new JButton();

        buttons[0].setBounds(50, 80, 400, 40);
        buttons[1].setBounds(50, 130, 400, 40);
        buttons[2].setBounds(50, 180, 400, 40);
        buttons[3].setBounds(50, 230, 400, 40);



        for (int i = 0; i < 4; i++) {
            frame.add(buttons[i]);
        }
        frame.add(label);
       display();
        for(int i=0;i<4;i++){
            final int finalI = i;
            buttons[i].addActionListener(e -> {

                int index= questionList.get(questionIndex).getRightIndex();
                if(finalI !=index){
                    JOptionPane.showMessageDialog(frame,"Wrong answer...");

                }
                else{
                    JOptionPane.showMessageDialog(frame,"Right answer...");
                    score+=10;
                }
                questionIndex++;
                display();

            });

        }


    }

    public static void saveScore()  {
       try{ FileWriter writer= new FileWriter("Saved_Score.txt",true);
        LocalDateTime timestamp=LocalDateTime.now();
        writer.write("your score for quiz "+currentFileName+" is := "+score+" given at time := ["+timestamp+"]"+"\n");
        writer.close();}
       catch (IOException e){
           e.printStackTrace();
       }
    }

public static void display()  {
        if(questionIndex>=questionList.size()){
            JOptionPane.showMessageDialog(frame,"end of the quiz..."+"\n"+"your score is :="+score );
            saveScore();
            frame.dispose();
            return;
        }
        Question q = questionList.get(questionIndex);

        label.setText(q.getProblem());
        String [] temp= q.getOptions();
        for(int i=0;i<4;i++){
            buttons[i].setText(temp[i]);
        }
}

public static void SelectQuiz(){
    JFrame selectorFrame= new JFrame("Selector Frame");
    selectorFrame.setLayout(null);
    selectorFrame.setSize(500,400);
    selectorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JLabel ask=new JLabel();
    ask.setText("select which quiz you want to give below");
    ask.setBounds(50,30,400,30);
    Quizzes [] quizzes= new Quizzes[quizNames.length];
    for(int i=0;i<quizzes.length;i++){
        Quizzes q=new Quizzes();
        q.setQuizFile(quizFileNames[i]);
        q.setQuizName(quizNames[i]);
        quizzes[i]=q;
    }

    JComboBox<Quizzes> box= new JComboBox<>(quizzes);
    box.setBounds(50,60,200,30);
    selectorFrame.add(ask);
    selectorFrame.add(box);

    box.addActionListener(e -> {
        Quizzes w= (Quizzes) box.getSelectedItem();
        currentFileName=w.getQuizFile();
        questionList=LoadQuestion(currentFileName);
        score=0;
        questionIndex=0;
        selectorFrame.dispose();
        createAndDisplay();


    });

selectorFrame.setVisible(true);


}

    public static void main(String []args){
        SelectQuiz();

    }
}
