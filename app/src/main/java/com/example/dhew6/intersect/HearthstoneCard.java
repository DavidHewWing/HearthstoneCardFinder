package com.example.dhew6.intersect;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class HearthstoneCard implements Parcelable{

    private String name;
    private String imgURL;
    private String type;
    private String playerClass;
    private String cardSet;

    public HearthstoneCard(String name, String imgURL, String type, String playerClass, String cardSet) {
        this.name = name;
        this.imgURL = imgURL;
        this.type = type;
        this.playerClass = playerClass;
        this.cardSet = cardSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
