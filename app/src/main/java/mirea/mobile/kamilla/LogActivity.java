package mirea.mobile.kamilla;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import mirea.mobile.kamilla.databinding.ActivityLogBinding;

public class LogActivity extends AppCompatActivity {

    private ActivityLogBinding binding;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        binding.log2Btn.setOnClickListener(v -> {
            String email = binding.editTextEmailLog.getText().toString().trim();
            String password = binding.editTextTextPasswordLog.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LogActivity.this, "Поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LogActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LogActivity.this, "Ошибка! Проверьте данные.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.switchToRegBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LogActivity.this, RegActivity.class);
            startActivity(intent);
        });
    }

}
