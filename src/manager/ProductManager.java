package manager;

import io.ReadWriteFile;
import model.BodyMist;
import model.Product;
import model.ShowerGel;

import java.util.*;

public class ProductManager {
    List<Product> products = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    //    tạo hàm show danh sách sản phẩm:
    public void showProduct() {
        readProduct();
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-10s%-20s%-15s%-15s%-30s", "ID", "Name", "Volume", "Amount", "Price");
        System.out.println();
        System.out.println("----------------------------------------------------------------------");
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    //    tạo hàm thêm product:
    public void addProduct() {
        readProduct();
        products.add(menuCreateProduct());
        writeProduct();
    }

    //    hiển thị sản phẩm theo loại sữa tắm:
    public void showShowerGel() {
        readProduct();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i) instanceof ShowerGel) {
                System.out.println(products.get(i).toString());
            }
        }
    }

    //    hiển thị sản phẩm theo loại bodymist:
    public void showBodyMist() {
        readProduct();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i) instanceof BodyMist) {
                System.out.println(products.get(i).toString());
            }
        }
    }


    //  hàm viết dữ liệu thêm sản phẩm vào file:
    public void writeProduct() {
        ReadWriteFile.writeToFile("product.txt", products.toArray(Product[]::new));
    }

    //  hàm đọc dữ liệu:
    public void readProduct() {
        try {
            Product[] products = (Product[]) ReadWriteFile.readFromFile("product.txt");
            this.products = products != null ? new ArrayList<>(Arrays.asList(products)) : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            this.products = new ArrayList<>();
        }
    }

    //  menu tạo sản phẩm là sữa tắm hoặc bodymist:
    public Product menuCreateProduct() {
        System.out.println("1. ShowerGel\n2. BodyMist");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) {
            Product sg = createProduct(true);
            return sg;
        } else if (choice == 2) {
            Product bm = createProduct(false);
            return bm;
        } else {
            System.out.println("Chỉ nhập 1 hoặc 2");
            return null;
        }
    }


    // hàm tạo sản phẩm:
    public Product createProduct(boolean isShowerGel) {
        System.out.printf("%-20s", "Nhập tên sản phẩm: ");
        String name = scanner.nextLine();
        System.out.printf("%-20s", "Nhập dung tích sản phẩm: ");
        int volume = Integer.parseInt(scanner.nextLine());
        System.out.printf("%-20s", "Nhập số lượng sản phẩm: ");
        int amount = Integer.parseInt(scanner.nextLine());
        System.out.printf("%-20s", "Nhập giá sản phẩm: ");
        double price = Double.parseDouble(scanner.nextLine());
        int numId1;
        int numId2;
        if (products.size() != 0) {
            if (isShowerGel) {
                for (int i = products.size() - 1; i >= 0; i--) {
                    if (products.get(i) instanceof ShowerGel) {
                        numId1 = Integer.parseInt(String.valueOf(products.get(i).getId().charAt(0)));
                        numId1 = numId1 + 1;
                        String idSG = numId1 + "SG";
                        return new ShowerGel(idSG, name, volume, amount, price);
                    }
                }
                numId1 = 0;
                numId1 = numId1 + 1;
                String idSG = numId1 + "SG";
                return new ShowerGel(idSG, name, volume, amount, price);
            } else {
                for (int i = products.size() - 1; i >= 0; i--) {
                    if (products.get(i) instanceof BodyMist) {
                        numId2 = Integer.parseInt(String.valueOf(products.get(i).getId().charAt(0)));
                        numId2 = numId2 + 1;
                        String idBM = numId2 + "BM";
                        return new BodyMist(idBM, name, volume, amount, price);
                    }
                }
                numId2 = 0;
                numId2 = numId2 + 1;
                String idBM = numId2 + "BM";
                return new BodyMist(idBM, name, volume, amount, price);
            }
        } else if (isShowerGel) {
            numId1 = 0;
            numId1 = numId1+1;
            String idSG = numId1 + "SG";
            return new ShowerGel(idSG, name, volume, amount, price);
        } else {
            numId2 = 0;
            numId2 = numId2 +1;
            String idBM = numId2 + "BM";
            return new BodyMist(idBM, name, volume, amount, price);
        }

    }
}
