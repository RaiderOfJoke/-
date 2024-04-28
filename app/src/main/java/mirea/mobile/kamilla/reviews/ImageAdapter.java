package mirea.mobile.kamilla.reviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mirea.mobile.kamilla.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private final List<ImageData> imageDataList;

    public ImageAdapter(List<ImageData> imageDataList) {
        this.imageDataList = imageDataList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageData imageData = imageDataList.get(position);
        holder.imageView.setImageResource(imageData.getResourceId());
    }

    @Override
    public int getItemCount() {
        return imageDataList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.imageView);
        }
    }
}
