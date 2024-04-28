package mirea.mobile.kamilla;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mirea.mobile.kamilla.databinding.FragmentGalleryBinding;
import mirea.mobile.kamilla.reviews.ImageAdapter;
import mirea.mobile.kamilla.reviews.ImageData;
import mirea.mobile.kamilla.reviews.Review;
import mirea.mobile.kamilla.reviews.ReviewAdapter;

public class GalleryFragment extends Fragment {
    private final boolean isImageScaled = false;
    private List<Review> reviews;
    private ReviewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mirea.mobile.kamilla.databinding.FragmentGalleryBinding binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        RecyclerView recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        reviews = loadReviewsFromAsset();
        adapter = new ReviewAdapter(reviews);
        recyclerView.setAdapter(adapter);

        RecyclerView imageRecyclerView = binding.recyclerimage;
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        List<ImageData> images = new ArrayList<>();
        images.add(new ImageData(R.drawable.a));
        images.add(new ImageData(R.drawable.b));
        images.add(new ImageData(R.drawable.c));

        ImageAdapter imageAdapter = new ImageAdapter(images);
        imageRecyclerView.setAdapter(imageAdapter);


        Button btnSortByName = binding.btnSortByName;
        Button btnSortByMarkAsc = binding.btnSortByMarkAsc;
        Button btnSortByMarkDesc = binding.btnSortByMarkDesc;

        btnSortByName.setOnClickListener(v -> {
            Collections.sort(reviews, (r1, r2) -> r1.getName().compareTo(r2.getName()));
            adapter.notifyDataSetChanged();
        });

        btnSortByMarkAsc.setOnClickListener(v -> {
            Collections.sort(reviews, (r1, r2) -> r1.getMark() - r2.getMark());
            adapter.notifyDataSetChanged();
        });

        btnSortByMarkDesc.setOnClickListener(v -> {
            Collections.sort(reviews, (r1, r2) -> r2.getMark() - r1.getMark());
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    private List<Review> loadReviewsFromAsset() {
        String json;
        try {
            InputStream is = requireContext().getAssets().open("reviews.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e("GalleryFragment", "Error reading reviews.json", ex);
            return new ArrayList<>(); // возвращаем пустой список, а не null
        }

        try {
            Gson gson = new Gson();
            Type reviewListType = new TypeToken<ArrayList<Review>>() {
            }.getType();
            return gson.fromJson(json, reviewListType);
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
            Log.e("GalleryFragment", "Error parsing reviews.json", ex);
            return new ArrayList<>(); // возвращаем пустой список, а не null
        }
    }


}
