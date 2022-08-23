package com.gainingskills.tylorapp;

public class AdsModel {

    String cImage,cName,cPrice,itemId;

    public AdsModel() {
    }

    public AdsModel(String cImage, String cName, String cPrice, String itemId) {
        this.cImage = cImage;
        this.cName = cName;
        this.cPrice = cPrice;
        this.itemId = itemId;
    }

    public String getcImage() {
        return cImage;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setcImage(String cImage) {
        this.cImage = cImage;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcPrice() {
        return cPrice;
    }

    public void setcPrice(String cPrice) {
        this.cPrice = cPrice;
    }
}
