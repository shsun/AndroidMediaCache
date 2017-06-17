package com.biz.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;

import com.biz.R;
import com.biz.entry.ImageEntry;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.base.adapter.BaseViewHolder;
import com.base.adapter.recyclerview.BaseRecyclerViewAdapter;

import java.util.HashMap;
import java.util.Map;


public class ImageAdapter extends BaseRecyclerViewAdapter<ImageEntry> {

    public static final String TAG = "ImageAdapter";

    Map<String, Integer> heightMap = new HashMap<>();
    static Map<String, Integer> widthMap = new HashMap<>();

    public ImageAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public BaseViewHolder doCreateViewHolder(BaseViewHolder pholder){
        return new ViewHolder(pholder);
    }

    @Override
    public void doBindViewHolder(com.base.adapter.BaseViewHolder pholder, final ImageEntry pdata) {
        final ViewHolder holder = (ViewHolder)pholder;
        if (heightMap.containsKey(pdata.getUrl())) {
            int height = heightMap.get(pdata.getUrl());
            if (height > 0) {
                updateItemtHeight(height, holder.itemView);
                holder.draweeView.setImageURI(Uri.parse(pdata.getUrl()));
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
                    int heightTarget = (int) getTargetHeight(imageInfo.getWidth(), imageInfo.getHeight(), holder.itemView, pdata.getUrl());
                    if (heightTarget <= 0) {
                        return;
                    }
                    heightMap.put(pdata.getUrl(), heightTarget);
                    updateItemtHeight(heightTarget, holder.itemView);
                }
                FLog.i(TAG, "onFinalImageSet, id="+id);
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                FLog.e(TAG, "onIntermediateImageSet, id="+id);
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                FLog.e(TAG, "onFailure, id="+id + ", msg=" + throwable.getLocalizedMessage());
            }
        };
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(pdata.getUrl()))
                .setControllerListener(controllerListener)
                .setTapToRetryEnabled(true)
                .build();
        holder.draweeView.setController(controller);
        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_5.mp4";
                Intent intent = new Intent(ImageAdapter.this.mContext, VitamioVideoPlayerActivity.class);
                intent.putExtra(VitamioVideoPlayerActivity.VIDEO_PLAYER_KEY_URL, url);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ImageAdapter.this.mContext.startActivity(intent);
            }
        });
        */
    }

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

    /**
     *
     */
    static class ViewHolder extends com.base.adapter.BaseViewHolder {

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