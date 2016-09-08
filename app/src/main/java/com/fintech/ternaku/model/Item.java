package com.fintech.ternaku.model;

import android.graphics.Bitmap;

/**
 * Created by YORIS on 9/7/16.
 */
public class Item {
    private final String name;
    private final int id;
    private final Bitmap img;

    public Item(String name, int id, Bitmap img) {
        this.name = name;
        this.id = id;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public Bitmap getImg(){return img;}
}
