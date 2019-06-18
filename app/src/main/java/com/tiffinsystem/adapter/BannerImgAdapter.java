package com.tiffinsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tiffinsystem.R;
import com.tiffinsystem.modal.BannerImgModal;

import java.util.List;

public class BannerImgAdapter extends RecyclerView.Adapter<BannerImgAdapter.ImageViewHolder> {
    private Context context;
    private List<BannerImgModal> imageModalList;

    public BannerImgAdapter(Context context, List<BannerImgModal> imageModalList) {
        this.context = context;
        this.imageModalList = imageModalList;
    }

    @NonNull
    @Override
    public BannerImgAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.banner_show_layout, viewGroup, false);
        return new BannerImgAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerImgAdapter.ImageViewHolder imageViewHolder, int i) {
        final BannerImgModal newsInfoModal = imageModalList.get(i);
        final String imgUrl = newsInfoModal.getImgUrl();

        Glide.with(context)
                .asBitmap()
                .load(imgUrl)
                /*.placeholder(R.drawable.placeholder)
                .apply(RequestOptions.circleCropTransform())*/
                .into(imageViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return imageModalList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_banner);
        }
    }
}


