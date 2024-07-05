public class ATM {
    private BankAccount userAccount;

    public ATM(BankAccount account) {
        this.userAccount = account;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= userAccount.getBalance()) {
            userAccount.withdraw(amount);
            System.out.println("Withdrawal of $" + amount + " successful.");
        } else {
            System.out.println("Withdrawal failed. Insufficient funds.");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            userAccount.deposit(amount);
            System.out.println("Deposit of $" + amount + " successful.");
        } else {
            System.out.println("Deposit failed. Invalid amount.");
        }
    }

    public void checkBalance() {
        System.out.println("Current balance: $" + userAccount.getBalance());
    }
}
