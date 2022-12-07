package view.admin;


import manager.ProductManager;

import java.util.Scanner;

public class MenuAdmin1 {
    ProductManager productManager = new ProductManager();
    Scanner scanner = new Scanner(System.in);
    public void showMenuAdmin1() {
        int choice;
        while (true) {
            try {
                System.out.println("Menu Quản lý sản phẩm\n1. Thêm sản phẩm\n2. Xem danh sách sản phẩm\n3. Xoá sản phẩm\n4. Sửa sản phẩm\n5. Back");
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
                    default:
                        return;
                }
            } catch (Exception e) {
                System.err.println("Nhập số đi mà!!!");
            }
        }
    }
}
