package model;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
//    thể tích, dung tích sản phẩm
    private int volume;
//    số lượng sản phẩm
    private int amount;
    private double price;

    public Product() {
    }


    public Product(String id, String name, int volume, int amount, double price) {
        this.id = id;
        this.name = name;
        this.volume = volume;
        this.amount = amount;
        this.price = price;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public String covertPrice(double price) {
        String covertPrice = String.format("%.0f VND", price);
        return covertPrice;
    }

    @Override
    public String toString() {
        return String.format("%45s%-10s%-20s%-15s%-15s%-30s","",id,name,volume,amount,covertPrice(getPrice()));
    }

}
