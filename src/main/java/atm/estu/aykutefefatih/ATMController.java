package atm.estu.aykutefefatih;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class ATMController {
    private static CustomerAccount currentAccount = null;

    public static CustomerAccount getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(CustomerAccount currentAccount) {
        ATMController.currentAccount = currentAccount;
    }

    public static boolean authCustomer(String inputPin) {
        try{
            return currentAccount.getPin().equals(inputPin);
        } catch (Exception e) {
            return false;
        }
         
    } 
    
    public static void deposit(double amount) {
        BankCentralSystem.updateBalance(amount+currentAccount.getBalance()); 
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logTransaction("DEPOSIT", String.valueOf(amount), currentDate);
    }
    
    public static boolean withdraw(double amount) {
        if (currentAccount.getBalance() >= amount) {
            BankCentralSystem.updateBalance(currentAccount.getBalance() - amount);  
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            logTransaction("WITHDRAW", String.valueOf(amount), currentDate);
            return true;
        }
        return false;
    }

    public static double checkBalance(){
        return currentAccount.getBalance();
    }

    public static void logTransaction(String transactionType, String amount, String date){
         String cardID = currentAccount.getCardNumber();
         LinkedList<String> transactionList = BankCentralSystem.getLogList();

        String logEntry = String.format("[%s] %s - Amount: %s - Card: %s", date, transactionType, amount, cardID);
        transactionList.add(logEntry);
    }
    public static void printAllLogs() {
        LinkedList<String> transactionList = BankCentralSystem.getLogList();
        if (transactionList.isEmpty()) {
            System.out.println("There is no log to show.");
            return;
        }

        System.out.println("\n--- TRANSACTION HISTORY (LOGS) ---");
        for (String log : transactionList) {
            System.out.println(log);
        }
        System.out.println("------------------------------\n");
    }
}
