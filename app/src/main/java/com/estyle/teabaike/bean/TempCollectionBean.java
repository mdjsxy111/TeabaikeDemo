package com.estyle.teabaike.bean;

public class TempCollectionBean {

    private int position;
    private CollectionBean collection;

    public TempCollectionBean(int position, CollectionBean collection) {
        this.position = position;
        this.collection = collection;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public CollectionBean getCollection() {
        return collection;
    }

    public void setCollection(CollectionBean collection) {
        this.collection = collection;
    }
}
