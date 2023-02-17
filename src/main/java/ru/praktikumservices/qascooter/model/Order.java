package ru.praktikumservices.qascooter.model;

public class Order {

    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private Color color;

    public Order() {
    }

    public Order(String firstName, String lastName, String address, int metroStation, String phone, int rentTime,
                 String deliveryDate, String comment, Color color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
}
