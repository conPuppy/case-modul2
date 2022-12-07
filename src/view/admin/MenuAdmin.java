package view.admin;

import manager.CustomerManager;
import manager.ProductManager;
import view.admin.MenuAdmin1;
import view.admin.MenuAdmin2;

import java.util.Scanner;

public class MenuAdmin {
    Scanner scanner = new Scanner(System.in);
    MenuAdmin1 menuAdmin1 = new MenuAdmin1();
    MenuAdmin2 menuAdmin2 = new MenuAdmin2();
    //    tạo hàm hiển thị menu sau khi đăng nhập thành công vào tài khoản admin:
    public void showMenuAdminAfterLogin() {
        int choice;
        while (true) {
            try {
                System.out.println("""
                        Menu Admin
                        1. Quản lý sản phẩm
                        2. Quản lý khách hàng
                        3. Đăng xuất""");
                System.out.println("Nhập lựa chọn của admin:");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        menuAdmin1.showMenuAdmin1();
                        break;
                    case 2:
                        menuAdmin2.showMenuAdmin2();
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
