package com.barryirvine.tide.placepicker.ui;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.barryirvine.tide.placepicker.R;
import com.squareup.picasso.Picasso;

public class DataBindingUtils {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(@NonNull final ImageView view, @NonNull final String imageUrl) {
        if (imageUrl.equals("")) {
            view.setImageResource(R.drawable.ic_image_grey_24dp);
        } else {
            Picasso.with(view.getContext())
                    .load(imageUrl)
                    .resizeDimen(R.dimen.thumbnail_size, R.dimen.thumbnail_size)
                    .placeholder(R.drawable.ic_image_grey_24dp)
                    .centerCrop()
                    .into(view);
        }
    }
}
