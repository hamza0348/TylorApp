package com.gainingskills.tylorapp;

public class OrdersDataHolder {

    String CustomerImage,CustomerName, pushId,phone,itemId;

    public OrdersDataHolder() {
    }

    public OrdersDataHolder(String customerImage, String customerName, String pushId, String phone, String itemId) {
        CustomerImage = customerImage;
        CustomerName = customerName;
        this.pushId = pushId;
        this.phone = phone;
        this.itemId = itemId;
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

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
