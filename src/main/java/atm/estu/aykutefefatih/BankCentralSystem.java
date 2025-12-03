package atm.estu.aykutefefatih;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BankCentralSystem {
    private static Map<String, CustomerAccount> customerAccounts; 
    private static Stack<String> transactionStack = new Stack<>();
    
    private BankCentralSystem() {}
    
    protected static void initializeTestData() {
        // Simüle etmek için yaptık, örnek olarak hesaplar oluşturduk
        customerAccounts = new HashMap<>();
        customerAccounts.put("12345", new CustomerAccount("12345", "1234", 5000.0));
        customerAccounts.put("13579", new CustomerAccount("13579", "5678", 3000.0));
        customerAccounts.put("02468", new CustomerAccount("02468", "0000", 10000.0));
    }

    protected boolean validatePIN(String inputPIN){
        if(inputPIN.equals(customerAccounts.get(ATMController.getCurrentAccount().getCardNumber()).getPin())){
            return true;
        }
        return false;
    }

    protected static boolean setCurrentAccount(String cardNumber){
        CustomerAccount localCurrentAccount = customerAccounts.get(cardNumber);
        if(localCurrentAccount != null){
            ATMController.setCurrentAccount(localCurrentAccount);
            return true;
        }
        return false;
    }

    public static void updateBalance(double newBalance) {
        CustomerAccount current = ATMController.getCurrentAccount();
        if (current != null) {
            current.setBalance(newBalance);
        }
    }
    public static Stack<String> getLogStack(){
        return transactionStack;
    }
}
