package manager;

import io.ReadWriteFile;
import model.Product;
import model.User;
import model.comparatorbybill.ComparatorBill;
import view.customer.MenuCustomer;

import java.util.*;

public class CustomerManager {
    ProductManager productManager = new ProductManager();
    List<User> users = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    MenuCustomer menuCustomer;
    static int index;

    public CustomerManager() {
        menuCustomer = new MenuCustomer();
    }

    public CustomerManager(MenuCustomer menuCustomer) {
        this.menuCustomer = menuCustomer;
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
                menuCustomer.showMenuCustomer();
                System.out.println("Đăng nhập thành công!");
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

    //    tạo hàm xoá tài khoản khách hàng cho admin:
    public void deleteCustomer() {
        readUsers();
        System.out.println("Nhập tên tài khoản Customer bạn muốn xoá: ");
        String name = InputString.inputString("[a-zA-Z]([a-zA-Z0-9])+");
        if (findUserByName(name) != null) {
            users.remove(x);
            System.out.println("Xoá tài khoản " + name + " thành công!");
        } else System.out.println("Không tìm thấy tài khoản Customer có tên: " + name);
        writeUsers();
    }

    //  tạo hàm tạo tài khoản khách hàng:
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
        double bill;
        productManager.readProduct();
        User currentUser = users.get(index);
        System.out.println(index);
        List<Product> productList = currentUser.getCart();
        productList.add(pickProduct());
        currentUser.setCart(productList);
        try {
            bill = currentUser.getBill();
            for (int i = 0; i < currentUser.getCart().size(); i++) {
                currentUser.setBill(bill += currentUser.getCart().get(i).getPrice() * currentUser.getCart().get(i).getAmount());
            }
            System.out.println("Đã cập nhật giỏ hàng thành công!");
            writeUsers();
            productManager.writeProduct();
        } catch (NullPointerException e) {
            System.out.println("Cập nhật giỏ hàng thất bại!");
        }
    }

    //  hàm hiển thị giỏ hàng:
    public void showCart() {
        readUsers();
        formCart();
        for (int i = 0; i < (users.get(index).getCart().size()); i++) {
            System.out.println(users.get(index).getCart().get(i).toString());
        }
        String convertBill = String.format("%.0f VND", users.get(index).getBill());
        System.out.printf("%45s%s", "", "----------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%75s%2s", "Total:", convertBill);
        System.out.println();
        System.out.printf("%45s%s", "", "----------------------------------------------------------------------");
        System.out.println();
    }
    public void formCart() {
        System.out.printf("%75s%s", "", users.get(index).getName() + "'s" + " Cart");
        System.out.println();
        System.out.printf("%45s%s", "", "----------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%45s%-10s%-20s%-15s%-15s%-30s", "", "ID", "Name", "Volume (ml) ", "Amount", "Price");
        System.out.println();
        System.out.printf("%45s%s", "", "----------------------------------------------------------------------");
        System.out.println();
    }

    //    tạo hàm tìm khách hàng tiềm năng(có tổng bill lớn nhất):
    public void showTop3VipCustomer() {
        readUsers();
        users.sort(new ComparatorBill());
        System.out.printf("%91s","Top 3 khách hàng mua hàng nhiều nhất");
        System.out.println();
        System.out.printf("%45s%s", "", "-------------------------------------------------------");
        System.out.println();
        System.out.printf("%48s%20s%20s","Top","CustomerName","Bill");
        System.out.println();
        System.out.printf("%45s%s", "", "-------------------------------------------------------");
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.printf("%47s%15s%31s",(i+1),users.get(i).getName(),users.get(i).getBill());
            System.out.println();
        }
        System.out.printf("%45s%s", "", "-------------------------------------------------------");
        System.out.println();
    }
//    tạo hàm tính tổng doanh thu:
    public void turnover() {
        readUsers();
        double sum = 0.0;
        for (int i = 1; i < users.size()-1; i++) {
            sum += users.get(i).getBill();
        }
        System.out.println(" Tổng doanh thu đạt được: "+ String.format("%.0f VND", sum) );
    }

    //    tạo hàm hiển thị danh sách khách hàng
    public void showCustomer() {
        readUsers();
        System.out.printf("%45s%45s", "", "Danh sách khách hàng");
        formShowCustomer();
        for (User user : users) {
            System.out.println(user.toString());
        }
        System.out.printf("%45s%s", "", "------------------------------------------------------------------");
        System.out.println();
    }
    public void formShowCustomer() {
        System.out.println();
        System.out.printf("%45s%s", "", "------------------------------------------------------------------");
        System.out.println();
        System.out.printf("%50s%57s", "Email", "CustomerName");
        System.out.println();
        System.out.printf("%45s%s", "", "------------------------------------------------------------------");
        System.out.println();
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
    int x;

    public User findUserByName(String name) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name)) {
                x = i;
                return users.get(i);
            }
        }
        return null;
    }


}
