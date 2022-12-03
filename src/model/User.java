package model;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String name;
    private String password;
    private Permission permission;

    public User() {
    }

    public User(String email, String name, String password, Permission permission) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.permission = permission;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return String.format("%-30s%-20s",email,name);
    }
}
