
import java.awt.event.ActionListener;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AccountsMain extends Application implements ActionListener {
	Scene scene;

	public static int custid;

	public void start(Stage primaryStage) throws Exception {

		NoneSelected none2 = new NoneSelected("None", 0000.0, 0.00, 0.0, 0.00);

		CustomerPersister cus = new CustomerPersister();

		Label selected = new Label("Account Selected");

		// Withdraw Button
		Button withdraw = new Button("Withdraw");
		// Deposit Button
		Button deposit = new Button("Deposit");

		Button appLoan = new Button("Loan Application");
		Button payLoan = new Button("Make Payment");
		// Logout Button
		Button logout = new Button("Log Out");

		Label balance = new Label("Balance:");
		Label money = new Label("0.00 $");
		Label amount = new Label("Amount");
		TextField quantity = new TextField();

		ImageView icon = new ImageView(
				new Image(getClass().getResourceAsStream("/res/zillionSparks.png"), 400, 300, true, true));
		ImageView icon2 = new ImageView(
				new Image(getClass().getResourceAsStream("/res/zillion.png"), 300, 300, true, true));

		ChoiceBox theAccounts = new ChoiceBox(
				FXCollections.observableArrayList("None Selected", "Saving Account", "Checking Account"));

		HBox hBox = new HBox(10);
		hBox.setMinWidth(400);
		hBox.setMinHeight(100);
		hBox.setSpacing(1);
		hBox.setAlignment(Pos.TOP_CENTER);
		hBox.getChildren().add(icon);

		// Main Panel

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setPadding(new Insets(10, 25, 25, 25));
		grid.setVgap(10);
		grid.setHgap(10);
		grid.add(selected, 3, 0);
		grid.add(balance, 2, 1);
		grid.add(amount, 2, 2);
		grid.add(withdraw, 2, 4);
		grid.add(money, 3, 1);
		grid.add(quantity, 3, 2);
		grid.add(deposit, 3, 4);
		grid.add(appLoan, 2, 15);
		grid.add(payLoan, 2, 16);
		grid.add(logout, 2, 17);

		// left pane

		VBox leftPanel = new VBox(10);
		leftPanel.setPadding(new Insets(30));
		leftPanel.setSpacing(20);
		leftPanel.setAlignment(Pos.TOP_CENTER);
		leftPanel.getChildren().add(new Label("Select Account"));
		leftPanel.getChildren().add(theAccounts);
		// all the action listeners here for the components

		// this is the code for the withdraw button
		withdraw.setOnAction((ActionEvent e) -> {

			if (theAccounts.getValue().toString().equalsIgnoreCase("Saving Account")) {
				try {
					cus.withdrawSavings(custid, Double.parseDouble(quantity.getText()));
					money.setText(cus.getSavingsBalance(custid) + " $");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (theAccounts.getValue().toString().equalsIgnoreCase("Checking Account")) {

				try {
					cus.withdrawCheckings(custid, Double.parseDouble(quantity.getText()));
					money.setText(cus.getCheckingsBalance(custid) + " $");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (theAccounts.getValue().toString().equalsIgnoreCase("None Selected")) {
				try {
					money.setText("Select Valid Account");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		// Deposit Button Click Event
		deposit.setOnAction((ActionEvent e) -> {

			if (theAccounts.getValue().toString().equalsIgnoreCase("Saving Account")) {
				cus.depositSavings(custid, Double.parseDouble(quantity.getText()));
				money.setText(cus.getSavingsBalance(custid) + " $");
			} else if (theAccounts.getValue().toString().equalsIgnoreCase("Checking Account")) {
				cus.depositCheckings(custid, Double.parseDouble(quantity.getText()));
				money.setText(cus.getCheckingsBalance(custid) + " $");
			} else if (theAccounts.getValue().toString().equalsIgnoreCase("None Selected")) {
				try {
					money.setText("Select Valid Account");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		// this is the code for the drop down menu

		theAccounts.getSelectionModel().selectFirst();
		theAccounts.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
					selected.setText(newValue.toString());
					if (newValue.toString().equalsIgnoreCase("Saving Account")) {
						money.setText(cus.getSavingsBalance(custid) + " $");
					} else if (newValue.toString().equalsIgnoreCase("Checking Account")) {
						money.setText(cus.getCheckingsBalance(custid) + " $");
					} else if (newValue.toString().equalsIgnoreCase("None Selected")) {
						money.setText(none2.getBalance() + " $");
					}
				});

		HBox bottomPane = new HBox(10);
		bottomPane.setMinWidth(400);
		bottomPane.setMinHeight(100);
		bottomPane.setSpacing(1);
		bottomPane.setAlignment(Pos.TOP_CENTER);
		bottomPane.getChildren().add(icon2);

		BorderPane borders = new BorderPane();
		borders.setTop(hBox);
		borders.setRight(grid);
		borders.setLeft(leftPanel);
		borders.setBottom(bottomPane);

		// GO TO LOAN PAYMENT ACTIVITY
		payLoan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			// Override the handle method
			public void handle(ActionEvent e) {
				MakePayment makePay = new MakePayment();
				primaryStage.close();
				try {
					makePay.start(primaryStage);
					MakePayment.custid = AccountsMain.custid;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		appLoan.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				LoanApplication loanApp = new LoanApplication();
				primaryStage.close();
				try {
					loanApp.start(primaryStage);
					LoanApplication.custid = AccountsMain.custid;
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		// LOG OUT BUTTON CLICK EVENT
		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			// Override the handle method
			public void handle(ActionEvent e) {
				Login login = new Login();
				primaryStage.close();
				try {
					login.start(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Scene scene = new Scene(borders, 900, 900);
		primaryStage.setTitle("ZillionSparks Application"); // Set title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show();
	}
	// }

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void actionPerformed(java.awt.event.ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
