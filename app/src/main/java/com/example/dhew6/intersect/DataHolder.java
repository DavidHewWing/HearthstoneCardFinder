package com.example.dhew6.intersect;

import java.util.List;

public class DataHolder {

    private static DataHolder dataHolder;
    private List<HearthstoneCard> dataList;

    public void setDataList(List<HearthstoneCard> dataList) {
        this.dataList = dataList;
    }

    public List<HearthstoneCard> getDataList() {
        return dataList;
    }

    public synchronized static DataHolder getInstance() {

        if (dataHolder == null) {
            dataHolder = new DataHolder();
        }
        return dataHolder;

    }

}
