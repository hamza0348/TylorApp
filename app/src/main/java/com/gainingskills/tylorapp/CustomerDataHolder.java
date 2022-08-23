package com.gainingskills.tylorapp;

public class CustomerDataHolder {


    String image,name,phone,uid;


    public CustomerDataHolder() {
    }

    public CustomerDataHolder(String image, String name, String phone, String uid) {
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
