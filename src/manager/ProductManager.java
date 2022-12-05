package manager;

import io.ReadWriteFile;
import model.BodyMist;
import model.comparatorbyprice.ComparatorPriceAscending;
import model.Product;
import model.ShowerGel;
import model.comparatorbyprice.ComparatorPriceDescending;

import java.util.*;

public class ProductManager {
    List<Product> products = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    //    tạo hàm show danh sách sản phẩm:
    public void showProduct() {
        readProduct();
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-10s%-20s%-15s%-15s%-30s", "ID", "Name", "Volume (ml) ", "Amount", "Price");
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

    //    tạo hàm tìm kiếm sản phẩm theo tên cho khách hàng:
    public void searchProductByName() {
        String nameSearch = InputString.inputString("^([a-zA-Z\\s])+");
//        tạo 1 mảng String để hứng nameSearch sau khi split
        String[] nameSearchAfterSplit = nameSearch.split(" ");
//        tạo 1 chuỗi để hứng nameSearchAfterSplit sau khi nối lại không có dấu cách:
        String str = "";
        for (int i = 0; i < nameSearchAfterSplit.length; i++) {
            if(nameSearchAfterSplit[i]!=null) str+= nameSearchAfterSplit[i];
        }


//        tạo mảng động chứa các mảng tên sau khi đã được split
        List<String[]> nameAfterSplit = new ArrayList<>();
//        split được tên product thứ i nào thì add vào mảng nameAfterSplit
        for (int i = 0; i < products.size(); i++) {
            nameAfterSplit.add(products.get(i).getName().split(" "));
        }
//        tạo 1 mảng String để chứa các tên sau khi nối lại không có dấu cách:
        String[] listName = new String[products.size()];
        for (int i = 0; i < listName.length; i++) {
            listName[i] = "";
        }
        for (int i = 0; i < products.size(); i++) {
            // tạo 1 mảng String để hứng các phần tử trong mảng động nameAfterSplit
            String[] string = nameAfterSplit.get(i);
            for (int j = 0; j < string.length; j++) {
                if (string[j]!=null) listName[i] += string[j];
            }
        }

        for (int i = 0; i < listName.length; i++) {
            if(str.equalsIgnoreCase(listName[i])) System.out.println(products.get(i).toString());
        }

    }

    //    tạo hàm tìm kiếm sản phẩm theo id cho admin:
    public int searchProductById() {
        String id = scanner.nextLine();
        for (int i = 0; i < products.size(); i++) {
            if (id.equals(products.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }

    //    tạo hàm xoá sản phẩm trong menu Admin:
    public void deleteProduct() {
        readProduct();
        System.out.println("Nhập tên sản phẩm bạn muốn xoá:");
        searchProductByName();
        System.out.println("Nhập id sản phẩm bạn muốn xoá: ");
        int index = searchProductById();
        if (index >= 0) {
            products.remove(index);
            System.out.println("Đã xoá sản phẩm!");
        } else {
            System.out.println("Không tìm thấy ID sản phẩm!");
        }
        writeProduct();
    }

    //    tạo hàm sắp xếp sản phẩm theo giá từ thấp đến cao:
    public void ascendingPrice() {
        readProduct();
        products.sort(new ComparatorPriceAscending());
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    //    tạo hàm sắp xếp sản phẩm theo giá từ cao xuống thấp:
    public void descendingPrice() {
        readProduct();
        products.sort(new ComparatorPriceDescending());
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    //    hiển thị sản phẩm theo loại sữa tắm:
    public void showShowerGel() {
        readProduct();
        for (Product product : products) {
            if (product instanceof ShowerGel)
                System.out.println(product);
        }
    }

    //    hiển thị sản phẩm theo loại bodymist:
    public void showBodyMist() {
        readProduct();
        for (Product product : products) {
            if (product instanceof BodyMist) {
                System.out.println(product);
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
            return createProduct(true);
        } else if (choice == 2) {
            return createProduct(false);
        } else {
            System.out.println("Chỉ nhập 1 hoặc 2");
            return null;
        }
    }

    //    hàm tạo ID sản phẩm:
    public String createID(boolean isShowerGel) {
        int numId1;
        int numId2;
        if (products.size() > 0) {
            if (isShowerGel) {
                for (int i = products.size() - 1; i >= 0; i--) {
                    if (products.get(i) instanceof ShowerGel) {
                        numId1 = Integer.parseInt(String.valueOf(products.get(i).getId().charAt(0)));
                        numId1 = numId1 + 1;
                        return numId1 + "SG";
                    }
                }
                numId1 = 0;
                numId1 = numId1 + 1;
                return numId1 + "SG";
            } else {
                for (int i = products.size() - 1; i >= 0; i--) {
                    if (products.get(i) instanceof BodyMist) {
                        numId2 = Integer.parseInt(String.valueOf(products.get(i).getId().charAt(0)));
                        numId2 = numId2 + 1;
                        return numId2 + "BM";
                    }
                }
                numId2 = 0;
                numId2 = numId2 + 1;
                return numId2 + "BM";
            }
        } else if (isShowerGel) {
            numId1 = 0;
            numId1 = numId1 + 1;
            return numId1 + "SG";
        } else {
            numId2 = 0;
            numId2 = numId2 + 1;
            return numId2 + "BM";
        }
    }

    // hàm tạo sản phẩm:
    public Product createProduct(boolean isShowerGel) {
        System.out.printf("%-20s", "Nhập tên sản phẩm: ");
        String name = InputString.inputString("^([a-zA-Z\\s])+");
        System.out.printf("%-20s", "Nhập dung tích sản phẩm: ");
        int volume = Integer.parseInt(scanner.nextLine());
        System.out.printf("%-20s", "Nhập số lượng sản phẩm: ");
        int amount = Integer.parseInt(scanner.nextLine());
        System.out.printf("%-20s", "Nhập giá sản phẩm: ");
        double price = Double.parseDouble(scanner.nextLine());
        if (isShowerGel) {
            return new ShowerGel(createID(true), name, volume, amount, price);
        } else return new BodyMist(createID(false), name, volume, amount, price);
    }
}
