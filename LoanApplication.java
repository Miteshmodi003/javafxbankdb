import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoanApplication extends Application {
	public static int custid;
	CustomerPersister cus = new CustomerPersister();

	@Override
	public void start(Stage primaryStage1) {
		primaryStage1.setTitle("Loan Application");
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(20);
		pane.setVgap(20);
		pane.setPadding(new Insets(25, 25, 25, 25));
		Scene scene = new Scene(pane, 700, 700);

		Text sceneTitle = new Text("Loan Application");
		sceneTitle.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 25));
		pane.add(sceneTitle, 0, 0, 2, 1);
		Label total = new Label("Loan Amount:");
		pane.add(total, 0, 1);
		TextField totalField = new TextField();
		pane.add(totalField, 1, 1);

		Label months = new Label("Months:");
		pane.add(months, 0, 2);
		TextField monthsField = new TextField();
		pane.add(monthsField, 1, 2);

		Label creditScore = new Label("Credit Score: ");
		pane.add(creditScore, 0, 3);
		TextField creditField = new TextField();
		pane.add(creditField, 1, 3);

		Button calculateButton = new Button("Confirm");
		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		hbox.getChildren().add(calculateButton);
		pane.add(hbox, 1, 4);

		Button logout = new Button("Log Out");
		HBox hbox1 = new HBox(10);
		hbox.setAlignment(Pos.BOTTOM_LEFT);
		hbox1.getChildren().add(logout);
		pane.add(hbox1, 1, 5);

		Button goBack = new Button("Go Back");
		HBox hbox2 = new HBox(10);
		hbox2.setAlignment(Pos.BOTTOM_LEFT);
		hbox2.getChildren().add(goBack);
		pane.add(hbox2, 2, 5);

		Button makePayment = new Button("Make Payment");
		HBox hbox3 = new HBox(10);
		hbox3.setAlignment(Pos.BOTTOM_LEFT);
		hbox3.getChildren().add(goBack);
		pane.add(hbox3, 2, 4);

		Text yearlyInterestRate = new Text(); // Yearly Interest rate
		pane.add(yearlyInterestRate, 1, 7);

		Text monthlyInterestRate = new Text(); // Monthly Interest rate
		pane.add(monthlyInterestRate, 1, 8);

		Text monthlyPayment = new Text();
		pane.add(monthlyPayment, 1, 9);

		Text interestIncurred = new Text();
		pane.add(interestIncurred, 1, 10);

		Text totalAmtToBePaid = new Text();
		pane.add(totalAmtToBePaid, 1, 11);

		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			// Override the handle method
			public void handle(ActionEvent e) {
				Login login = new Login();
				primaryStage1.close();
				try {
					login.start(primaryStage1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		goBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				AccountsMain ac = new AccountsMain();
				primaryStage1.close();
				try {
					ac.start(primaryStage1);
					AccountsMain.custid = LoanApplication.custid;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		makePayment.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				MakePayment mk = new MakePayment();

			}
		});

		calculateButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if (cus.hasPreviousLoan(custid)) {
					totalAmtToBePaid.setText("You already have previous loan pending to be paid!");
					return;
				}

				// Interest Calculation Formula
				double loanAmount = Double.parseDouble(totalField.getText());
				int months_needed = Integer.parseInt(monthsField.getText());
				int creditScore_num = Integer.parseInt(creditField.getText());

				if (creditScore_num < 500) {
					double yearlyInterest_rate = 10.00;

					double monthlyInterestrate = Math.round(yearlyInterest_rate * 100.00 / 12.00) / 100.00;
					double monthlyInstallment = (Math.round(loanAmount * monthlyInterestrate * 100.00 / 100.00)
							/ 100.00) + Math.round(loanAmount * 100.0 / months_needed) / 100.0;
					double total_Interest_Amt_Incurred = Math
							.round((loanAmount * monthlyInterestrate / 100 * months_needed) * 100.0) / 100.0;
					double total_Amt_To_BePaid = total_Interest_Amt_Incurred + loanAmount;

					cus.loanApplication(custid, loanAmount, months_needed, months_needed, monthlyInterestrate,
							yearlyInterest_rate, total_Interest_Amt_Incurred, total_Amt_To_BePaid, total_Amt_To_BePaid,
							creditScore_num);

					// Display Estimation
					yearlyInterestRate.setText("Your yearly Interest Rate will be " + yearlyInterest_rate + " %");
					monthlyInterestRate.setText("Your monthly Interest Rate will be " + monthlyInterestrate + " %");
					monthlyPayment.setText("Monthly Installment will be " + monthlyInstallment + " USD");
					interestIncurred.setText("Total interest incurred at the end of " + months_needed + " months is "
							+ total_Interest_Amt_Incurred + " USD");
					totalAmtToBePaid.setText("Total Amount to be paid at the end of " + months_needed + " months is "
							+ total_Amt_To_BePaid + " USD");
				}

				else if (creditScore_num >= 500 && creditScore_num < 600) {
					double yearlyInterest_rate = 8.00;
					/*
					 * double monthlyInterestrate = yearlyInterest_rate / 12;
					 * double monthlyInstallment = ((loanAmount *
					 * monthlyInterestrate) / 100) + (loanAmount /
					 * months_needed); double total_Interest_Amt_Incurred =
					 * ((loanAmount * monthlyInterestrate) / 100) *
					 * months_needed;
					 */
					double monthlyInterestrate = Math.round(yearlyInterest_rate * 100.00 / 12.00) / 100.00;
					double monthlyInstallment = (Math.round(loanAmount * monthlyInterestrate * 100.00 / 100.00)
							/ 100.00) + Math.round(loanAmount * 100.0 / months_needed) / 100.0;
					double total_Interest_Amt_Incurred = Math
							.round((loanAmount * monthlyInterestrate / 100 * months_needed) * 100.0) / 100.0;
					double total_Amt_To_BePaid = total_Interest_Amt_Incurred + loanAmount;

					cus.loanApplication(custid, loanAmount, months_needed, months_needed, monthlyInterestrate,
							yearlyInterest_rate, total_Interest_Amt_Incurred, total_Amt_To_BePaid, total_Amt_To_BePaid,
							creditScore_num);

					// Display Estimation
					yearlyInterestRate.setText("Your yearly Interest Rate will be " + yearlyInterest_rate + " %");
					monthlyInterestRate.setText("Your monthly Interest Rate will be " + monthlyInterestrate + " %");
					monthlyPayment.setText("Monthly Installment will be " + monthlyInstallment + " USD");
					interestIncurred.setText("Total interest incurred at the end of " + months_needed + " months is "
							+ total_Interest_Amt_Incurred + " USD");
					totalAmtToBePaid.setText("Total Amount to be paid at the end of " + months_needed + " months is "
							+ total_Amt_To_BePaid + " USD");
				}

				else if (creditScore_num >= 600 && creditScore_num < 700) {
					double yearlyInterest_rate = 6.00;
					double monthlyInterestrate = Math.round(yearlyInterest_rate * 100.00 / 12.00) / 100.00;
					double monthlyInstallment = (Math.round(loanAmount * monthlyInterestrate * 100.00 / 100.00)
							/ 100.00) + Math.round(loanAmount * 100.0 / months_needed) / 100.0;
					double total_Interest_Amt_Incurred = Math
							.round((loanAmount * monthlyInterestrate / 100 * months_needed) * 100.0) / 100.0;
					double total_Amt_To_BePaid = total_Interest_Amt_Incurred + loanAmount;

					cus.loanApplication(custid, loanAmount, months_needed, months_needed, monthlyInterestrate,
							yearlyInterest_rate, total_Interest_Amt_Incurred, total_Amt_To_BePaid, total_Amt_To_BePaid,
							creditScore_num);

					// Display Estimation
					yearlyInterestRate.setText("Your yearly Interest Rate will be " + yearlyInterest_rate + " %");
					monthlyInterestRate.setText("Your monthly Interest Rate will be " + monthlyInterestrate + " %");
					monthlyPayment.setText("Monthly Installment will be " + monthlyInstallment + " USD");
					interestIncurred.setText("Total interest incurred at the end of " + months_needed + " months is "
							+ total_Interest_Amt_Incurred + " USD");
					totalAmtToBePaid.setText("Total Amount to be paid at the end of " + months_needed + " months is "
							+ total_Amt_To_BePaid + " USD");
				}

				else {
					double yearlyInterest_rate = 4.50;
					double monthlyInterestrate = Math.round(yearlyInterest_rate * 100.00 / 12.00) / 100.00;
					double monthlyInstallment = (Math.round(loanAmount * monthlyInterestrate * 100.00 / 100.00)
							/ 100.00) + Math.round(loanAmount * 100.0 / months_needed) / 100.0;
					double total_Interest_Amt_Incurred = Math
							.round((loanAmount * monthlyInterestrate / 100 * months_needed) * 100.0) / 100.0;
					double total_Amt_To_BePaid = total_Interest_Amt_Incurred + loanAmount;

					cus.loanApplication(custid, loanAmount, months_needed, months_needed, monthlyInterestrate,
							yearlyInterest_rate, total_Interest_Amt_Incurred, total_Amt_To_BePaid, total_Amt_To_BePaid,
							creditScore_num);

					// Display Estimation
					yearlyInterestRate.setText("Your yearly Interest Rate will be " + yearlyInterest_rate + " %");
					monthlyInterestRate.setText("Your monthly Interest Rate will be " + monthlyInterestrate + " %");
					monthlyPayment.setText("Monthly Installment will be " + monthlyInstallment + " USD");
					interestIncurred.setText("Total interest incurred at the end of " + months_needed + " months is "
							+ total_Interest_Amt_Incurred + " USD");
					totalAmtToBePaid.setText("Total Amount to be paid at the end of " + months_needed + " months is "
							+ total_Amt_To_BePaid + " USD");
				}
			}
		});
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * The main() method is ignored in correctly deployed JavaFX application.
	 * main() serves only as fallback in case the application can not be
	 * launched through deployment artifacts, e.g., in IDEs with limited FX
	 * support. NetBeans ignores main().
	 *
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}