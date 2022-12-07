package view;

import manager.AdminManager;
import manager.CustomerManager;

import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    AdminManager adminManager = new AdminManager();
    CustomerManager customerManager = new CustomerManager();
    public void showMenu() {
        int choice;
        while (true) {
            try{
                System.out.println("Menu\n1. Login Admin\n2. Register Customer\n3. Login Customer\n4. Thoát");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> adminManager.login();
                    case 2 -> customerManager.register();
                    case 3 -> customerManager.login();
                    case 4 -> System.exit(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Nhập số đi!");
            }
        }
    }
}
