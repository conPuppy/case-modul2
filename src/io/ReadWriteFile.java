package io;

import model.User;

import java.io.*;
import java.util.List;

public final class ReadWriteFile {
    private ReadWriteFile() {
    }

    public static boolean writeToFile(String fileName, Object object) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Object readFromFile(String fileName) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
