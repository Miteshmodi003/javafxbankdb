public class NoneSelected extends BankAccount {
	private double overDrawFee;
	private double overDrawLimit;
	
	public NoneSelected() {
		
	}
	
	public NoneSelected(String accountNumber, double balance, double overDraftFee, double overDrawLimit,
			double accountMaintenanceFee) {
		super(accountNumber, balance, accountMaintenanceFee);
		this.overDrawFee = overDraftFee;
		this.overDrawLimit = overDrawLimit;
	}

	public double getOverDrawLimit() {
		return overDrawLimit;
	}

	public void withdraw(double amount) throws MyIllegalArgumentsException {
		if (amount <= getBalance()) {
			super.withdraw(amount);
		} else {
			if (amount > getBalance() + overDrawLimit - overDrawFee)
				throw new MyIllegalArgumentsException("Amount exceeds" + "overDrawlimit");
			else
				super.withdraw(amount + overDrawFee);
		}
	}

	public double getOverDrawFee() {
		return overDrawFee;
	}

	@Override
	public void deductAccountMaintenanceFee() {
		if (getBalance() < 750.0)
			try {
				withdraw(accountMaintenanceFee);
			} catch (MyIllegalArgumentsException e) {
				e.getMessage();
			}
	}

}
