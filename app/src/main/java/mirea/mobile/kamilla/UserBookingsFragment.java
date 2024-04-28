package mirea.mobile.kamilla;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mirea.mobile.kamilla.databinding.FragmentUserBookingsBinding;
import mirea.mobile.kamilla.reviews.Booking;
import mirea.mobile.kamilla.reviews.BookingViewHolder;

public class UserBookingsFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private List<Booking> bookings;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mirea.mobile.kamilla.databinding.FragmentUserBookingsBinding binding = FragmentUserBookingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mirea21hotel-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference();
        reference = reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("Booking");

        recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookings = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        adapter = new RecyclerView.Adapter<BookingViewHolder>() {
            @NonNull
            @Override
            public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_booking, parent, false);
                return new BookingViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
                Booking booking = bookings.get(position);
                holder.textViewRoomType.setText("Тип номера: " + booking.getRoom_type());
                holder.textViewGuestsNumber.setText("Количество гостей: " + booking.getGuests_number());
                holder.textViewDateIn.setText("Дата заезда: " + format.format(booking.getDate_in()));
                holder.textViewDateOut.setText("Дата выезда: " + format.format(booking.getDate_out()));
                holder.textViewChildBed.setText("Детская кроватка: " + booking.getChild_bed());
            }

            @Override
            public int getItemCount() {
                return bookings.size();
            }
        };
        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String roomType = dataSnapshot.child("room_type").getValue(String.class);
                        String guestsNumber = dataSnapshot.child("guests_number").getValue(String.class);
                        String childBed = dataSnapshot.child("child_bed").getValue(String.class);
                        Long dateInTimestamp = dataSnapshot.child("date_in").getValue(Long.class);
                        Long dateOutTimestamp = dataSnapshot.child("date_out").getValue(Long.class);

                        if (dateInTimestamp != null && dateOutTimestamp != null) {
                            Date dateIn = new Date(dateInTimestamp);
                            Date dateOut = new Date(dateOutTimestamp);

                            Booking booking = new Booking(roomType, guestsNumber, dateIn, dateOut, childBed);
                            bookings.add(booking);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
