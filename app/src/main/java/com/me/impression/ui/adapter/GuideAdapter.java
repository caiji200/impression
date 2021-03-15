package com.me.impression.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.me.impression.R;
import com.me.impression.entity.GuideBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class GuideAdapter extends BannerAdapter<GuideBean, GuideAdapter.BannerViewHolder> {

    public GuideAdapter(List<GuideBean> mDatas) {
        super(mDatas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guide,
                parent,false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindView(BannerViewHolder holder, GuideBean data, int position, int size) {
        holder.lottieView.setAnimation(data.getAnimResId());
        holder.guideTextTv.setText(data.getMessage());
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        LottieAnimationView lottieView;
        TextView guideTextTv;;
        public BannerViewHolder(@NonNull View view) {
            super(view);
            this.lottieView = view.findViewById(R.id.lottieAnimationView);
            this.guideTextTv = view.findViewById(R.id.guideTextTv);
        }
    }
}