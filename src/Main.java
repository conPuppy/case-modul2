import manager.UserManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserManager adminManager = new UserManager();
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("Menu:\n1.Register\n2.Login\n3.Show");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    adminManager.register();
                    break;
                case 2:
                    adminManager.login();
                    break;
                case 3:
                    adminManager.showAdmin();
                    break;
            }
        }
    }
}