package mirea.mobile.kamilla;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mirea.mobile.kamilla.databinding.FragmentBookingBinding;

public class BookingFragment extends Fragment {

    String need_bed = "Нет";
    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mirea.mobile.kamilla.databinding.FragmentBookingBinding binding = FragmentBookingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mirea21hotel-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference();

        Spinner spinner_room_type = binding.spinnerRoomType;
        Spinner spinner_guests_number = binding.spinnerGuestsNumber;
        EditText editTextDateIn = binding.editTextDateIn;
        EditText editTextDateOut = binding.editTextDateOut;
        Switch switch_bed = binding.switchBed;

        Button booking_btn = binding.bookingBtn;
        Button bookinglist_btn = binding.bookinglistBtn;
        Button btnDelete = binding.btnDelete;

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        setDatePickerDialog(requireContext(), editTextDateIn, format);
        setDatePickerDialog(requireContext(), editTextDateOut, format);

        switch_bed.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                need_bed = "Да";
            } else {
                need_bed = "Нет";
            }
        });

        booking_btn.setOnClickListener(v -> {
            String selected_room_type = spinner_room_type.getSelectedItem().toString();
            String selected_guests_number = spinner_guests_number.getSelectedItem().toString();

            String date_in_str = editTextDateIn.getText().toString();
            String date_out_str = editTextDateOut.getText().toString();

            Date date_in;
            Date date_out;
            try {
                date_in = format.parse(date_in_str);
                date_out = format.parse(date_out_str);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Пожалуйста, введите дату в формате dd-MM-yyyy", Toast.LENGTH_LONG).show();
                return;
            }

            reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("Booking").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() > 5) {
                        Toast.makeText(getContext(), "На эти даты всё занято", Toast.LENGTH_LONG).show();
                    } else if (date_out.before(date_in)) {
                        Toast.makeText(getContext(), "Даты выбраны некорректно", Toast.LENGTH_LONG).show();
                    } else {
                        DatabaseReference bookingRef = reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("Booking").push();

                        bookingRef.child("room_type").setValue(selected_room_type);
                        bookingRef.child("guests_number").setValue(selected_guests_number);
                        bookingRef.child("date_in").setValue(date_in.getTime());
                        bookingRef.child("date_out").setValue(date_out.getTime());
                        bookingRef.child("child_bed").setValue(need_bed);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                }
            });
        });

        bookinglist_btn.setOnClickListener(v -> {
            UserBookingsFragment fragment = new UserBookingsFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        btnDelete.setOnClickListener(v -> {
            reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("Booking").removeValue();
        });

        return view;
    }

    private void setDatePickerDialog(Context context, EditText editText, SimpleDateFormat format) {
        editText.setFocusable(false);
        editText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    (datePicker, year, month, day) -> {
                        calendar.set(year, month, day);
                        String date = format.format(calendar.getTime());
                        editText.setText(date);
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
    }
}