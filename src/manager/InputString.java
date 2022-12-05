package manager;

import java.util.Scanner;

//tạo class chứa phương thức nhập dữ liệu từ người dùng:

public final class InputString {
    static Scanner scanner = new Scanner(System.in);
    public static String inputString(String regex) {
        String line = scanner.nextLine();
        while (!line.matches(regex)) {
            System.out.println("Nhập sai định dạng! Xin mời nhập lại.");
            line = scanner.nextLine();
        }
        return line;
    }
}
