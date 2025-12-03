package atm.estu.aykutefefatih;

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
        if (amount > 0) { //aslında gereksiz zaten atm'de eksi tuşu olmayacak ama
            BankCentralSystem.updateBalance(amount+currentAccount.getBalance()); 
        }
    }
    
    public static boolean withdraw(double amount) {
        if (currentAccount.getBalance() >= amount) {
            BankCentralSystem.updateBalance(currentAccount.getBalance() - amount);  
            return true;
        }
        return false;
    }

    public static double checkBalance(){
        return currentAccount.getBalance();
    }

    public static void logTransaction(String transactionType, String amount, String date){

    }
}
