package mirea.mobile.kamilla;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import mirea.mobile.kamilla.databinding.FragmentContactsBinding;

public class ContactsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mirea.mobile.kamilla.databinding.FragmentContactsBinding binding = FragmentContactsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Uri uri = Uri.parse("yandexnavi://build_route_on_map?lat_to=43.577&lon_to=39.722");
        Intent intentYandexNavi = new Intent(Intent.ACTION_VIEW, uri);
        intentYandexNavi.setPackage("ru.yandex.yandexnavi");
        PackageManager packageManager = requireContext().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intentYandexNavi, 0);
        boolean isIntentSafe = activities.size() > 0;

        Button navigator_btn = binding.navigatorBtn;

        navigator_btn.setOnClickListener(v -> {
            if (isIntentSafe) {
                startActivity(intentYandexNavi);
            } else {
                Toast.makeText(requireContext(), "Ошибка! Приложение Яндекс.Навигатор не установлено.", Toast.LENGTH_SHORT).show();
            }
        });

        AppCompatTextView addressTextView = binding.txtAddress;
        addressTextView.setOnClickListener(v -> {
            String map = "http://maps.google.co.in/maps?q=" + addressTextView.getText().toString();
            Intent intentMap = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
            startActivity(intentMap);
        });

        binding.infobtn.setOnClickListener(v -> {
            InfoFragment fragment = new InfoFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        Button logoutBtn = binding.logoutbtn;
        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            Intent restartIntent = requireContext().getPackageManager().getLaunchIntentForPackage(requireContext().getPackageName());
            restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(restartIntent);
        });

        return view;
    }
}
