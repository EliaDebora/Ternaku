package com.fintech.ternaku.adapters;

import com.fintech.ternaku.model.Item;

/**
 * Created by YORIS on 9/7/16.
 */
public interface ItemClickListener {
    void itemClicked(Item item);
    void itemClicked(Section section);
}
