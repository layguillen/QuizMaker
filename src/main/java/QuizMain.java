import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class QuizMain {

    private String question;
    private int correctAnswer;
    private int guess;
    private int score = 0;
    private int possibleScore;

    private Scanner userInput = new Scanner(System.in);
    private File fileFrom;
    private String fileFromName;
    private static final String OUTPUT_FILE = "Scores.txt";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    public void run() {
        try (Scanner quizScanner = new Scanner(fileFrom)) {
            while (quizScanner.hasNextLine()){
                possibleScore++;

                String line = quizScanner.nextLine();
                String[] array = makeArray(line);
                question = array[0];
                System.out.println(getQuestion());
                displayAnswers(array);
                getUserAnswer();
            }
            System.out.println(getScore());
            logScore(getScore(), fileFromName);

        } catch (FileNotFoundException fnfe) {
            System.out.println("File error: " + fnfe.getMessage());
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void logScore(String score, String quizName){
        String date = formatter.format(new Date());
        try(PrintWriter dataOutput = new PrintWriter(new FileOutputStream(OUTPUT_FILE, true))){
            dataOutput.println(String.format("%s       Quiz: %s       %s", date, quizName, score));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    private void getUserAnswer() {
        System.out.print("Select your answer (number): ");
        try{
            guess = Integer.parseInt(userInput.nextLine().trim());
        } catch (NumberFormatException e){
            guess = -1;
        }

        if (isCorrect()){
            score++;
        }
    }

    private boolean isCorrect(){
        if (guess == correctAnswer){
            System.out.println("Correct!");
            System.out.println();
            return true;
        } else if(guess == -1){
            System.out.println("Invalid input. Please enter a number.");
            System.out.println();
            return false;
        } else{
            System.out.println("Incorrect.");
            System.out.println();
            return false;
        }
    }

    private void displayAnswers(String[] array) {
        for (int i = 1; i < array.length; i++) {
            if(array[i].contains("*")){
                correctAnswer = i;
            }
            System.out.println(i + ". " + makeSubstring(array[i]));
        }
    }

    private String getQuestion() {
        return question;
    }

    private String[] makeArray(String string){
        return string.split("\\|");
    }

    private String makeSubstring(String string){
        if(string.contains("*")){
         return string.substring(0, string.length() -1);
        }
        return string;
    }

    private String getScore(){
        String userScore = Integer.toString(score);
        String finalScore = userScore + "/" + possibleScore;
        return "Correct answers: " + finalScore;
    }

    private void displayWelcomeMessage(){
        System.out.println();
        System.out.println("=============================");
        System.out.println("Welcome to the Quiz Machine!");
        System.out.println("=============================");
        System.out.println();
    }

    public void getFileChoice(){
        displayWelcomeMessage();
        System.out.println("1. Database Quiz");
        System.out.println("2. General Java Quiz");
        System.out.println("3. OOP Quiz");
        System.out.println();
        System.out.print("Select the number of the quiz you'd like to take (Ex: 1): ");
        System.out.println();

        String userChoice = userInput.nextLine();
        if(userChoice.contains("1")){
            fileFrom = new File("C:\\Users\\Student\\workspace\\SideProjects\\QuizMaker\\src\\main\\resources\\DatabaseQuiz.txt");
            fileFromName = "Database Quiz";
        }
        else if (userChoice.contains("2")){
            fileFrom = new File("C:\\Users\\Student\\workspace\\SideProjects\\QuizMaker\\src\\main\\resources\\JavaGeneralQuiz.txt");
            fileFromName = "Java General Quiz";
        }
        else if (userChoice.contains("3")){
            fileFrom = new File("C:\\Users\\Student\\workspace\\SideProjects\\QuizMaker\\src\\main\\resources\\OOPquiz.txt");
            fileFromName = "OOP Quiz";
        }
    }


}
