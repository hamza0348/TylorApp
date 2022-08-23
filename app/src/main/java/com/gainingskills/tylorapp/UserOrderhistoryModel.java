package com.gainingskills.tylorapp;

public class UserOrderhistoryModel {

    String img,itemId,name,price,uid;

    public UserOrderhistoryModel() {
    }

    public UserOrderhistoryModel(String img, String itemId, String name, String price, String uid) {
        this.img = img;
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.uid = uid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
