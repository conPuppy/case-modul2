package view.customer;

import manager.ProductManager;

import java.util.Scanner;

public class MenuShowProductByPrice {
    Scanner scanner = new Scanner(System.in);
    ProductManager productManager = new ProductManager();
    //    tạo hàm hiển thị lọc giá từ thấp đến cao và ngược lại:
    public void showComparePrice() {
        int choice;
        while (true) {
            try {
                System.out.println("""
                        Lọc sản phẩm theo giá:
                        1. Các sản phẩm có giá từ thấp đến cao
                        2. Các sản phẩm có giá từ cao xuống thấp
                        3. Giá thấp hơn 100k
                        4. Giá từ 100k đến 300k
                        5. Giá lớn hơn 300k
                        6. Back
                        """);
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        productManager.ascendingPrice();
                        break;
                    case 2:
                        productManager.descendingPrice();
                        break;
                    case 3:
                        productManager.lowerPrice();
                        break;
                    case 4:
                        productManager.betweenPrice();
                        break;
                    case 5:
                        productManager.higherPrice();
                        break;
                    default:
                        return;
                }
            } catch (Exception e) {
                System.err.println("Nhập số đi nha!");
            }
        }
    }
}
