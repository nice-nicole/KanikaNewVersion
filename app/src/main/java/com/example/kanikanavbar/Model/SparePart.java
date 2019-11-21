package com.example.kanikanavbar.Model;

import com.google.firebase.database.Exclude;

public class SparePart {
    private String mName;
    private String mImageUrl;
    private String mCategory;
    private String mPrice;
    private String mPhoneNumber;

    public SparePart() {
        //empty constructor needed
    }

    public SparePart(String name, String imageUrl, String category, String price, String phoneNUmber) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
        mCategory= category;
        mPrice= price;
        mPhoneNumber= phoneNUmber;
    }

    public String getName() {
        return mName;
    }



    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }


}
