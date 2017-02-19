package com.biz.entry;

import com.shsunframework.entry.BaseEntry;

/**
 * Created by shsun on 17/1/18.
 */

public class PersonEntry extends BaseEntry {

    private final String name;
    private final String description;
    private final String price;
    private final String category;
    private final String imageName;

    public PersonEntry(String id, String name, String description, String price, String category,
                       String imageName) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImageName() {
        return imageName;
    }
}
