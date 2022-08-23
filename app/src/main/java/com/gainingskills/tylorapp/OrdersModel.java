package com.gainingskills.tylorapp;

public class OrdersModel {

    String button,itemId,itemimage,itemname,itemprice,pushId,uid;

    public OrdersModel() {
    }

    public OrdersModel(String button, String itemId, String itemimage, String itemname, String itemprice, String pushId, String uid) {
        this.button = button;
        this.itemId = itemId;
        this.itemimage = itemimage;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.pushId = pushId;
        this.uid = uid;
    }


    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemimage() {
        return itemimage;
    }

    public void setItemimage(String itemimage) {
        this.itemimage = itemimage;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
