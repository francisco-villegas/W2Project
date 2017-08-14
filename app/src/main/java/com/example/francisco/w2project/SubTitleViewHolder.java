package com.example.francisco.w2project;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by kuliza-195 on 12/23/16.
 */

public class SubTitleViewHolder extends ChildViewHolder {

    private TextView subTitleTextView;
    private ImageView subTitleImageView;

    public SubTitleViewHolder(View itemView) {
        super(itemView);
        subTitleTextView = (TextView) itemView.findViewById(R.id.subtitle);
        subTitleImageView = (ImageView) itemView.findViewById(R.id.list_item_genre_arrow);
    }

    public void setSubTitleName(String name) {
        subTitleTextView.setText(name);
    }

    public void setSubtitleImg(Context context, int draw_res) {
        Drawable res = ContextCompat.getDrawable(context,draw_res);
        subTitleImageView.setImageDrawable(res);
    }
}
