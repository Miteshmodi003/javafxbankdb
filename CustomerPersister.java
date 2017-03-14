
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.DatabaseMetaData;

public class CustomerPersister {

	private final String databaseName = "javafx";
	private final String userName = "root";
	private final String password = "";
	private final String url = "jdbc:mysql://localhost/" + databaseName;

	public Connection createConnection() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, userName, password);
		return conn;
	}

	/**
	 * Create a customer and also corresponding saving accounts and checking
	 * accounts
	 * 
	 * @param firstName
	 * @param middleName
	 * @param lastname
	 * @param email
	 * @param username
	 * @param password
	 * @return customer if added successfully, else 0
	 */
	public int insertCustomer(String firstName, String middleName, String lastname, String email, String username,
			String password) {
		Connection connection;
		try {
			connection = createConnection();
			if (connection != null) {
				String query = "Insert into customers (first_name,middle_name,last_name,email,username,password) values(?,?,?,?,?,?)";

				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, firstName);
				ps.setString(2, middleName);
				ps.setString(3, lastname);
				ps.setString(4, email);
				ps.setString(5, username);
				ps.setString(6, password);
				ps.executeUpdate();

				query = "Select * from customers order by customerID desc";
				ps = connection.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				rs.next();
				int customerId = rs.getInt(1);
				createAccount(customerId, getNextCheckingAccountNumber(), 0, getNextSavingAccountNumber(), 0);

				return customerId;

			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Method for Creating Account soon as User hits Submit button
	private boolean createAccount(int cusID, String checkingAcc, double checkingBal, String savingAcc,
			double savingBal) {
		Connection connection;
		try {
			connection = createConnection();
			if (connection != null) {
				String query = "Insert into Account values(?,?,?,?,?)";

				PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, cusID);
				ps.setString(2, checkingAcc);
				ps.setDouble(3, checkingBal);
				ps.setString(4, savingAcc);
				ps.setDouble(5, savingBal);
				ps.executeUpdate();
				return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String getNextCheckingAccountNumber() {
		Connection connection;
		try {
			connection = createConnection();
			if (connection != null) {
				String query = "Select * from account order by customerID desc";
				PreparedStatement ps = connection.prepareStatement(query);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					String lastChecking = rs.getString("Checking_Account");
					return "C" + (Integer.parseInt(lastChecking.substring(1)) + 1);
				} else
					return "C1000";
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getNextSavingAccountNumber() {
		Connection connection;
		try {
			connection = createConnection();
			if (connection != null) {
				String query = "Select * from account order by customerID desc";
				PreparedStatement ps = connection.prepareStatement(query);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					String lastChecking = rs.getString("Saving_Account");
					return "S" + (Integer.parseInt(lastChecking.substring(1)) + 1);
				} else
					return "S1000";
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int checkLogin(String username, String password) {
		Connection connection;
		try {
			connection = createConnection();
			if (connection != null) {
				String query = "Select * from customers where username = ?";

				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, username);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {

					String actual = rs.getString("password");
					if (actual.equals(password)) {
						/*
						 * String query1 =
						 * "UPDATE customers SET online_status='1'";
						 * PreparedStatement ps1=
						 * connection.prepareStatement(query1);
						 * ps1.executeQuery();
						 */

						return rs.getInt("customerID");
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean depositCheckings(int custId, double amount) {

		Connection connection;
		DatabaseMetaData db;
		try {
			connection = createConnection();
			if (connection != null) {

				String query = "update account set Checking_Balance = ? where customerid = ?";
				PreparedStatement ps = connection.prepareStatement(query);

				ps.setDouble(1, getCheckingsBalance(custId) + amount);
				ps.setInt(2, custId);
				ps.executeUpdate();
				return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

		}
		return false;

	}

	public boolean withdrawCheckings(int custId, double amount) {
		double previous = getCheckingsBalance(custId);
		if (previous < amount)
			return false;
		else {
			Connection connection;
			try {
				connection = createConnection();
				if (connection != null) {
					String query = "update account set Checking_Balance = ? where customerid = ?";
					PreparedStatement ps = connection.prepareStatement(query);

					ps.setDouble(1, previous - amount);
					ps.setInt(2, custId);
					ps.executeUpdate();
					return true;
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	public double getCheckingsBalance(int custid) {
		Connection connection;
		try {
			connection = createConnection();
			if (connection != null) {
				String query = "Select * from Account where customerID = ?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, custid);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {

					return rs.getDouble("Checking_Balance");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return -1;

	}

	public double getSavingsBalance(int custid) {
		Connection connection;
		try {
			connection = createConnection();
			if (connection != null) {
				String query = "Select * from Account where customerID = ?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, custid);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {

					return rs.getDouble("Saving_Balance");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return -1;

	}

	public boolean withdrawSavings(int custid, double amount) {
		double previous = getSavingsBalance(custid);
		if (previous < amount)
			return false;
		else {
			Connection connection;
			try {
				connection = createConnection();
				if (connection != null) {
					String query = "update account set Saving_Balance = ? where customerid = ?";
					PreparedStatement ps = connection.prepareStatement(query);

					ps.setDouble(1, previous - amount);
					ps.setInt(2, custid);
					ps.executeUpdate();
					return true;
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	public boolean depositSavings(int custId, double amount) {

		Connection connection;
		try {
			connection = createConnection();
			if (connection != null) {
				String query = "update account set Saving_Balance = ? where customerid = ?";
				PreparedStatement ps = connection.prepareStatement(query);

				ps.setDouble(1, getSavingsBalance(custId) + amount);
				ps.setInt(2, custId);
				ps.executeUpdate();
				return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int loanApplication(int custId, double loanAmount, int months, int remMonths, double monthlyInterest, double annualInterest,
			double interestAmt, double totalAmount, double remainingAmt, int creditScore) {
		Connection connection;
		try {
			connection = createConnection();
			if (connection != null) {
				String insertLoanInfoQuery = "INSERT into loan(customerID, loan_amount, months, Remaining_Months, monthly_interest, annual_interest, interest_amount_incurred, total_amount_plus_interest, Remaining_Amount_ToBe_Paid, credit_score) values(?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = connection.prepareStatement(insertLoanInfoQuery);

				ps.setInt(1, custId);
				ps.setDouble(2, loanAmount);
				ps.setInt(3, months);
				ps.setInt(4, remMonths);
				ps.setDouble(5, monthlyInterest);
				ps.setDouble(6, annualInterest);
				ps.setDouble(7, interestAmt);
				ps.setDouble(8, totalAmount);
				ps.setDouble(9, remainingAmt);
				ps.setInt(10, creditScore);
				ps.executeUpdate();

				return custId;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public double getLoanBalance(int custid) {
		Connection connection;
		try {
			connection = createConnection();
			if (connection != null) {
				String query = "Select * from loan where customerID = ?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, custid);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {

					return rs.getDouble("Remaining_Amount_ToBe_Paid");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int getRemainingMonths(int custid) {
		Connection connection;
		try {
			connection = createConnection();
			if (connection != null) {
				String query = "SELECT * FROM loan where customerID = ?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, custid);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {

					return rs.getInt("Remaining_Months");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean setRemainingMonths(int custid, int months) {
		//months = 1;
		int remaining = getRemainingMonths(custid);
		if (remaining < months) {
			return false;
		} else {
			Connection connection;
			try {
				connection = createConnection();
				if (connection != null) {
					String query = "UPDATE loan SET Remaining_Months=? where customerID = ?";
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setInt(1, remaining - months);
					ps.setInt(2, custid);

					ps.executeUpdate();
					return true;
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	public boolean paidInstallment(int custId, double amount) {
		double previous = getLoanBalance(custId);
		if (previous < amount) {
			return false;
		} else {
			Connection connection;
			try {
				connection = createConnection();
				if (connection != null) {
					String query = "UPDATE loan set Remaining_Amount_ToBe_Paid=? WHERE customerID=?";
					PreparedStatement ps = connection.prepareStatement(query);

					ps.setDouble(1, previous - amount);
					ps.setInt(2, custId);
					ps.executeUpdate();
					
					if(previous-amount==0){
						query = "Delete from loan where customerID=?";
						ps = connection.prepareStatement(query);
						ps.setInt(1, custId);
						ps.executeUpdate();
					}
					return true;
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return false;
		}
	}
	
	public boolean hasPreviousLoan(int custid){
		Connection connection;
		try {
			connection = createConnection();
			if (connection != null) {
				String query = "SELECT * FROM loan where customerID = ?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, custid);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {

					return true;
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
