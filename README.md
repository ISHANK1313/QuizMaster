# 🎯 Quiz Master - Interactive Desktop Exam Prep

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-red?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Complete-success?style=for-the-badge)

**A feature-rich, professional-grade quiz application with modern UI and comprehensive features**

[Features](#-features) • [Demo](#-demo) • [Installation](#-installation) • [Usage](#-usage) • [Architecture](#-architecture)

</div>

---

### 🎨 Visual Experience
- **Dynamic UI Layouts** - Automatically adjusts based on question type (with/without images)
- **Countdown Timer** - Visual urgency with red warning when time runs low
- **Sound Effects** - Immersive feedback for correct answers, mistakes, and timeouts
- **Image Support** - Questions can include visual elements with automatic scaling

### 💪 Technical Excellence
- **Self-Contained JAR** - All resources (images, sounds, questions) bundled inside
- **Persistent Score Tracking** - Saves results with timestamps to disk
- **Thread-Safe Audio** - Non-blocking sound playback
- **Resource Management** - Professional packaging with no external dependencies
- **Question Shuffling** - Different experience every time

---

## ✨ Features

### 📚 Multi-Quiz Support
- **Java Basics** - Test your programming knowledge
- **Mathematics** - Sharpen your calculation skills  
- **History** - Challenge your historical facts

### ⏱️ Smart Timer System
- 10-second countdown per question
- Visual warning (red text) when time is running out
- Automatic progression when timer expires
- Alarm sound for timeout events

### 🎵 Audio Feedback
- ✅ **Bell sound** - Correct answer celebration
- ❌ **Buzz sound** - Wrong answer notification  
- ⏰ **Alarm sound** - Time's up alert

### 📊 Score Management
- Real-time score tracking (10 points per correct answer)
- Persistent save system with clean timestamps
- Scores saved next to JAR file (works anywhere)
- Historical record of all attempts

### 🎲 Smart Question System
- Question shuffling for replayability
- Image-based questions with fallback handling
- Question counter showing progress
- Graceful error handling (missing assets don't crash)

---

## 🎬 Demo

### Quiz Selection Screen
```
┌─────────────────────────────────────┐
│  Select which quiz you want to give │
│  ┌──────────────────────────────┐  │
│  │ [v] Java Basics              │  │
│  │     Math                     │  │
│  │     History                  │  │
│  └──────────────────────────────┘  │
└─────────────────────────────────────┘
```

### Quiz Interface (With Image)
```
┌─────────────────────────────────────────┐
│  What is this logo?                     │
│  ┌─────────────┐                        │
│  │   [IMAGE]   │                        │
│  │  Java Logo  │                        │
│  └─────────────┘                        │
│  ┌───────────────────────────────────┐ │
│  │ A) Java                           │ │
│  ├───────────────────────────────────┤ │
│  │ B) Python                         │ │
│  ├───────────────────────────────────┤ │
│  │ C) C++                            │ │
│  ├───────────────────────────────────┤ │
│  │ D) JavaScript                     │ │
│  └───────────────────────────────────┘ │
│                                         │
│  Time left: 7 seconds  Questions: 3    │
└─────────────────────────────────────────┘
```

---

## 🚀 Installation

### Prerequisites
- Java Runtime Environment (JRE) 20 or higher
- No external dependencies required!

### Quick Start

1. **Download the JAR file**
   ```bash
   # Clone this repository
   git clone https://github.com/yourusername/quiz-app.git
   cd quiz-app
   ```

2. **Build the project** (if building from source)
   ```bash
   # Using IntelliJ IDEA or any Java IDE
   # Build -> Build Artifacts -> Quiz_App:jar -> Build
   ```

3. **Run the application**
   ```bash
   java -jar Quiz_App.jar
   ```

That's it! The app runs immediately—no installation, no setup, no hassle.

---

## 🎮 Usage

### Starting a Quiz
1. Launch the application
2. Select your quiz from the dropdown menu
3. Click to start (selection auto-triggers)

### Answering Questions
- Click any of the four option buttons
- You have **10 seconds** per question
- Timer turns **red** with 4 seconds remaining
- Get instant feedback with sound and dialog

### Viewing Scores
Check the `Saved_Score.txt` file in the same directory as the JAR:
```
your score for quiz java_basics.txt is := 40 
given at time := [ Date is :=2025-10-04,Time is := 23:54:13]
```

---

## 🏗️ Architecture

### Project Structure
```
Quiz_App/
├── Quiz_App.java          # Main application logic
├── Question.java          # Question data model
├── Quizzes.java          # Quiz configuration model
├── images/               # Question images
│   └── java.png
├── sounds/               # Audio feedback
│   ├── bell.wav         # Correct answer
│   ├── buzz.wav         # Wrong answer
│   └── alarm-tone.wav   # Timeout
├── java_basics.txt      # Java quiz questions
├── math.txt             # Math quiz questions
└── history.txt          # History quiz questions
```

### Core Components

#### 🎯 Quiz_App.java
The brain of the application:
- `LoadQuestion()` - Reads questions from bundled text files
- `createAndDisplay()` - Builds the main UI dynamically
- `display()` - Renders current question with adaptive layout
- `StartTimer()` - Manages countdown with visual feedback
- `SelectQuiz()` - Quiz selection interface
- `saveScore()` - Persistent score management
- `playSound()` - Thread-safe audio playback

#### 📝 Question.java
Data model for quiz questions:
- Problem text
- Four answer options
- Correct answer index
- Optional image filename
- Image presence flag

#### 🎲 Quizzes.java
Quiz configuration:
- Quiz display name
- Associated text file
- String representation for UI

### Question File Format
```
Question text;Option A;Option B;Option C;Option D;Correct Index;[Optional Image Path]
```

**Example:**
```
What is this logo?;Java;Python;C++;JavaScript;0;/images/java.png
```

---

## 🎯 Key Technical Highlights

### 1. Professional Resource Management
```java
InputStream stream = Quiz_App.class.getResourceAsStream("/" + fileName);
```
Loads all resources from inside the JAR—no external file dependencies.

### 2. Dynamic UI Adaptation
```java
public static void positionComponentsWithImage() { ... }
public static void positionComponentsWithoutImage() { ... }
```
Automatically adjusts layout based on question type.

### 3. Non-Blocking Audio
```java
new Thread(() -> { 
    // Play sound without freezing UI 
}).start();
```
Sounds play smoothly without interrupting user interaction.

### 4. Smart Score Persistence
```java
String jarDir = new File(Quiz_App.class.getProtectionDomain()
    .getCodeSource().getLocation().toURI()).getParent();
```
Scores save relative to JAR location—works anywhere!

### 5. Timer Visual Feedback
```java
if(timeLeft < 4) {
    timerLabel.setForeground(Color.RED);
}
```
Creates urgency and enhances user experience.

---

## 🛠️ Customization

### Adding New Quizzes

1. **Create a question file** (e.g., `science.txt`)
   ```
   What is H2O?;Oxygen;Hydrogen;Water;Carbon;2
   ```

2. **Update the Quiz_App.java arrays:**
   ```java
   private static String[] quizNames = {"java basics", "Math", "History", "Science"};
   private static String[] quizFileNames = {"java_basics.txt", "math.txt", "history.txt", "science.txt"};
   ```

3. **Rebuild the JAR** and include the new text file

### Adding Images to Questions

1. Place image in the `images/` folder
2. Add image path to question line:
   ```
   Question text;A;B;C;D;correct_index;/images/your_image.png
   ```

---

## 📈 Future Enhancements

- [ ] Difficulty levels (Easy, Medium, Hard)
- [ ] Statistics dashboard with charts
- [ ] Multiplayer mode
- [ ] Category-based filtering
- [ ] Export scores to PDF
- [ ] Dark mode theme
- [ ] Question editor GUI
- [ ] Leaderboard system

---

## 🤝 Contributing

Contributions are welcome! This project was built as a learning experience, and improvements are always appreciated.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is open source and available under the [MIT License](LICENSE).
---

## 🙏 Acknowledgments

- Built with modern Java and Swing GUI framework
- Designed for educational and practical use

---

<div align="center">

### ⭐ Star this repo if you found it helpful!

**Made with Java and Swing • Built from scratch**

</div>
