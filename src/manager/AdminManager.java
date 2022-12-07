package manager;

import model.Admin;
import view.admin.MenuAdmin;

import java.util.Scanner;

public class AdminManager {
    Scanner scanner = new Scanner(System.in);
    Admin admin = new Admin();
    MenuAdmin menuAdmin = new MenuAdmin();

    //    tạo hàm đăng nhập:
    public void login() {
        System.out.println("Nhập tên đăng nhập tài khoản admin: ");
        String name = scanner.nextLine();
        System.out.println("Nhập password: ");
        String password = scanner.nextLine();
        if (name.equals(admin.getName()) && password.equals(admin.getPassword())) {
            System.out.println("Đăng nhập thành công tài khoản admin!");
            menuAdmin.showMenuAdminAfterLogin();
        } else {
            System.out.println("Đăng nhập thất bại");
        }
    }
}
