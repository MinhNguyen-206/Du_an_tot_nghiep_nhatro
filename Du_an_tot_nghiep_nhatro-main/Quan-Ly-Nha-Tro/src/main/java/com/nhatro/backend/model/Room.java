package com.nhatro.backend.model;

public class Room {

    private int id;

    private String title;
    private String category;
    private String location;

    private String price;
    private String area;
    private String deposit;
    private String status;

    private String image;

    private String ownerName;
    private String ownerAvatar;
    private String ownerPhone;

    private String description;

    private String electricity;
    private String water;
    private String serviceFee;
    private String internet;

    private String address;

    public Room() {
    }

    public Room(
            int id,
            String title,
            String category,
            String location,
            String price,
            String area,
            String deposit,
            String status,
            String image,
            String ownerName,
            String ownerAvatar,
            String ownerPhone,
            String description,
            String electricity,
            String water,
            String serviceFee,
            String internet,
            String address
    ) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.location = location;
        this.price = price;
        this.area = area;
        this.deposit = deposit;
        this.status = status;
        this.image = image;
        this.ownerName = ownerName;
        this.ownerAvatar = ownerAvatar;
        this.ownerPhone = ownerPhone;
        this.description = description;
        this.electricity = electricity;
        this.water = water;
        this.serviceFee = serviceFee;
        this.internet = internet;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getInternet() {
        return internet;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}