package com.fintech.ternaku.adapters;

/**
 * Created by YORIS on 9/7/16.
 */
public class Section {

    private final String name;

    public boolean isExpanded;

    public Section(String name) {
        this.name = name;
        isExpanded = true;
    }

    public String getName() {
        return name;
    }
}
