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
                case 1 -> adminManager.showMenuAdmin();
                case 2 -> customerManager.register();
                case 3 -> customerManager.login();
                case 4 -> System.exit(0);
            }
        }
    }
}
