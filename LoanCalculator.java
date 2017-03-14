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

public class LoanCalculator extends Application {

	@Override
	public void start(Stage primaryStage1) {
		primaryStage1.setTitle("Interest Calculator");
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(20);
		pane.setVgap(20);
		pane.setPadding(new Insets(25, 25, 25, 25));
		Scene scene = new Scene(pane, 500, 500);

		Text sceneTitle = new Text("Loan Estimator");
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

		Button calculateButton = new Button("Calculate");
		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		hbox.getChildren().add(calculateButton);
		pane.add(hbox, 1, 4);

		/*
		 * Text interestMsg = new Text(); pane.add(interestMsg, 1, 6);
		 */

		Text yearlyInterestRate = new Text(); // Yearly Interest rate
		pane.add(yearlyInterestRate, 1, 6);

		Text monthlyInterestRate = new Text(); // Monthly Interest rate
		pane.add(monthlyInterestRate, 1, 7);

		Text monthlyPayment = new Text();
		pane.add(monthlyPayment, 1, 8);

		Text interestIncurred = new Text();
		pane.add(interestIncurred, 1, 9);

		Text totalAmtToBePaid = new Text();
		pane.add(totalAmtToBePaid, 1, 10);

		calculateButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				// Interest Calculation Formula
				Double loanAmount = Double.parseDouble(totalField.getText());
				// Double interest = (Double.parseDouble(percentField.getText())
				// * Double.parseDouble(monthField.getText()))/100;

				Double months_needed = Double.parseDouble(monthsField.getText());
				Double creditScore_num = Double.parseDouble(creditField
						.getText());

				if (creditScore_num < 500) {
					double yearlyInterest_rate = 10.00;
					double monthlyInterestrate = yearlyInterest_rate / 12;
					double monthlyInstallment = ((loanAmount * monthlyInterestrate) / 100)
							+ (loanAmount / months_needed);
					double total_Interest_Amt_Incurred = ((loanAmount * monthlyInterestrate) / 100)
							* months_needed;
					double total_Amt_To_BePaid = total_Interest_Amt_Incurred
							+ loanAmount;
					yearlyInterestRate
							.setText("Your yearly Interest Rate will be "
									+ yearlyInterest_rate + " %");
					monthlyInterestRate
							.setText("Your monthly Interest Rate will be "
									+ monthlyInterestrate + " %");
					monthlyPayment.setText("Monthly Installment will be "
							+ monthlyInstallment + " USD");
					interestIncurred
							.setText("Total interest incurred at the end of "
									+ months_needed + " months is "
									+ total_Interest_Amt_Incurred + " USD");
					totalAmtToBePaid
							.setText("Total Amount to be paid at the end of "
									+ months_needed + " months is "
									+ total_Amt_To_BePaid + " USD");
				}

				else if (creditScore_num >= 500 && creditScore_num < 600) {
					double yearlyInterest_rate = 8.00;
					double monthlyInterestrate = yearlyInterest_rate / 12;
					double monthlyInstallment = ((loanAmount * monthlyInterestrate) / 100)
							+ (loanAmount / months_needed);
					double total_Interest_Amt_Incurred = ((loanAmount * monthlyInterestrate) / 100)
							* months_needed;
					double total_Amt_To_BePaid = total_Interest_Amt_Incurred
							+ loanAmount;
					yearlyInterestRate
							.setText("Your yearly Interest Rate will be "
									+ yearlyInterest_rate + " %");
					monthlyInterestRate
							.setText("Your monthly Interest Rate will be "
									+ monthlyInterestrate + " %");
					monthlyPayment.setText("Monthly Installment will be "
							+ monthlyInstallment + " USD");
					interestIncurred
							.setText("Total interest incurred at the end of "
									+ months_needed + " months is "
									+ total_Interest_Amt_Incurred + " USD");
					totalAmtToBePaid
							.setText("Total Amount to be paid at the end of "
									+ months_needed + " months is "
									+ total_Amt_To_BePaid + " USD");
				}

				else if (creditScore_num >= 600 && creditScore_num < 700) {
					double yearlyInterest_rate = 6.00;
					double monthlyInterestrate = yearlyInterest_rate / 12;
					double monthlyInstallment = ((loanAmount * monthlyInterestrate) / 100)
							+ (loanAmount / months_needed);
					double total_Interest_Amt_Incurred = ((loanAmount * monthlyInterestrate) / 100)
							* months_needed;
					double total_Amt_To_BePaid = total_Interest_Amt_Incurred
							+ loanAmount;
					yearlyInterestRate
							.setText("Your yearly Interest Rate will be "
									+ yearlyInterest_rate + " %");
					monthlyInterestRate
							.setText("Your monthly Interest Rate will be "
									+ monthlyInterestrate + " %");
					monthlyPayment.setText("Monthly Installment will be "
							+ monthlyInstallment + " USD");
					interestIncurred
							.setText("Total interest incurred at the end of "
									+ months_needed + " months is "
									+ total_Interest_Amt_Incurred + " USD");
					totalAmtToBePaid
							.setText("Total Amount to be paid at the end of "
									+ months_needed + " months is "
									+ total_Amt_To_BePaid + " USD");
				}

				else {
					double yearlyInterest_rate = 4.50;
					double monthlyInterestrate = yearlyInterest_rate / 12;
					double monthlyInstallment = ((loanAmount * monthlyInterestrate) / 100)
							+ (loanAmount / months_needed);
					double total_Interest_Amt_Incurred = ((loanAmount * monthlyInterestrate) / 100)
							* months_needed;
					double total_Amt_To_BePaid = total_Interest_Amt_Incurred
							+ loanAmount;
					yearlyInterestRate
							.setText("Your yearly Interest Rate will be "
									+ yearlyInterest_rate + " %");
					monthlyInterestRate
							.setText("Your monthly Interest Rate will be "
									+ monthlyInterestrate + " %");
					monthlyPayment.setText("Monthly Installment will be "
							+ monthlyInstallment + " USD");
					interestIncurred
							.setText("Total interest incurred at the end of "
									+ months_needed + " months is "
									+ total_Interest_Amt_Incurred + " USD");
					totalAmtToBePaid
							.setText("Total Amount to be paid at the end of "
									+ months_needed + " months is "
									+ total_Amt_To_BePaid + " USD");
				}

			}
		});
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}