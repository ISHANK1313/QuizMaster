import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class  Quiz_App {
    private static JFrame frame= new JFrame();
    private static JLabel label= new JLabel();
    private static JLabel timerLabel= new JLabel();
    private static int timeLeft;
    private  static Timer clock;
    private static int questionIndex;
    private static  JButton [] buttons = new JButton[4];
    private static int score;
    private static String currentFileName=null;
    private static List<Question> questionList=null;
    private static String[] quizNames={"java basics","Math","History"};
    private static String[] quizFileNames={"java_basics.txt","math.txt","history.txt"};
    private static JLabel questionLabel= new JLabel();
    private static int questionCounter=-1;
    private static JLabel imageLabel= new JLabel();
    public static List<Question> LoadQuestion(String fileName) {

        List<Question> questions = new ArrayList<>();
        try {

            InputStream stream = Quiz_App.class.getResourceAsStream("/" + fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                Question question = new Question();
                if (parts.length >= 6) {
                    question.setProblem(parts[0]);
                    String temp[] = {parts[1], parts[2], parts[3], parts[4]};
                    question.setOptions(temp);
                    question.setRightIndex(Integer.parseInt(parts[5]));
                    if(parts.length==7){
                        question.setImageFileName(parts[6]);
                        question.setImagePresent(true);
                    }
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
        frame.setSize(500, 500);
        frame.setLayout(null);
        buttons[0] = new JButton();
        buttons[1] = new JButton();
        buttons[2] = new JButton();
        buttons[3] = new JButton();
        frame.add(timerLabel);

        for (int i = 0; i < 4; i++) {
            frame.add(buttons[i]);
        }
        frame.add(label);
        frame.add(questionLabel);
        frame.add(imageLabel);
            display();
            for (int i = 0; i < 4; i++) {
                final int finalI = i;
                buttons[i].addActionListener(e -> {

                    int index = questionList.get(questionIndex).getRightIndex();
                    if (finalI != index) {
                        if (clock != null && clock.isRunning()) {
                            clock.stop();
                        }
                        playSound("buzz.wav");
                        JOptionPane.showMessageDialog(frame, "Wrong answer...");
                        questionCounter--;

                    } else {
                        if (clock != null && clock.isRunning()) {
                            clock.stop();
                        }
                        playSound("bell.wav");
                        JOptionPane.showMessageDialog(frame, "Right answer...");
                        score += 10;
                        questionCounter--;

                    }

                    questionIndex++;
                    display();

                });

            }


    }

    public static void saveScore() {
        try {
            // Get the directory where the JAR is located
            String jarDir = new File(Quiz_App.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI())
                    .getParent();
            File scoreFile = new File(jarDir, "Saved_Score.txt");

            FileWriter writer = new FileWriter(scoreFile, true);
            LocalDateTime timestamp = LocalDateTime.now();
            String now = timestamp.toString();
            String[] date = now.split("T");
            writer.write("your score for quiz " + currentFileName + " is := " + score +
                    " given at time := [ Date is :=" + date[0] +
                    ",Time is := " + date[1].substring(0,8) + "]" + "\n");
            writer.close();
            System.out.println("Score saved to: " + scoreFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public static void display()  {
        if(questionIndex>=questionList.size()){
            if(clock!=null){
                clock.stop();
            }
            JOptionPane.showMessageDialog(frame,"end of the quiz..."+"\n"+"your score is :="+score );
            saveScore();
            frame.dispose();
            return;
        }
        if(questionCounter==-1){
            questionCounter=questionList.size();
        }

        Question q = questionList.get(questionIndex);
        if(q.isImagePresent()){
            String imagePath=q.getImageFileName();
            java.net.URL imageUrl=Quiz_App.class.getResource(imagePath);
            if (imageUrl==null) {
                System.out.println("Image not found: " + imagePath);
                positionComponentsWithoutImage();
            }
            else {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image img= icon.getImage();
                Image scaledImg= img.getScaledInstance(200,150,Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaledImg);

                imageLabel.setIcon(icon);
                positionComponentsWithImage();
            }
        }
        else{
            positionComponentsWithoutImage();
        }
        questionLabel.setText("questions left:= "+questionCounter);
        label.setText(q.getProblem());
        String [] temp= q.getOptions();
        for(int i=0;i<4;i++){
            buttons[i].setText(temp[i]);
        }

    StartTimer();

}

public static void StartTimer(){
    timeLeft=10;
    timerLabel.setText("time left: "+timeLeft+" seconds");
    clock=new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            timeLeft--;
            timerLabel.setText("time left: "+timeLeft+" seconds");
            timerLabel.setForeground(Color.BLACK);
            if(timeLeft<4){
                timerLabel.setForeground(Color.RED);
            }

            if(timeLeft==0){
                clock.stop();
                playSound("alarm-tone.wav");
                JOptionPane.showMessageDialog(frame,"Time's up");

                questionIndex++;
                questionCounter--;
                display();
            }
        }
    });
    clock.start();
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
        Collections.shuffle(questionList);
        score=0;
        questionIndex=0;
        selectorFrame.dispose();
        createAndDisplay();


    });

selectorFrame.setVisible(true);


}

    public static void playSound(String fileName){
        new Thread(() -> {
            try {
                InputStream stream = Quiz_App.class.getResourceAsStream("/sounds/" + fileName);

                if (stream == null) {
                    System.out.println("Sound file not found: " + fileName);
                    return;
                }


                BufferedInputStream bufferedStream = new BufferedInputStream(stream);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();

                while (!clip.isRunning()) {
                    Thread.sleep(10);
                }
                while (clip.isRunning()) {
                    Thread.sleep(10);
                }

                clip.close();

            } catch (Exception e) {
                System.out.println("Error playing sound: " + fileName);
                e.printStackTrace();
            }
        }).start();
    }

public static void positionComponentsWithoutImage(){
        imageLabel.setVisible(false);
    label.setBounds(50, 30, 400, 30);
    buttons[0].setBounds(50, 80, 400, 40);
    buttons[1].setBounds(50, 130, 400, 40);
    buttons[2].setBounds(50, 180, 400, 40);
    buttons[3].setBounds(50, 230, 400, 40);

    timerLabel.setBounds(50, 280, 150, 30);

    questionLabel.setBounds(300, 280, 150, 30);

}

public static void positionComponentsWithImage(){
    label.setBounds(50, 30, 400, 30);
    imageLabel.setBounds(50,60,200,150);
    imageLabel.setVisible(true);
    buttons[0].setBounds(50, 220, 400, 40);
    buttons[1].setBounds(50, 270, 400, 40);
    buttons[2].setBounds(50, 320, 400, 40);
    buttons[3].setBounds(50, 370, 400, 40);

    timerLabel.setBounds(50, 430, 150, 30);

    questionLabel.setBounds(300, 430, 150, 30);

}

    public static void main(String[] args) {

        SelectQuiz();
    }
}
