package manager;

import io.ReadWriteFile;
import model.Permission;
import model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    ProductManager productManager = new ProductManager();
    List<User> users = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    //    tạo hàm show menu của Admin: đăng nhập, đăng ký, đăng xuất
    public void showMenuAdmin() {
        int choice;
        while (true) {
            System.out.println("Menu:\n1.Register\n2.Login\n3.Logout");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:

            }
        }
    }

    //    tạo hàm hiển thị danh sách admin
    public void showAdmin() {
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    //  tạo hàm đăng ký
    public void register() {
        readUsers();
        users.add(createAdmin());
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
        String name = inputString("[a-zA-Z]([a-zA-Z0-9])+");
        if (findUserByName(name) != null) {
            System.out.printf("%-20s", "Nhập password đăng nhập: ");
            String password = inputString("[a-zA-Z0-9]+");
            if (checkLogin(name, password)) {
                System.out.println("Đăng nhập thành công!");
                productManager.showMenuProduct();
            }
            else System.out.println("Đăng nhập thất bại!");
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

    //  tạo hàm nhận dữ liệu người dùng nhập vào:
    public static String inputString(String regex) {
        String line = scanner.nextLine();
        while (!line.matches(regex)) {
            System.out.println("Nhập sai định dạng! Xin mời nhập lại.");
            line = scanner.nextLine();
        }
        return line;
    }

    public User createAdmin() {
//        tạo đối tượng user là loại admin:
        System.out.printf("%-20s", "Nhập email đăng ký admin: ");
        // email, name, password
        String email;
        while (true) {
            email = inputString("[a-zA-Z][a-zA-Z0-9]*@[a-zA-Z0-9](.[a-zA-Z0-9])+");
            if (findUserByEmail(email) == null) break;
            System.out.println("Email đã tồn tại, xin mời nhập lại!");
        }
        System.out.printf("%-20s", "Nhập tên đăng ký admin: ");
        String name;
        while (true) {
            name = inputString("[a-zA-Z]([a-zA-Z0-9])+");
            if (findUserByName(name) == null) break;
            System.out.println("Name đã tồn tại, xin mời nhập lại!");
        }
        System.out.printf("%-20s", "Nhập password đăng ký admin: ");
        String password = inputString("[a-zA-Z0-9]+");
        System.out.println("Đăng ký tài khoản admin thành công!");
        return new User(email, name, password, Permission.ADMIN);
    }


}
