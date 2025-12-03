package atm.estu.aykutefefatih;

import java.util.Scanner;

public class ATMUI {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int count = 1;
        BankCentralSystem.initializeTestData();

        for (; count <= 3; count++) {
            System.out.println("Please enter Card Number: ");
            String tempCardNumber = sc.nextLine();
            BankCentralSystem.setCurrentAccount(tempCardNumber);
            System.out.println("Please enter PIN: ");
            String tempPIN = sc.nextLine();
            simulateDelay(2,"Authenticating");
            if (ATMController.authCustomer(tempPIN)) {
                showMainMenu();
                break;
            }
            System.out.printf("Card Number or PIN is invalid, %d attempts are left.\n", 3-count);
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
        while(true){  
            System.out.println(
            """
                Please select the transaction:
                1-Deposit
                2-Withdraw
                3-Check Balance
                4-Log out
            """);
            selection = Integer.parseInt(sc.nextLine());
            simulateDelay(1, "Please wait");
            switch (selection) {
                case 1:    
                    depositMenu();
                    break;
                case 2:
                    withdrawMenu();
                    break;
                case 3:
                    
                    break;
                case 4:
                    
                    break;
                default:
                    System.out.println("");
                    break;
            }
        }
    }
    private static void depositMenu(){
        clearScreen();
        int localAmount;
        System.out.println("Please enter the amount you want to deposit: ");
        localAmount = Integer.valueOf(sc.nextLine());
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
            System.out.printf("Please enter the amount you want to withdraw (Current Balance: %f): \n",ATMController.checkBalance());
            localAmount = Integer.valueOf(sc.nextLine());

            simulateDelay(0.5, "Processing");

            if(ATMController.withdraw(localAmount) == true){
                System.out.println("Withdraw Successful!");
                break;
            }else{
                System.out.printf("Please enter an amount in range of your balance  (Current Balance: %f): \n",ATMController.checkBalance());
            }
            sleep(2);
            clearScreen();
        }
        
    }
    
}
