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
            try {
                System.out.println("Menu:\n1. Xem sản phẩm theo loại\n2. Xem sản phẩm theo giá\n3. Tìm kiếm sản phẩm theo tên" +
                        "\n4. Thêm sản phẩm vào giỏ hàng\n5. Xem giỏ hàng\n6. Đổi mật khẩu\n7. Đổi username\n8. Logout");
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> productManager.showTypeProduct();
                    case 2 -> showComparePrice();
                    case 3 -> {
                        System.out.println("Nhập tên sản phẩm muốn tìm: ");
                        productManager.searchProductByName();
                    }
                    case 4 -> updateCart();
                    case 5 -> showCart();
                    case 6 -> {
                        if(changePassword()) return;
                    }
                    case 7 -> {
                        if(changeUserName()) {
                            System.exit(0);
                        } else {
                            return;
                        }
                    }
                    default -> {
                        writeUsers();
                        return;
                    }
                }
            } catch (Exception e) {
                System.err.println("Nhập số đê!");
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
            if (number <= ProductManager.products.get(index).getAmount()) {
                Product product = new Product(id, ProductManager.products.get(index).getName(), ProductManager.products.get(index).getVolume(), number, ProductManager.products.get(index).getPrice());
                ProductManager.products.get(index).setAmount(ProductManager.products.get(index).getAmount() - product.getAmount());
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
        double bill = currentUser.getBill();
        for (int i = 0; i < currentUser.getCart().size(); i++) {
            currentUser.setBill(bill += currentUser.getCart().get(i).getPrice() * currentUser.getCart().get(i).getAmount());
        }
        System.out.println("Đã cập nhật giỏ hàng thành công!");
        writeUsers();
        productManager.writeProduct();
    }

    //  hàm hiển thị giỏ hàng:
    public void showCart() {
        readUsers();
        System.out.printf("%75s%s", "", "My Cart");
        System.out.println();
        System.out.printf("%45s%s", "", "------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%45s%-10s%-20s%-15s%-15s%-30s", "", "ID", "Name", "Volume (ml) ", "Amount", "Price");
        System.out.println();
        System.out.printf("%45s%s", "", "------------------------------------------------------------------");
        System.out.println();
        for (int i = 0; i < (users.get(index).getCart().size()); i++) {
            System.out.println(users.get(index).getCart().get(i).toString());
        }
        String convertBill = String.format("%.0f VND", users.get(index).getBill());
        System.out.printf("%45s%s", "", "------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%75s%2s", "Total:", convertBill);
        System.out.println();
        System.out.printf("%45s%s", "", "------------------------------------------------------------------");
        System.out.println();
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

    //  tạo hàm đăng ký
    public void register() {
        readUsers();
        users.add(createCustomer());
        writeUsers();
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

    // tạo hàm đổi mật khẩu:
    public boolean changePassword() {
        readUsers();
        System.out.println("Nhập lại mật khẩu cũ: ");
        String password = InputString.inputString("[a-zA-Z0-9]+");
        if (password.equals(users.get(index).getPassword())) {
            System.out.println("Nhập mật khẩu mới: ");
            String newPassword = InputString.inputString("[a-zA-Z0-9]+");
            users.get(index).setPassword(newPassword);
            writeUsers();
            System.out.println("Đổi mật khẩu thành công!");
            return true;
        }
        return false;
    }
//    tạo hàm đổi username:
    public boolean changeUserName() {
        readUsers();
        System.out.println("Nhập lại mật khẩu cũ: ");
        String password = InputString.inputString("[a-zA-Z0-9]+");
        if (password.equals(users.get(index).getPassword())) {
            System.out.println("Nhập username mới: ");
            String newUserName = InputString.inputString("[a-zA-Z]([a-zA-Z0-9])+");
            users.get(index).setName(newUserName);
            writeUsers();
            System.out.println("Đổi UserName thành công!");
            return true;
        }
        return false;
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


}
