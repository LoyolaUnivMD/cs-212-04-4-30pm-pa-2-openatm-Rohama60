public class ATM {
    private String accountOwner;
    private double balance;
    private double[] transactionHistory;
    private int numOfRecentTransactions;

    // Constructor to initialize the account
    public ATM(int numOfRecentTransactions, double initialDeposit, String accountOwner) {
        this.numOfRecentTransactions = numOfRecentTransactions;
        this.transactionHistory = new double[numOfRecentTransactions];
        this.accountOwner = accountOwner;
        this.balance = initialDeposit;
    }

    // Method to check if the person is the owner of the account
    public boolean isOwner(String person) {
        return this.accountOwner.equals(person);
    }

    // Method to deposit funds into the account
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            updateTransactionHistory(amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Method to withdraw funds from the account
    public void withdraw(double amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            updateTransactionHistory(-amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    // Method to get the transaction history
    public double[] getStats() {
        return this.transactionHistory;
    }

    @Override
    public String toString() {
        return "Account Owner: " + this.accountOwner + ", Balance: " + this.balance;
    }

    // Helper method to update the transaction history
    private void updateTransactionHistory(double amount) {
        for (int i = this.numOfRecentTransactions - 1; i > 0; i--) {
            this.transactionHistory[i] = this.transactionHistory[i - 1];
        }
        this.transactionHistory[0] = amount;
    }
}