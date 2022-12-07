package view.customer;

import manager.CustomerManager;
import manager.ProductManager;
import view.product.MenuShowProduct;

import java.util.Scanner;

public class MenuCustomer {
    ProductManager productManager = new ProductManager();
    Scanner scanner = new Scanner(System.in);
    MenuShowProduct menuShowProduct = new MenuShowProduct();
    CustomerManager customerManager = new CustomerManager(this);
    MenuShowProductByPrice menuShowProductByPrice = new MenuShowProductByPrice();
    //    tạo hàm show menu của Customer sau khi đăng nhập
    public void showMenuCustomer() {
        int choice;
        while (true) {
            try {
                System.out.println("""
                        Menu:
                        1. Xem sản phẩm theo loại
                        2. Xem sản phẩm theo giá
                        3. Tìm kiếm sản phẩm theo tên
                        4. Thêm sản phẩm vào giỏ hàng
                        5. Xem giỏ hàng
                        6. Đổi mật khẩu
                        7. Đổi username
                        8. Logout""");
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> menuShowProduct.showTypeProduct();
                    case 2 -> menuShowProductByPrice.showComparePrice();
                    case 3 -> {
                        System.out.println("Nhập tên sản phẩm muốn tìm: ");
                        productManager.searchProductByName();
                    }
                    case 4 -> customerManager.updateCart();
                    case 5 -> customerManager.showCart();
                    case 6 -> {
                        if(customerManager.changePassword()) return;
                    }
                    case 7 -> {
                        if(customerManager.changeUserName()) {
                            System.exit(0);
                        } else {
                            return;
                        }
                    }
                    default -> {
                        customerManager.writeUsers();
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Nhập số đê!");
            }
        }
    }

}
