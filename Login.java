import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class Login extends Application {

	Scene scene, scene2;
	String userNameText;
	String userNamePassword;

	public void start(Stage primaryStage) throws Exception {

		ImageView icon = new ImageView(
				new Image(getClass().getResourceAsStream("/res/zillionSparks.png"), 400, 300, true, true));

		Popup pop = new Popup();
		pop.setX(750);
		pop.setY(600);
		VBox popPane = new VBox(10);
		popPane.setSpacing(2);
		popPane.setAlignment(Pos.CENTER);
		Button popButton = new Button("Acknowlege");
		Text popText = new Text(0, 10, "The Password or username you have entered is incorrect");
		popText.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));
		popText.setFill(Color.BLACK);
		popText.setUnderline(true);
		popPane.getChildren().addAll(popText, popButton);
		pop.getContent().addAll(popPane);

		Button btLogin = new Button("Login");
		TextField userID = new TextField();
		PasswordField userPass = new PasswordField();
		Label userName = new Label("User Name");
		Label passWord = new Label("Password:");

		// Create the Header Box Fast Cash icon
		HBox hb = new HBox();
		hb.setPadding(new Insets(20, 20, 20, 30));
		HBox hBox = new HBox(10);
		hBox.setPadding(new Insets(20, 20, 20, 130.5));

		hBox.getChildren().add(icon);

		// Create Main Panel

		GridPane pane = new GridPane();

		// pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(20, 20, 20, 20));
		// pane.setPadding(new Insets(10, 0, 60, 175));
		pane.setHgap(10);
		pane.setVgap(10);
		// label position for "Already a member?"
		pane.add(new Label("Already a member? Sign in here."), 5, 0);
		// label position for user name
		pane.add(userName, 4, 1);
		// label position for password
		pane.add(passWord, 4, 2);
		// login button position
		pane.add(btLogin, 5, 3);
		// user name text field position
		pane.add(userID, 5, 1);
		// password field position
		pane.add(userPass, 5, 2);

		// Label for "Create a new account"
		pane.add(new Label("New User? Create a new account here!"), 5, 4);
		// Sign up Button created
		Button btSignUp = new Button("Sign up here!");
		// Fix the button position
		pane.add(btSignUp, 5, 5);
		GridPane.setHalignment(btSignUp, HPos.LEFT);

		// Create a button-click to go to Loan Calculator
		Button btLoanCalc = new Button("Loan Estimator");
		// Add a new label into pane for "Need to estimate a loan?"
		pane.add(new Label("Need to estimate a loan?"), 5, 6);
		// Fix the button position: loan calculator
		pane.add(btLoanCalc, 5, 7);
		GridPane.setHalignment(btLoanCalc, HPos.LEFT);

		// BorderPane creation
		BorderPane borders = new BorderPane();
		borders.setTop(hBox);
		borders.setCenter(pane);

		// Event handling for login button click
		btLogin.setOnAction((e) -> {
			String uName = userID.getText();
			String pWord = userPass.getText();

			int resultLogin = new CustomerPersister().checkLogin(uName, pWord);

			if (resultLogin != 0) {
				try {
					AccountsMain am = new AccountsMain();
					primaryStage.close();
					am.start(primaryStage);
					AccountsMain.custid = resultLogin;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				pop.show(primaryStage);
			}

		});

		// click on sign up button and go to registration page
		btSignUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			// Override the handle method
			public void handle(ActionEvent e) {
				System.out.println("Loading a sign up page...");
				RegisterationPage goToRegPage = new RegisterationPage();
				primaryStage.close();
				goToRegPage.start(primaryStage);
			}
		});

		// Loan calculator button-click event handling
		btLoanCalc.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			// Override the handle method
			public void handle(ActionEvent e) {
				LoanCalculator lc = new LoanCalculator();
				primaryStage.close();
				lc.start(primaryStage);
			}
		});

		popButton.setOnAction(e -> pop.hide());

		Scene scene = new Scene(borders, 900, 900);
		primaryStage.setTitle("Fast Cash Application"); // Set title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
