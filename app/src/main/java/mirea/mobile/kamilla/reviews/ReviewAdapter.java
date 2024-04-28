package mirea.mobile.kamilla.reviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mirea.mobile.kamilla.R;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    private final List<Review> reviews;

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.txtName.setText(review.getName());
        holder.txtDesc.setText(review.getDescription());
        holder.txtMark.setText(String.valueOf(review.getMark()));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtMark, txtDesc;

        public ReviewHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.txtName);
            txtMark = v.findViewById(R.id.txtMark);
            txtDesc = v.findViewById(R.id.txtDesc);
        }
    }
}
