package mirea.mobile.kamilla;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import mirea.mobile.kamilla.databinding.FragmentMainMenuBinding;

public class MainMenuFragment extends Fragment {

    public MainMenuFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mirea.mobile.kamilla.databinding.FragmentMainMenuBinding binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        final BookingFragment bookingFragment = new BookingFragment();
        final GalleryFragment galleryFragment = new GalleryFragment();
        final ContactsFragment contactsFragment = new ContactsFragment();
        final StocksFragment stocksFragment = new StocksFragment();

        BottomNavigationView bottomNavigationView = binding.bottomNavMenu;
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;
            switch (item.getItemId()) {
                case R.id.booking:
                    selectedFragment = bookingFragment;
                    break;
                case R.id.contacts:
                    selectedFragment = contactsFragment;
                    break;
                case R.id.gallery:
                    selectedFragment = galleryFragment;
                    break;
                case R.id.stocks:
                    selectedFragment = stocksFragment;
                    break;
                default:
                    selectedFragment = bookingFragment;
                    break;
            }
            getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });

        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, bookingFragment).commit();

        return view;
    }
}
