import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ATMGUI extends Application {

    private BankAccount userAccount;
    private ATM atm;

    private TextField amountField;
    private Label balanceLabel;
    private Label messageLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        userAccount = new BankAccount(1000); // Example starting balance
        atm = new ATM(userAccount);

        primaryStage.setTitle("ATM Machine");

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        // Top Pane - Title
        HBox topPane = new HBox();
        topPane.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(10));
        
        Label titleLabel = new Label("Welcome to ATM");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        topPane.getChildren().add(titleLabel);

        borderPane.setTop(topPane);

        // Center Pane - Buttons and Input
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        balanceLabel = new Label("Current Balance: R" + userAccount.getBalance());
        balanceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        GridPane.setConstraints(balanceLabel, 0, 0, 2, 1);
        grid.getChildren().add(balanceLabel);

        Label amountLabel = new Label("Amount:");
        GridPane.setConstraints(amountLabel, 0, 1);
        grid.getChildren().add(amountLabel);

        amountField = new TextField();
        GridPane.setConstraints(amountField, 1, 1);
        grid.getChildren().add(amountField);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button withdrawButton = new Button("Withdraw");
        withdrawButton.setOnAction(e -> withdrawAction());
        buttonBox.getChildren().add(withdrawButton);

        Button depositButton = new Button("Deposit");
        depositButton.setOnAction(e -> depositAction());
        buttonBox.getChildren().add(depositButton);

        Button checkBalanceButton = new Button("Check Balance");
        checkBalanceButton.setOnAction(e -> checkBalanceAction());
        buttonBox.getChildren().add(checkBalanceButton);

        grid.add(buttonBox, 0, 2, 2, 1);

        borderPane.setCenter(grid);

        // Bottom Pane - Message Area
        messageLabel = new Label("");
        messageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        messageLabel.setTextFill(Color.RED);
        messageLabel.setAlignment(Pos.CENTER);
        borderPane.setBottom(messageLabel);

        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void withdrawAction() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            atm.withdraw(amount);
            updateBalanceLabel();
            showMessage("Withdrawal of R" + amount + " successful.", false);
        } catch (NumberFormatException e) {
            showMessage("Invalid amount format. Please enter a valid number.", true);
        } catch (Exception e) {
            showMessage("Withdrawal failed. Insufficient funds or invalid amount.", true);
        }
    }

    private void depositAction() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            atm.deposit(amount);
            updateBalanceLabel();
            showMessage("Deposit of R" + amount + " successful.", false);
        } catch (NumberFormatException e) {
            showMessage("Invalid amount format. Please enter a valid number.", true);
        }
    }

    private void checkBalanceAction() {
        updateBalanceLabel();
        showMessage("Current balance: R" + userAccount.getBalance(), false);
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Current Balance: R" + userAccount.getBalance());
    }

    private void showMessage(String message, boolean isError) {
        if (isError) {
            messageLabel.setTextFill(Color.RED);
        } else {
            messageLabel.setTextFill(Color.GREEN);
        }
        messageLabel.setText(message);
    }
}
