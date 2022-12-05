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
            System.out.println("Menu Admin\n1. Thêm sản phẩm\n2. Xem danh sách sản phẩm\n3. Xoá sản phẩm" +
                    "\n4. Sửa sản phẩm\n5. Xem danh sách khách hàng\n6. Đăng xuất");
            System.out.println("Nhập lựa chọn của admin:");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    productManager.addProduct();
                    break;
                case 2:
                    showTypeProduct();
                    break;
                case 3:
                    productManager.deleteProduct();
                    break;
                case 4:
                    break;
                case 5:
                    customerManager.showCustomer();
                    break;
                case 6:
                    return;
            }
        }
    }

    //    tạo hàm hiển thị menu sắp xếp sản phẩm theo loại:
    public void showTypeProduct() {
        int choice;
        while (true) {
            System.out.println("Type Product:\n1. All Product\n2. Shower Gel\n3. Body Mist\n4. Back");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    productManager.showProduct();
                    break;
                case 2:
                    System.out.println("--------------------------< Shower Gel >---------------------------");
                    productManager.showShowerGel();
                    break;
                case 3:
                    System.out.println("--------------------------< Body Mist >----------------------------");
                    productManager.showBodyMist();
                    break;
                default:
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
