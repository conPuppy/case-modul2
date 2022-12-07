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
                System.out.println("Menu:\n1. Xem sản phẩm theo loại\n2. Xem sản phẩm theo giá\n3. Tìm kiếm sản phẩm theo tên" +
                        "\n4. Thêm sản phẩm vào giỏ hàng\n5. Xem giỏ hàng\n6. Đổi mật khẩu\n7. Đổi username\n8. Logout");
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
