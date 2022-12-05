package manager;

import io.ReadWriteFile;
import model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CustomerManager {
    ProductManager productManager = new ProductManager();
    List<User> users = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    //    tạo hàm show menu của Customer: đăng nhập, đăng ký
    public void showMenuCustomer() {
        int choice;
        while (true) {
            System.out.println("Menu:\n1. Xem sản phẩm\n2. Hiển thị sản phẩm sữa tắm\n3. Hiển thị sản phẩm bodymist\n4. Lọc sản phẩm theo giá\n5. Logout");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    productManager.showProduct();
                    break;
                case 2:
                    System.out.println("--------------------------< Shower Gel >---------------------------");
                    productManager.showShowerGel();
                    break;
                case 3:
                    System.out.println("--------------------------< Body Mist >----------------------------");
                    productManager.showBodyMist();
                    break;
                case 4:
                    showComparePrice();
                    break;
                case 5:
                    return;

            }
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
        for (User user : users) {
            if (name.equals(user.getName()) && password.equals(user.getPassword())) {
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
        System.out.printf("%-20s", "Nhập email đăng ký admin: ");
        // email, name, password
        String email;
        while (true) {
            email = InputString.inputString("[a-zA-Z][a-zA-Z0-9]*@[a-zA-Z0-9](.[a-zA-Z0-9])+");
            if (findUserByEmail(email) == null) break;
            System.out.println("Email đã tồn tại, xin mời nhập lại!");
        }
        System.out.printf("%-20s", "Nhập tên đăng ký admin: ");
        String name;
        while (true) {
            name = InputString.inputString("[a-zA-Z]([a-zA-Z0-9])+");
            if (findUserByName(name) == null) break;
            System.out.println("Name đã tồn tại, xin mời nhập lại!");
        }
        System.out.printf("%-20s", "Nhập password đăng ký admin: ");
        String password =InputString.inputString("[a-zA-Z0-9]+");
        System.out.println("Đăng ký tài khoản admin thành công!");
        return new User(email, name, password);
    }


}
