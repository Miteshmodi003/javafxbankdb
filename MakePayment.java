import java.awt.event.ActionListener;
import java.security.GeneralSecurityException;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MakePayment extends Application implements ActionListener {
	Scene scene;
	public static int custid;

	@Override
	public void start(Stage primaryStage) throws Exception {

		CustomerPersister cus = new CustomerPersister();
		// TODO Auto-generated method stub
		Text t = new Text("Loan Payment");
		t.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
		t.setFill(Color.BLUE);

		Button btnConfirm = new Button("Confirm Payment");
		TextField payAmt = new TextField();
		Label userName = new Label("Amount");
		// LoanID, Loan Amount
		Loan loan = new Loan("Nov16MiteshLoan", 12400.0, "07DEE003", "D235", 12, 600);
		NoneSelected none2 = new NoneSelected("None", 0000.0, 0.00, 0.0, 0.00);
		// SavingAccount savings2 = new SavingAccount("C124", 1000.0, 0.04,
		// 5.0);
		// CheckingAccount checking2 = new CheckingAccount("D235", 1200.0, 30.0,
		// 1000.0, 12.0);

		Label selected = new Label("Account Selected");
		// Withdraw Button
		Button makeAPayBtn = new Button("Make Payment");
		// Deposit Button
		Button goBackBtn = new Button("Go Back");

		// Logout Button
		Button logout = new Button("Log Out");

		Label balance = new Label("Balance:");
		Label money = new Label("0.00 $");
		Label amount = new Label("Amount");
		TextField quantity = new TextField();
		quantity.setMinWidth(427);

		ImageView icon = new ImageView(
				new Image(getClass().getResourceAsStream("/res/zillionSparks.png"), 400, 300, true, true));
		ImageView icon2 = new ImageView(
				new Image(getClass().getResourceAsStream("/res/zillion.png"), 300, 300, true, true));

		ChoiceBox theAccounts = new ChoiceBox(
				FXCollections.observableArrayList("None Selected", "Saving Account", "Checking Account"));

		// FastCash Image Position
		HBox hBox = new HBox(10);
		hBox.setMinWidth(400);
		hBox.setMinHeight(100);
		hBox.setSpacing(1);
		hBox.setAlignment(Pos.TOP_CENTER);
		hBox.getChildren().add(icon);

		GridPane pane = new GridPane();
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setPadding(new Insets(10, 25, 25, 25));
		pane.setVgap(10);
		pane.setHgap(10);
		pane.add(selected, 3, 0);
		pane.add(balance, 2, 1);
		pane.add(amount, 2, 2);
		pane.add(makeAPayBtn, 3, 4);
		pane.add(money, 3, 1);
		pane.add(quantity, 3, 2);
		pane.add(goBackBtn, 3, 5);
		pane.add(logout, 3, 28);

		Text paidSuccessfully = new Text();
		pane.add(paidSuccessfully, 3, 7);

		Text totalAmtToBePaid = new Text();
		pane.add(totalAmtToBePaid, 3, 6);

		// left pane
		VBox leftPanel = new VBox(10);
		leftPanel.setPadding(new Insets(30));
		leftPanel.setSpacing(20);
		leftPanel.setAlignment(Pos.TOP_CENTER);
		leftPanel.getChildren().add(new Label("Select Account"));
		leftPanel.getChildren().add(theAccounts);

		makeAPayBtn.setOnAction((ActionEvent e) -> {

			if (theAccounts.getValue().toString().equalsIgnoreCase("Saving Account")) {
				try {
					cus.withdrawSavings(custid, Double.parseDouble(quantity.getText()));
					money.setText(cus.getSavingsBalance(custid) + " $");
					double paidAmount = Double.parseDouble(quantity.getText());
					cus.paidInstallment(custid, paidAmount);
					double remainingAmt = cus.getLoanBalance(custid);

					boolean remainingMonths = cus.setRemainingMonths(custid, 1);
					int updatedMonths = cus.getRemainingMonths(custid);
					double avgAmount_To_Be_Paid = Math.round(remainingAmt / updatedMonths *100.0)/100.0;
					totalAmtToBePaid.setText(
							"Remaining Amount to be Paid " + remainingAmt + " $\nYour Average Pay per Month should be "
									+ avgAmount_To_Be_Paid + " $\nnow onwards within " + updatedMonths + " months.");

					paidSuccessfully.setText("Your payment transaction was executed successfully!\nThank you!");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (theAccounts.getValue().toString().equalsIgnoreCase("Checking Account")) {

				try {
					cus.withdrawCheckings(custid, Double.parseDouble(quantity.getText()));
					money.setText(cus.getCheckingsBalance(custid) + " $");

					double paidAmount = Double.parseDouble(quantity.getText());
					cus.paidInstallment(custid, paidAmount);
					double remainingAmt = cus.getLoanBalance(custid);

					boolean remainingMonths = cus.setRemainingMonths(custid, 1);
					int updatedMonths = cus.getRemainingMonths(custid);
					double avgAmount_To_Be_Paid = Math.round(remainingAmt / updatedMonths *100.0)/100.0;
					totalAmtToBePaid.setText(
							"Remaining Amount to be Paid " + remainingAmt + " $\n Your Average Pay per Month should be "
									+ avgAmount_To_Be_Paid + " $\nnow onwards within " + updatedMonths + " months.");
					paidSuccessfully.setText("Your payment transaction was executed successfully!\nThank you!");
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
		borders.setRight(pane);
		borders.setLeft(leftPanel);
		borders.setBottom(bottomPane);

		goBackBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			// Override the handle method
			public void handle(ActionEvent e) {
				AccountsMain ac = new AccountsMain();
				primaryStage.close();
				try {
					ac.start(primaryStage);
					AccountsMain.custid = MakePayment.custid;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

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

		Scene scene = new Scene(borders, 1000, 1000);
		primaryStage.setTitle("Make Loan Payment"); // Set title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
