import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class OnlineExaminationEnhancedWithInCharge extends Application {
    private Stage window;
    private Scene loginScene, examScene, resultScene, adminScene, inChargeScene;
    private int currentQuestion = 0;
    private int score = 0;
    private String currentUser;
    private final Map<String, String> users = new HashMap<>();
    private final Map<String, Integer> userScores = new HashMap<>();
    private final String adminUsername = "SPCET";
    private final String adminPassword = "1127";
    private final String inChargeUsername = "incharge";
    private final String inChargePassword = "admin123";

    private final String[][] questions = {
        {"Welcome to the quiz! Let's begin.", "What is the capital of France?", "Paris", "London", "Berlin", "Madrid", "1"},
        {"Keep going! You're doing great.", "What is 2 + 2?", "3", "4", "5", "6", "2"},
        {"Final question! Good luck!", "Which programming language is used for Android development?", "C++", "Python", "Java", "Ruby", "3"}
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Online Examination System");

        // Initialize user data
        users.put("bharghavi", "1234");
        users.put("priya", "4321");

        // Login Scene
        loginScene = createLoginScene();

        // Exam Scene
        examScene = createExamScene();

        // Result Scene
        resultScene = createResultScene();

        // Admin Scene
        adminScene = createAdminScene();

        // In-charge Scene
        inChargeScene = createInChargeScene();

        // Show Login Scene initially
        window.setScene(loginScene);
        window.show();
    }

    private Scene createLoginScene() {
        GridPane loginLayout = new GridPane();
        loginLayout.setPadding(new Insets(20));
        loginLayout.setHgap(10);
        loginLayout.setVgap(10);
        loginLayout.setStyle("-fx-background-color: #D6EAF8;");

        Label userLabel = new Label("Username:");
        userLabel.setStyle("-fx-text-fill: #1F618D; -fx-font-size: 14px;");

        TextField usernameInput = new TextField();
        Label passLabel = new Label("Password:");
        passLabel.setStyle("-fx-text-fill: #1F618D; -fx-font-size: 14px;");

        PasswordField passwordInput = new PasswordField();
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 14px;");

        loginLayout.add(userLabel, 0, 0);
        loginLayout.add(usernameInput, 1, 0);
        loginLayout.add(passLabel, 0, 1);
        loginLayout.add(passwordInput, 1, 1);
        loginLayout.add(loginButton, 1, 2);

        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            if (users.containsKey(username) && users.get(username).equals(password)) {
                currentUser = username;
                currentQuestion = 0;
                score = 0;
                showQuestion();
                window.setScene(examScene);
            } else if (username.equals(adminUsername) && password.equals(adminPassword)) {
                window.setScene(adminScene);
            } else if (username.equals(inChargeUsername) && password.equals(inChargePassword)) {
                window.setScene(inChargeScene);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password!");
            }
        });

        return new Scene(loginLayout, 300, 200);
    }

    private Scene createExamScene() {
        VBox examLayout = new VBox(10);
        examLayout.setPadding(new Insets(20));
        examLayout.setStyle("-fx-background-color: #FDEDEC;");

        Label taglineLabel = new Label();
        taglineLabel.setStyle("-fx-text-fill: #D35400; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label questionLabel = new Label();
        questionLabel.setStyle("-fx-text-fill: #283747; -fx-font-size: 14px;");

        ToggleGroup optionsGroup = new ToggleGroup();
        RadioButton option1 = new RadioButton();
        option1.setStyle("-fx-text-fill: #2874A6;");

        RadioButton option2 = new RadioButton();
        option2.setStyle("-fx-text-fill: #2874A6;");

        RadioButton option3 = new RadioButton();
        option3.setStyle("-fx-text-fill: #2874A6;");

        RadioButton option4 = new RadioButton();
        option4.setStyle("-fx-text-fill: #2874A6;");

        option1.setToggleGroup(optionsGroup);
        option2.setToggleGroup(optionsGroup);
        option3.setToggleGroup(optionsGroup);
        option4.setToggleGroup(optionsGroup);

        Button nextButton = new Button("Next");
        nextButton.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-size: 14px;");

        Button bookmarkButton = new Button("Bookmark");
        bookmarkButton.setStyle("-fx-background-color: #F1C40F; -fx-text-fill: white; -fx-font-size: 14px;");

        HBox buttonBox = new HBox(10, nextButton, bookmarkButton);
        examLayout.getChildren().addAll(taglineLabel, questionLabel, option1, option2, option3, option4, buttonBox);

        nextButton.setOnAction(e -> {
            RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();
            if (selected != null) {
                int selectedIndex = Integer.parseInt(selected.getId());
                if (selectedIndex == Integer.parseInt(questions[currentQuestion][6])) {
                    score++;
                }
                currentQuestion++;
                if (currentQuestion < questions.length) {
                    showQuestion();
                } else {
                    userScores.put(currentUser, score);
                    window.setScene(resultScene);
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an answer.");
            }
        });

        bookmarkButton.setOnAction(e -> showAlert(Alert.AlertType.INFORMATION, "Bookmark", "Question bookmarked!"));

        return new Scene(examLayout, 500, 400);
    }

    private Scene createResultScene() {
        VBox resultLayout = new VBox(10);
        resultLayout.setPadding(new Insets(20));
        resultLayout.setStyle("-fx-background-color: #E8F8F5;");

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-text-fill: #117864; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #1A5276; -fx-font-size: 14px;");

        Button backButton = new Button("Back to Login");
        backButton.setStyle("-fx-background-color: #5DADE2; -fx-text-fill: white;");

        backButton.setOnAction(e -> window.setScene(loginScene));

        resultLayout.getChildren().addAll(resultLabel, messageLabel, backButton);

        resultScene = new Scene(resultLayout, 400, 250);

        // Update the result label dynamically
        resultScene.setOnShowing(e -> {
            resultLabel.setText("Congratulations, " + currentUser + "!");
            resultLabel.setText(resultLabel.getText() + "\nYou scored " + score + " out of " + questions.length + ".");

            if (score == questions.length) {
                messageLabel.setText("Excellent! You got a perfect score. Keep it up!");
            } else if (score >= (questions.length / 2)) {
                messageLabel.setText("Good job! You're doing great. Keep practicing to improve further.");
            } else {
                messageLabel.setText("Don't worry! Keep learning and trying, and you'll improve!");
            }
        });

        return resultScene;
    }

    private Scene createAdminScene() {
        VBox adminLayout = new VBox(10);
        adminLayout.setPadding(new Insets(20));
        adminLayout.setStyle("-fx-background-color: #FCF3CF;");

        Label adminLabel = new Label("Student Scores:");
        adminLabel.setStyle("-fx-text-fill: #6E2C00; -fx-font-size: 16px;");

        TextArea scoreArea = new TextArea();
        scoreArea.setEditable(false);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #F39C12; -fx-text-fill: white;");

        Button refreshButton = new Button("Refresh Scores");
        refreshButton.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white;");

        refreshButton.setOnAction(e -> {
            StringBuilder scores = new StringBuilder();
            for (Map.Entry<String, Integer> entry : userScores.entrySet()) {
                scores.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            scoreArea.setText(scores.toString());
        });

        backButton.setOnAction(e -> window.setScene(loginScene));

        adminLayout.getChildren().addAll(adminLabel, scoreArea, refreshButton, backButton);

        return new Scene(adminLayout, 400, 300);
    }

    private Scene createInChargeScene() {
        VBox inChargeLayout = new VBox(10);
        inChargeLayout.setPadding(new Insets(20));
        inChargeLayout.setStyle("-fx-background-color: #EAECEE;");

        Label inChargeLabel = new Label("In-Charge Dashboard");
        inChargeLabel.setStyle("-fx-text-fill: #1C2833; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button viewScoresButton = new Button("View User Scores");
        viewScoresButton.setStyle("-fx-background-color: #2874A6; -fx-text-fill: white;");

        Button manageQuestionsButton = new Button("Manage Questions");
        manageQuestionsButton.setStyle("-fx-background-color: #D35400; -fx-text-fill: white;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #F39C12; -fx-text-fill: white;");

        viewScoresButton.setOnAction(e -> {
            StringBuilder scores = new StringBuilder();
            for (Map.Entry<String, Integer> entry : userScores.entrySet()) {
                scores.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            showAlert(Alert.AlertType.INFORMATION, "User Scores", scores.toString());
        });

        manageQuestionsButton.setOnAction(e -> showAlert(Alert.AlertType.INFORMATION, "Manage Questions", "Here you can add or remove questions."));

        backButton.setOnAction(e -> window.setScene(loginScene));

        inChargeLayout.getChildren().addAll(inChargeLabel, viewScoresButton, manageQuestionsButton, backButton);

        return new Scene(inChargeLayout, 400, 300);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
