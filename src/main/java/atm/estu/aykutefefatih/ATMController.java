package atm.estu.aykutefefatih;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        String logEntry = String.format("[%s] %s - Amount: %s - Card: %s", date, transactionType, amount, cardID);
        BankCentralSystem.getLogStack().add(logEntry);
    }

}
