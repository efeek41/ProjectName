package atm.estu.aykutefefatih;

public class CustomerAccount {
    private final String cardNumber;
    private String pin;
    private double balance;
    
    public CustomerAccount(String cardNumber, String pin, double balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
}