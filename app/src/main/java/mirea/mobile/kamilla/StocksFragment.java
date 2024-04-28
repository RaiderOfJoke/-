package mirea.mobile.kamilla;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mirea.mobile.kamilla.databinding.FragmentStocksBinding;

public class StocksFragment extends Fragment {

    public StocksFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mirea.mobile.kamilla.databinding.FragmentStocksBinding binding = FragmentStocksBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        TextView textView1 = binding.textViewStock1;
        TextView textView2 = binding.textViewStock2;
        TextView textView3 = binding.textViewStock3;

        reference = reference.child("Stocks");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String stock1 = snapshot.child("1").getValue().toString();
                textView1.setText(stock1);
                String stock2 = snapshot.child("2").getValue().toString();
                textView2.setText(stock2);
                String stock3 = snapshot.child("3").getValue().toString();
                textView3.setText(stock3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
