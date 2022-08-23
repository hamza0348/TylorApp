package com.gainingskills.tylorapp;

public class uidModel {
    String CustomerImage,CustomerName,userId;

    public uidModel() {
    }

    public uidModel(String customerImage, String customerName, String userId) {
        CustomerImage = customerImage;
        CustomerName = customerName;
        this.userId = userId;
    }


    public String getCustomerImage() {
        return CustomerImage;
    }

    public void setCustomerImage(String customerImage) {
        CustomerImage = customerImage;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
