package manager;

import io.ReadWriteFile;
import model.Product;
import model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CustomerManager {
    ProductManager productManager = new ProductManager();
    List<User> users = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    int index;

    //    tạo hàm show menu của Customer sau khi đăng nhập
    public void showMenuCustomer() {
        int choice;
        while (true) {
            System.out.println("Menu:\n1. Xem sản phẩm\n2. Hiển thị sản phẩm sữa tắm\n3. Hiển thị sản phẩm bodymist\n4. Lọc sản phẩm theo giá\n5. Thêm sản phẩm vào giỏ hàng\n6. Xem giỏ hàng\n7. Logout");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> productManager.showProduct();
                case 2 -> {
                    System.out.println("--------------------------< Shower Gel >---------------------------");
                    productManager.showShowerGel();
                }
                case 3 -> {
                    System.out.println("--------------------------< Body Mist >----------------------------");
                    productManager.showBodyMist();
                }
                case 4 -> showComparePrice();
                case 5 -> updateCart();
                case 6 -> showCart();
                default -> {
                    writeUsers();
                    return;
                }
            }
        }
    }


    //    tạo hàm chọn sản phẩm vào giỏ hàng:
    public Product pickProduct() {
        System.out.println("Nhập ID sản phẩm muốn thêm vào giỏ hàng: ");
        String id = scanner.nextLine();
        int index = productManager.searchProductById(id);
        if (index >= 0) {
            System.out.println("Nhập số lượng muốn thêm vào giỏ hàng: ");
            int number = Integer.parseInt(scanner.nextLine());
            if(number<= ProductManager.products.get(index).getAmount()){
                Product product = new Product(id, ProductManager.products.get(index).getName(), ProductManager.products.get(index).getVolume(), number, ProductManager.products.get(index).getPrice());
                ProductManager.products.get(index).setAmount(ProductManager.products.get(index).getAmount()-product.getAmount());
                return product;
            } else {
                System.out.println("Nhập quá số lượng sản phẩm còn trong kho!");
                return null;
            }

        } else return null;
    }


    //    tạo hàm cập nhật giỏ hàng:
    public void updateCart() {
        readUsers();
        productManager.readProduct();
        User currentUser = users.get(index);
        List<Product> productList = currentUser.getCart();
        productList.add(pickProduct());
        currentUser.setCart(productList);
        for (int i = 0; i < currentUser.getCart().size(); i++) {
            System.out.println(currentUser.getCart().get(i).toString());
        }
        writeUsers();
        productManager.writeProduct();
    }

    public void showCart() {
        readUsers();
        for (int i = 0; i < (users.get(index).getCart().size()); i++) {
            System.out.println(users.get(index).getCart().get(i).toString());
        }
    }

    //    tạo hàm hiển thị danh sách khách hàng
    public void showCustomer() {
        readUsers();
        System.out.println("-------------------------------------------------");
        System.out.printf("%-30s%-20s", "Email", "CustomerName");
        System.out.println();
        System.out.println("-------------------------------------------------");
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    //    tạo hàm hiển thị lọc giá từ thấp đến cao và ngược lại:
    public void showComparePrice() {
        int choice;
        while (true) {
            System.out.println("Lọc sản phẩm theo giá:\n1. Giá từ thấp đến cao\n2. Giá từ cao xuống thấp\n3. Back");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("--------------------------< Ascending Price >----------------------------");
                    productManager.ascendingPrice();
                    break;
                case 2:
                    System.out.println("--------------------------< Descending Price >----------------------------");
                    productManager.descendingPrice();
                    break;
                default:
                    return;
            }
        }
    }

    //  tạo hàm đăng ký
    public void register() {
        readUsers();
        users.add(createCustomer());
        writeUsers();
    }

    //  hàm viết dữ liệu đăng kí tài khoản vào file:
    public void writeUsers() {
        ReadWriteFile.writeToFile("useraccount.txt", users.toArray(User[]::new));
    }

    //  hàm đọc dữ liệu:
    public void readUsers() {
        try {
            User[] users = (User[]) ReadWriteFile.readFromFile("useraccount.txt");
            this.users = users != null ? new ArrayList<>(Arrays.asList(users)) : new ArrayList<>();
        } catch (ClassCastException e) {
            e.printStackTrace();
            this.users = new ArrayList<>();
        }
    }

    //    tạo hàm đăng nhập
    public void login() {
        readUsers();
        System.out.printf("%-20s", "Nhập tên đăng nhập: ");
        String name = InputString.inputString("[a-zA-Z]([a-zA-Z0-9])+");
        if (findUserByName(name) != null) {
            System.out.printf("%-20s", "Nhập password đăng nhập: ");
            String password = InputString.inputString("[a-zA-Z0-9]+");
            if (checkLogin(name, password)) {
                System.out.println("Đăng nhập thành công!");
                showMenuCustomer();
            } else System.out.println("Đăng nhập thất bại!");
        } else System.out.println("Không tồn tại tên tài khoản!Đăng nhập thất bại!");
    }

    //    tạo hàm check tài khoản đăng nhập
    public boolean checkLogin(String name, String password) {
        for (int i = 0; i < users.size(); i++) {
            if (name.equals(users.get(i).getName()) && password.equals(users.get(i).getPassword())) {
                index = i;
                return true;
            }
        }
        return false;
    }

    //  tạo hàm tìm kiếm theo Email
    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    //  tạo hàm tìm kiếm theo tên
    public User findUserByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }


    public User createCustomer() {
//        tạo đối tượng user là khách hàng:
        System.out.printf("%-20s", "Nhập email đăng ký tài khoản: ");
        // email, name, password
        String email;
        while (true) {
            email = InputString.inputString("[a-zA-Z][a-zA-Z0-9]*@[a-zA-Z0-9](.[a-zA-Z0-9])+");
            if (findUserByEmail(email) == null) break;
            System.out.println("Email đã tồn tại, xin mời nhập lại!");
        }
        System.out.printf("%-20s", "Nhập tên đăng ký tài khoản: ");
        String name;
        while (true) {
            name = InputString.inputString("[a-zA-Z]([a-zA-Z0-9])+");
            if (findUserByName(name) == null) break;
            System.out.println("Name đã tồn tại, xin mời nhập lại!");
        }
        System.out.printf("%-20s", "Nhập password đăng ký tài khoản: ");
        String password = InputString.inputString("[a-zA-Z0-9]+");
        System.out.println("Đăng ký tài khoản khách hàng thành công!");
        return new User(email, name, password);
    }


}
