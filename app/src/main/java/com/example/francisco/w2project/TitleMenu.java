package com.example.francisco.w2project;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by sunil on 12/23/16.
 */

public class TitleMenu extends ExpandableGroup<SubTitle> {

    private int imageUrl;

    public TitleMenu(String title, int imageUrl, List<SubTitle> items) {
        super(title, items);
        this.imageUrl = imageUrl;
    }

    public int getImageUrl() {
        return imageUrl;
    }
}

