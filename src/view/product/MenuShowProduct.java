package view.product;

import manager.ProductManager;

import java.util.Scanner;

public class MenuShowProduct {
    ProductManager productManager = new ProductManager();
    Scanner scanner = new Scanner(System.in);

    //    tạo hàm show danh sách sản phẩm:
    public void showTypeProduct() {
        int choice;
        while (true) {
            try {
                System.out.println("Type Product:\n1. All Product\n2. Shower Gel\n3. Body Mist\n4. Back");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        productManager.showProduct();
                        break;
                    case 2:
                        productManager.showShowerGel();
                        break;
                    case 3:
                        productManager.showBodyMist();
                        break;
                    default:
                        return;
                }
            } catch (Exception e) {
                System.err.println("Nhập số đê!");
            }
        }
    }
}
