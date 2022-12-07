package manager;

import io.ReadWriteFile;
import model.BodyMist;
import model.comparatorbyprice.ComparatorPriceAscending;
import model.Product;
import model.ShowerGel;
import model.comparatorbyprice.ComparatorPriceDescending;

import java.util.*;

public class ProductManager {
    static List<Product> products = new ArrayList<>();
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
                        showProduct();
                        break;
                    case 2:
                        System.out.println("--------------------------< Shower Gel >---------------------------");
                        showShowerGel();
                        break;
                    case 3:
                        System.out.println("--------------------------< Body Mist >----------------------------");
                        showBodyMist();
                        break;
                    default:
                        return;
                }
            } catch (Exception e) {
                System.err.println("Nhập số đê!");
            }
        }
    }

    public void showProduct() {
        readProduct();
        System.out.printf("%72s%s","","All Product");
        System.out.println();
        System.out.printf("%45s%s","","----------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%45s%-10s%-20s%-15s%-15s%-30s","", "ID", "Name", "Volume (ml) ", "Amount", "Price");
        System.out.println();
        System.out.printf("%45s%s","","----------------------------------------------------------------------");
        System.out.println();
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    //    hiển thị sản phẩm theo loại sữa tắm:
    public void showShowerGel() {
        readProduct();
        System.out.printf("%72s%s","","Shower Gel");
        System.out.println();
        System.out.printf("%45s%s","","----------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%45s%-10s%-20s%-15s%-15s%-30s","", "ID", "Name", "Volume (ml) ", "Amount", "Price");
        System.out.println();
        System.out.printf("%45s%s","","----------------------------------------------------------------------");
        System.out.println();
        for (Product product : products) {
            if (product instanceof ShowerGel)
                System.out.println(product);
        }
    }

    //    hiển thị sản phẩm theo loại bodymist:
    public void showBodyMist() {
        readProduct();
        System.out.printf("%72s%s","","Body Mist");
        System.out.println();
        System.out.printf("%45s%s","","----------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%45s%-10s%-20s%-15s%-15s%-30s","", "ID", "Name", "Volume (ml) ", "Amount", "Price");
        System.out.println();
        System.out.printf("%45s%s","","----------------------------------------------------------------------");
        System.out.println();
        for (Product product : products) {
            if (product instanceof BodyMist) {
                System.out.println(product);
            }
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
        readProduct();
        String nameSearch = InputString.inputString("^([a-zA-Z\\s])+");
//        tạo 1 mảng String để hứng nameSearch sau khi split
        String[] nameSearchAfterSplit = nameSearch.split(" ");
//        tạo 1 chuỗi để hứng nameSearchAfterSplit sau khi nối lại không có dấu cách:
        StringBuilder str = new StringBuilder();
        for (String s : nameSearchAfterSplit) {
            if (s != null) str.append(s);
        }


//        tạo mảng động chứa các mảng tên sau khi đã được split
        List<String[]> nameAfterSplit = new ArrayList<>();
//        split được tên product thứ i nào thì add vào mảng nameAfterSplit
        for (Product product : products) {
            nameAfterSplit.add(product.getName().split(" "));
        }
//        tạo 1 mảng String để chứa các tên sau khi nối lại không có dấu cách:
        String[] listName = new String[products.size()];
        for (int i = 0; i < listName.length; i++) {
            listName[i] = "";
        }
        for (int i = 0; i < products.size(); i++) {
            // tạo 1 mảng String để hứng các phần tử trong mảng động nameAfterSplit
            String[] string = nameAfterSplit.get(i);
            for (String s : string) {
                if (s != null) listName[i] += s;
            }
        }

        for (int i = 0; i < listName.length; i++) {
            if (listName[i].toLowerCase().contains(str.toString().toLowerCase()))
                System.out.println(products.get(i).toString());
        }

    }

    //    tạo hàm tìm kiếm sản phẩm theo id cho admin:
    public int searchProductById(String id) {
        readProduct();
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
        int index = searchProductById(scanner.nextLine());
        if (index >= 0) {
            products.remove(index);
            System.out.println("Đã xoá sản phẩm!");
        } else {
            System.out.println("Không tìm thấy ID sản phẩm!");
        }
        writeProduct();
    }

    //    tạo hàm sửa sản phẩm:
    public void editProduct() {
        int choice;
        while (true) {
            System.out.println("1. Sửa tên sản phẩm\n2. Sửa giá sản phẩm\n3. Sửa số lượng sản phẩm\n4. Back");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    editNameProduct();
                    break;
                case 2:
                    editPriceProduct();
                    break;
                case 3:
                    editAmountProduct();
                    break;
                default:
                    return;
            }
        }
    }

    //    tạo hàm sửa tên sản phẩm:
    public void editNameProduct() {
        readProduct();
        System.out.println("Nhập tên sản phẩm bạn muốn sửa tên: ");
        searchProductByName();
        System.out.println("Nhập ID sản phẩm bạn muốn sửa tên:");
        int index = searchProductById(scanner.nextLine());
        if (index >= 0) {
            System.out.println("Nhập tên mới: ");
            String name = scanner.nextLine();
            products.get(index).setName(name);
            System.out.println("Đã cập nhật tên sản phẩm!");
        } else System.out.println("Không tìm sản phẩm!");
        writeProduct();
    }

    //    tạo hàm sửa giá sản phẩm:
    public void editPriceProduct() {
        readProduct();
        System.out.println("Nhập tên sản phẩm bạn muốn sửa giá: ");
        searchProductByName();
        System.out.println("Nhập ID sản phẩm bạn muốn sửa giá: ");
        int index = searchProductById(scanner.nextLine());
        if (index >= 0) {
            System.out.println("Nhập giá mới: ");
            double price = Double.parseDouble(scanner.nextLine());
            products.get(index).setPrice(price);
            System.out.println("Đã cập nhật giá sản phẩm!");
        } else System.out.println("Không tìm thấy sản phẩm!");
        writeProduct();
    }

    //    tạo hàm sửa số lượng sản phẩm:
    public void editAmountProduct() {
        readProduct();
        System.out.println("Nhập tên sản phẩm bạn muốn sửa số lượng: ");
        searchProductByName();
        System.out.println("Nhập ID sản phẩm bạn muốn sửa số lượng: ");
        int index = searchProductById(scanner.nextLine());
        if (index >= 0) {
            System.out.println("Nhập số lượng mới:");
            int amount = Integer.parseInt(scanner.nextLine());
            products.get(index).setAmount(amount);
            System.out.println("Đã cập nhật số lượng sản phẩm!");
        } else System.out.println("Không tìm thấy sản phẩm!");
        writeProduct();
    }

    //    tạo hàm sắp xếp sản phẩm theo giá từ thấp đến cao:
    public void ascendingPrice() {
        readProduct();
        System.out.printf("%72s%s","","Ascending Price");
        System.out.println();
        System.out.printf("%45s%s","","------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%45s%-10s%-20s%-15s%-15s%-30s","", "ID", "Name", "Volume (ml) ", "Amount", "Price");
        System.out.println();
        System.out.printf("%45s%s","","------------------------------------------------------------------");
        System.out.println();
        products.sort(new ComparatorPriceAscending());
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    //    tạo hàm sắp xếp sản phẩm theo giá từ cao xuống thấp:
    public void descendingPrice() {
        readProduct();
        System.out.printf("%72s%s","","Descending Price");
        System.out.println();
        System.out.printf("%45s%s","","------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%45s%-10s%-20s%-15s%-15s%-30s","", "ID", "Name", "Volume (ml) ", "Amount", "Price");
        System.out.println();
        System.out.printf("%45s%s","","------------------------------------------------------------------");
        System.out.println();
        products.sort(new ComparatorPriceDescending());
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    //    tạo hàm lọc các sản phẩm có giá dưới 100k:
    public void lowerPrice() {
        readProduct();
        System.out.printf("%72s%s","","Lower than 100k Price");
        System.out.println();
        System.out.printf("%45s%s","","------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%45s%-10s%-20s%-15s%-15s%-30s","", "ID", "Name", "Volume (ml) ", "Amount", "Price");
        System.out.println();
        System.out.printf("%45s%s","","------------------------------------------------------------------");
        System.out.println();
        for (Product product : products) {
            if (product.getPrice() < 100) {
                System.out.println(product.toString());
            }
        }
    }

    //    tạo hàm lọc các sản phẩm có giá từ 100k - 300k
    public void betweenPrice() {
        readProduct();
        System.out.printf("%72s%s","","Between 100k and 300k Price");
        System.out.println();
        System.out.printf("%45s%s","","------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%45s%-10s%-20s%-15s%-15s%-30s","", "ID", "Name", "Volume (ml) ", "Amount", "Price");
        System.out.println();
        System.out.printf("%45s%s","","------------------------------------------------------------------");
        System.out.println();
        for (Product product : products) {
            if (product.getPrice() >= 100 && product.getPrice() <= 300) {
                System.out.println(product.toString());
            }
        }
    }

    //    tạo hàm lọc các sản phẩm có giá lớn hơn 300k:
    public void higherPrice() {
        readProduct();
        System.out.printf("%72s%s","","Higher than 300k Price");
        System.out.println();
        System.out.printf("%45s%s","","------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%45s%-10s%-20s%-15s%-15s%-30s","", "ID", "Name", "Volume (ml) ", "Amount", "Price");
        System.out.println();
        System.out.printf("%45s%s","","------------------------------------------------------------------");
        System.out.println();
        for (Product product : products) {
            if (product.getPrice() > 300) {
                System.out.println(product.toString());
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
        while (true) {
            try {
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
            }catch (Exception e) {
                System.err.println("Chỉ nhập số 1 hoặc 2!");
            }
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
        int volume;
        int amount;
        double price;
        do {
            try {
                System.out.printf("%-20s", "Nhập dung tích sản phẩm: ");
                volume = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.err.println("Dung tích phải nhập số!");
            }
        } while (true);
        do {
            try {
                System.out.printf("%-20s", "Nhập số lượng sản phẩm: ");
                amount = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.err.println("Số lượng sản phẩm phải nhập số!");
            }
        } while(true);
        do{
            try {
                System.out.printf("%-20s", "Nhập giá sản phẩm: ");
                price = Double.parseDouble(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.err.println("Nhập giá phải nhập số!");
            }
        } while (true);
        if (isShowerGel) {
            return new ShowerGel(createID(true), name, volume, amount, price);
        } else return new BodyMist(createID(false), name, volume, amount, price);
    }
}
