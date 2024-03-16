import java.util.Scanner;

public class Main {
    private static ATM[] accounts = new ATM[5];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Main loop for the ATM program
        while (true) {
            System.out.println("Enter your first and last name or 'exit' to end:");
            String fullName = scanner.nextLine();
            if (fullName.equalsIgnoreCase("exit")) {
                break;
            }
            int accountNumber = findAccountNumber(fullName);
            if (accountNumber == -1) {
                if (hasUnusedAccounts()) {
                    accountNumber = createNewAccount(fullName);
                } else {
                    continue;
                }
            }
            ATM userAccount = accounts[accountNumber];
            System.out.println("Your account number is: " + accountNumber);

            // Menu for account operations
            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Get Statistics");
                System.out.println("4. View Recent Transactions");
                System.out.println("5. Leave");
                System.out.print("Select an option: ");
                int option;
                try {
                    option = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }
                switch (option) {
                    case 1:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount;
                        try {
                            depositAmount = Double.parseDouble(scanner.nextLine());
                            userAccount.deposit(depositAmount);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input for deposit amount.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount;
                        try {
                            withdrawalAmount = Double.parseDouble(scanner.nextLine());
                            userAccount.withdraw(withdrawalAmount);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input for withdrawal amount.");
                        }
                        break;
                    case 3:
                        displayStatistics(userAccount);
                        break;
                    case 4:
                        displayRecentTransactions(userAccount);
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
                if (option == 5) {
                    break;
                }
            }
        }
        System.out.println("Exiting ATM program.");
    }

    // Find the account number based on full name
    private static int findAccountNumber(String fullName) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].isOwner(fullName)) {
                return i;
            }
        }
        return -1;
    }

    // Check if there are unused accounts available
    private static boolean hasUnusedAccounts() {
        for (ATM acc : accounts) {
            if (acc == null) {
                return true;
            }
        }
        return false;
    }

    // Create a new account with the provided full name
    private static int createNewAccount(String fullName) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] == null) {
                accounts[i] = new ATM(5, 0.0, fullName);
                return i;
            }
        }
        // Should not reach here if checked with hasUnusedAccounts before
        return -1;
    }

    // Display statistics for the account
    private static void displayStatistics(ATM account) {
        double[] stats = account.getStats();
        double min = Double.MAX_VALUE, max = Double.MIN_VALUE, sum = 0;
        for (double transaction : stats) {
            if (transaction != 0) {
                sum += transaction;
                if (transaction < min) {
                    min = transaction;
                }
                if (transaction > max) {
                    max = transaction;
                }
            }
        }
        double avg = sum / stats.length;
        System.out.println("Current Balance: " + account.toString());
        System.out.println("Min Transaction Size: " + min);
        System.out.println("Max Transaction Size: " + max);
        System.out.println("Average Transaction Size: " + avg);
    }

    // Display recent transactions for the account
    private static void displayRecentTransactions(ATM account) {
        System.out.println("Recent Transactions:");
        double[] transactions = account.getStats();
        for (double transaction : transactions) {
            if (transaction != 0) {
                System.out.println(transaction > 0 ? "Deposit: +" + transaction : "Withdrawal: " + transaction);
            }
        }
    }
}