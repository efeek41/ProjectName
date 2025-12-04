package atm.estu.aykutefefatih;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class ATMController {
    //burası direkt aggregation relationship
    private static ATMController controllerInstance;
    private static BankCentralSystem centralSystemInstance = BankCentralSystem.getCentralSystem();
    private CustomerAccount currentAccount = null;

    //singleton için constructor gizledik
    private ATMController(){}

    //singleton pattern
    protected static ATMController getCentralSystem(){
        if(controllerInstance == null){
            controllerInstance = new ATMController();
        }
        return controllerInstance; 
    }

    //anlık giriş yapılı hesabı döndürüyor
    public CustomerAccount getCurrentAccount() {
        return currentAccount;
    }

    //anlık giriş yapılı hesabı ayarlıyor
    protected void setCurrentAccount(String cardNumber) {
        controllerInstance.currentAccount = centralSystemInstance.findCurrentAccount(cardNumber);
    }

    //çıkış yapıyor
    protected void logOut(){
        controllerInstance.setCurrentAccount(null);
    }

    //auth başlangıcı
    protected boolean authCustomer(String inputPin) {
        try{
            return currentAccount.getPin().equals(inputPin);
        } catch (Exception e) {
            return false;
        }
         
    } 
    
    //parasal işlemlerin hepsi burada
    protected void deposit(double amount) {
        centralSystemInstance.updateBalance(amount+currentAccount.getBalance()); 
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logTransaction("DEPOSIT", String.valueOf(amount), currentDate);
    }
    
    protected boolean withdraw(double amount) {
        if (currentAccount.getBalance() >= amount) {
            centralSystemInstance.updateBalance(currentAccount.getBalance() - amount);  
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            logTransaction("WITHDRAW", String.valueOf(amount), currentDate);
            return true;
        }
        return false;
    }

    public double checkBalance(){
        return currentAccount.getBalance();
    }
    //
    //Simüle etmek için ekledik gerçek kodda olmaması gerekiyor
    public void logTransaction(String transactionType, String amount, String date){
         String cardID = currentAccount.getCardNumber();
         LinkedList<String> transactionList = centralSystemInstance.getLogList();

        String logEntry = String.format("[%s] %s - Amount: %s - Card: %s", date, transactionType, amount, cardID);
        transactionList.add(logEntry);
    }
    
    public void printAllLogs() {
        LinkedList<String> transactionList = centralSystemInstance.getLogList();
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
