package atm.estu.aykutefefatih;

import java.util.Scanner;

public class ATMUI {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        BankCentralSystem.initializeTestData();

        for (int count = 1; count <= 3; count++) {
            clearScreen();
            System.out.println("Please enter Card Number (For simulation, press X to show logs.): ");
            String tempInput = sc.nextLine();
            if(tempInput.equalsIgnoreCase("x")){
                clearScreen();
                ATMController.printAllLogs();
                System.out.println("Press ENTER to continue");
                sc.nextLine();
                count--;
                continue;
            }
            BankCentralSystem.setCurrentAccount(tempInput);
            System.out.println("Please enter PIN: ");
            String tempPIN = sc.nextLine();
            simulateDelay(2,"Authenticating");
            if (ATMController.authCustomer(tempPIN)) {
                count = 1;
                showMainMenu();
            }else{
                System.out.printf("Card Number or PIN is invalid, %d attempts are left.\n", 3-count);
            }
            
            sleep(1); 
        }
        
    }
    private static void clearScreen() {  
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }  

    private static void sleep(double time){
        try {
            Thread.sleep((int)(time * 1000));
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    private static void simulateDelay(double time, String word){
        clearScreen();
            System.out.println(word+".");
            sleep(time);
            clearScreen();
            System.out.println(word+"..");
            sleep(time);
            clearScreen();
            System.out.println(word+"...");
            sleep(time);
            clearScreen();
    }
    
    private static void showMainMenu(){
        int selection;
        while(ATMController.getCurrentAccount() != null){  
            System.out.println(
            """
                Please select the transaction:
                1-Deposit
                2-Withdraw
                3-Check Balance
                4-Log out
            """);
            selection = Integer.parseInt(sc.nextLine());
            simulateDelay(0.6, "Please wait");
            switch (selection) {
                case 1:    
                    depositMenu();
                    break;
                case 2:
                    withdrawMenu();
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    logOut();
                    break;
                default:
                    System.out.println("");
                    break;
            }
        }
    }
    private static void depositMenu(){
        int localAmount;
        System.out.println("Please enter the amount you want to deposit (Press X to cancel): ");
        String input = sc.nextLine();
        if(input.equalsIgnoreCase("x")){
            clearScreen();
            System.out.println("Deposit canceled.");
            return;
        }
        localAmount = Integer.valueOf(input);
        simulateDelay(0.5, "Processing");
        ATMController.deposit(localAmount);
        System.out.println("Deposit Successful!");
        sleep(2);
        clearScreen();
    }
    private static void withdrawMenu(){
        clearScreen();
        int localAmount;
        while (true) { 
            System.out.printf("Please enter the amount you want to withdraw (Press X to cancel)(Current Balance: %.2f TRY): \n",ATMController.checkBalance());
            String input = sc.nextLine();
            if(input.equalsIgnoreCase("x")){
                clearScreen();
                System.out.println("Withdraw canceled.");
                break;
            }

            localAmount = Integer.valueOf(input);

            simulateDelay(0.5, "Processing");

            if(ATMController.withdraw(localAmount) == true){
                System.out.println("Withdraw Successful!");
                break;
            }else{
                System.out.printf("Please enter an amount in range of your balance  (Current Balance: %.2f TRY): \n",ATMController.checkBalance());
            }
            sleep(2);
            clearScreen();
        }
    }
    private static void checkBalance(){
        System.out.printf("Current balance: %.2f TRY\n",ATMController.checkBalance());
    }
    private static void logOut(){
        ATMController.setCurrentAccount(null);
        simulateDelay(0.5, "Logging out");
    }
}
