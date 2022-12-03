package model;

public class Admin {
    private String name = "admin";
    private String password = "1234";
    private Permission permission = Permission.ADMIN;

    public Admin() {
    }

    public Admin(String name, String password, Permission permission) {
        this.name = name;
        this.password = password;
        this.permission = permission;
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
}
