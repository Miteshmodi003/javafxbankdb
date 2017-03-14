import java.awt.Rectangle;

import javax.xml.soap.Text;

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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RegisterationPage extends Application {
	@Override
	// Override the start method in the Application class
	public void start(Stage primaryStage) {
		// Create a pane and set its properties
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(10, 25, 25, 25));
		pane.setHgap(5.5);
		pane.setVgap(5.5);

		// Place nodes in the pane
		Label sceneTitle = new Label("Sign up here!");
		sceneTitle.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 25));
		pane.add(sceneTitle, 0, 0, 2, 1);
		pane.add(new Label("First Name:"), 0, 1);
		TextField firstName = new TextField();
		pane.add(firstName, 1, 1);

		pane.add(new Label("Middle Name:"), 0, 2);
		TextField middleName = new TextField();
		pane.add(middleName, 1, 2);

		pane.add(new Label("Last Name:"), 0, 3);
		TextField lastName = new TextField();
		pane.add(lastName, 1, 3);

		pane.add(new Label("Email Address:"), 0, 4);
		TextField emailTextField = new TextField();
		emailTextField.setPrefWidth(600);
		pane.add(emailTextField, 1, 4);

		pane.add(new Label("User name:"), 0, 5);
		TextField userName = new TextField();
		pane.add(userName, 1, 5);

		pane.add(new Label("Password"), 0, 6);
		PasswordField pwBox = new PasswordField();
		pane.add(pwBox, 1, 6);

		Button btSub = new Button("Submit");
		// Fix the button position
		pane.add(btSub, 1, 7);
		GridPane.setHalignment(btSub, HPos.LEFT);

		pane.add(new Label("Already a member? Sign in here"), 1, 8);
		Button btLog = new Button("Sign in");
		pane.add(btLog, 1, 9);

		btSub.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			// Override the handle method
			public void handle(ActionEvent e) {
				String fN = firstName.getText();
				String mN = middleName.getText();
				String lN = lastName.getText();
				String email = emailTextField.getText();
				String usrName = userName.getText();
				String pW = pwBox.getText();

				int resultCustomer = new CustomerPersister().insertCustomer(fN, mN, lN, email, usrName, pW);
				
				if (resultCustomer == 0)
					System.out.println("Information Submitted error");
				else
					System.out.println("Information Submitted Succesfully");

			}
		});

		btLog.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Login loginPage = new Login();
				primaryStage.close();
				try {
					loginPage.start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		Scene scene = new Scene(pane);
		primaryStage.setTitle("Registeration Page"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		launch(args);
	}

}
