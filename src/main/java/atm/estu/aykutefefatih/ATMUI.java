package atm.estu.aykutefefatih;

import java.util.Scanner;

public class ATMUI {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        BankCentralSystem.initializeTestData();

        for (int i = 1; i <= 3; i++) {
            System.out.println("Please enter Card Number: ");
            String tempCardNumber = sc.nextLine();
            BankCentralSystem.setCurrentAccount(tempCardNumber);
            System.out.println("Please enter PIN: ");
            String tempPIN = sc.nextLine();
            if (ATMController.authCustomer(tempPIN)) {
                
                break;
            }
            clearScreen();

            System.out.println("Authenticating.");
            sleep(2);
            clearScreen();
            System.out.println("Authenticating..");
            sleep(2);
            System.out.println("Authenticating...");
            sleep(2);
            System.out.printf("Card Number or PIN is invalid, %d attempts are left.\n", 3-i );
            sleep(1);
        
            
        }
        
    }
    private static void clearScreen() {  
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }  

    private static void sleep(int time){
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    private void showMainMenu(){

    }
    
}
