package com.biz.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.biz.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.shsunframework.adapter.BaseViewHolder;
import com.shsunframework.adapter.recyclerview.BaseRecyclerViewAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaede on 2015/10/22.
 */
public class ImageAdapter extends BaseRecyclerViewAdapter<String> {

    Map<String, Integer> heightMap = new HashMap<>();
    static Map<String, Integer> widthMap = new HashMap<>();

    public ImageAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public BaseViewHolder doCreateViewHolder(BaseViewHolder holder){
        return new ViewHolder(holder);
    }

    @Override
    public void doBindViewHolder(com.shsunframework.adapter.BaseViewHolder pholder, final String url) {

        final ViewHolder holder = (ViewHolder)pholder;

        if (heightMap.containsKey(url)) {
            int height = heightMap.get(url);
            if (height > 0) {
                updateItemtHeight(height, holder.itemView);
                holder.draweeView.setImageURI(Uri.parse(url));
                return;
            }
        }

        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                if (qualityInfo.isOfGoodEnoughQuality()) {
                    int heightTarget = (int) getTargetHeight(imageInfo.getWidth(), imageInfo.getHeight(), holder.itemView, url);
                    if (heightTarget <= 0) {
                        return;
                    }
                    heightMap.put(url, heightTarget);
                    updateItemtHeight(heightTarget, holder.itemView);
                }
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
            }
        };
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setControllerListener(controllerListener)
                .setTapToRetryEnabled(true)
                .build();
        holder.draweeView.setController(controller);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

//    public List<String> getList() {
//        return mDatas;
//    }

    private float getTargetHeight(float width, float height, View view, String url) {
        View child = view.findViewById(R.id.draweeview);
        float widthTarget;
        if (widthMap.containsKey(url)) {
            widthTarget = widthMap.get(url);
        } else {
            widthTarget = child.getMeasuredWidth();
            if (widthTarget > 0) {
                widthMap.put(url, (int) widthTarget);
            }
        }
        return height * (widthTarget / width);
    }

    private void updateItemtHeight(int height, View view) {
        CardView cardView = (CardView) view.findViewById(R.id.cardview);
        View child = view.findViewById(R.id.draweeview);
        CardView.LayoutParams layoutParams = (CardView.LayoutParams) child.getLayoutParams();
        layoutParams.height = height;
        cardView.updateViewLayout(child, layoutParams);
    }

    static class ViewHolder extends com.shsunframework.adapter.BaseViewHolder {

        public SimpleDraweeView draweeView;

        public ViewHolder(Context context, View itemView, ViewGroup parent, int position) {
            super(context, itemView, parent, position);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.draweeview);
        }

        public ViewHolder(BaseViewHolder holder) {
            super(holder);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.draweeview);
        }
    }

}