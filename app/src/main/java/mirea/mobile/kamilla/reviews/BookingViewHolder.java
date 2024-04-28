package mirea.mobile.kamilla.reviews;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import mirea.mobile.kamilla.R;

public class BookingViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewRoomType;
    public TextView textViewGuestsNumber;
    public TextView textViewDateIn;
    public TextView textViewDateOut;
    public TextView textViewChildBed;

    public BookingViewHolder(View itemView) {
        super(itemView);
        textViewRoomType = itemView.findViewById(R.id.textViewRoomType);
        textViewGuestsNumber = itemView.findViewById(R.id.textViewGuestsNumber);
        textViewDateIn = itemView.findViewById(R.id.textViewDateIn);
        textViewDateOut = itemView.findViewById(R.id.textViewDateOut);
        textViewChildBed = itemView.findViewById(R.id.textViewChildBed);
    }
}
