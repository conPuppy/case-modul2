package manager;

import java.util.Scanner;

public class Manager {
    Scanner scanner = new Scanner(System.in);
    AdminManager adminManager = new AdminManager();
    CustomerManager customerManager = new CustomerManager();
    public void showMenu() {
        int choice;
        while (true) {
            System.out.println("Menu\n1. Login Admin\n2. Register Customer\n3. Login Customer\n4. Thoát");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    adminManager.showMenuAdmin();
                    break;
                case 2:
                    customerManager.register();
                    break;
                case 3:
                    customerManager.login();
                    break;
                case 4:
                    System.exit(0);
                    break;
            }
        }
    }
}
