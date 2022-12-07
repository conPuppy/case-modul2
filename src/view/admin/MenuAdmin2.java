package view.admin;

import manager.CustomerManager;
import manager.ProductManager;

import java.util.Scanner;

public class MenuAdmin2 {
    Scanner scanner = new Scanner(System.in);
    CustomerManager customerManager = new CustomerManager();

    public void showMenuAdmin2() {
        int choice;
        while (true) {
            try {
                System.out.println("Menu Quản lý Khách hàng\n1. Xem danh sách khách hàng\n2. Xoá tài khoản khách hàng\n3. Back");
                System.out.println("Nhập lựa chọn của admin:");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        customerManager.showCustomer();
                        break;
                    case 2:
                        customerManager.deleteCustomer();
                        break;
                    default:
                        return;
                }
            } catch (Exception e) {
                System.err.println("Nhập số đi mà!!!");
            }
        }
    }
}
