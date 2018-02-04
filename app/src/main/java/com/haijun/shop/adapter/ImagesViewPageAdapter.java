package com.haijun.shop.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.xutils.ImageManager;
import org.xutils.x;

import java.util.List;

/**
 * Created by haijun on 2018/2/3.
 */

public class ImagesViewPageAdapter extends PagerAdapter {
    private List<String> mImagesList;
    private Context mContext;
    private ImageManager mImageManager;

    public ImagesViewPageAdapter(List<String> imagesList,Context context) {
        this.mImagesList = imagesList;
        this.mContext = context;
        mImageManager = x.image();
    }

    @Override
    public int getCount() {
        return mImagesList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        mImageManager.bind(imageView,mImagesList.get(position));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
