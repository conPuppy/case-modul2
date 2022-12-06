package manager;

import model.Admin;

import java.util.Scanner;

public class AdminManager {
    Scanner scanner = new Scanner(System.in);
    Admin admin = new Admin();
    ProductManager productManager = new ProductManager();
    CustomerManager customerManager = new CustomerManager();
//    tạo hàm hiển thị menu khi chọn đăng nhập vào tài khoản admin:

    public void showMenuAdmin() {
        int choice;
        while (true) {
            System.out.println("Menu Admin:\n1.Login\n2.Back");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    return;
            }
        }
    }

    //    tạo hàm hiển thị menu sau khi đăng nhập thành công vào tài khoản admin:
    public void showMenuAdminAfterLogin() {
        customerManager = new CustomerManager();
        int choice;
        while (true) {
            System.out.println("""
                    Menu Admin
                    1. Thêm sản phẩm
                    2. Xem danh sách sản phẩm
                    3. Xoá sản phẩm
                    4. Sửa sản phẩm
                    5. Xem danh sách khách hàng
                    6. Đăng xuất""");
            System.out.println("Nhập lựa chọn của admin:");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    productManager.addProduct();
                    break;
                case 2:
                    productManager.showTypeProduct();
                    break;
                case 3:
                    productManager.deleteProduct();
                    break;
                case 4:
                    productManager.editProduct();
                    break;
                case 5:
                    customerManager.showCustomer();
                    break;
                case 6:
                    return;
            }
        }
    }

    //    tạo hàm đăng nhập:
    public void login() {
        System.out.println("Nhập tên đăng nhập tài khoản admin: ");
        String name = scanner.nextLine();
        System.out.println("Nhập password: ");
        String password = scanner.nextLine();
        if (name.equals(admin.getName()) && password.equals(admin.getPassword())) {
            System.out.println("Đăng nhập thành công tài khoản admin!");
            showMenuAdminAfterLogin();
        } else {
            System.out.println("Đăng nhập thất bại");
        }
    }
}
