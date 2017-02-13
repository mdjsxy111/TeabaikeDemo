package com.estyle.teabaike.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CollectionBean {

    @Id()
    private long id;
    @Property(nameInDb = "current_time_millis")
    private long currentTimeMillis;
    @Property(nameInDb = "title")
    private String title;
    @Property(nameInDb = "source")
    private String source;
    @Property(nameInDb = "create_time")
    private String create_time;
    @Property(nameInDb = "author")
    private String author;

    @Generated(hash = 1651767811)
    public CollectionBean(long id, long currentTimeMillis, String title,
            String source, String create_time, String author) {
        this.id = id;
        this.currentTimeMillis = currentTimeMillis;
        this.title = title;
        this.source = source;
        this.create_time = create_time;
        this.author = author;
    }

    @Generated(hash = 1423617684)
    public CollectionBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    public void setCurrentTimeMillis(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}