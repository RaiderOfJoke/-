package mirea.mobile.kamilla;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mirea.mobile.kamilla.databinding.ActivityRegBinding;

public class RegActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mirea.mobile.kamilla.databinding.ActivityRegBinding binding = ActivityRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        EditText editTextNameReg = binding.editTextNameReg;
        EditText editTextEmailReg = binding.editTextEmailReg;
        EditText editTextPasswordReg = binding.editTextPasswordReg;
        Button reg2_btn = binding.reg2Btn;
        Button switchToLog_btn = binding.switchToLogBtn;

        reg2_btn.setOnClickListener(view -> {
            String name = editTextNameReg.getText().toString().trim();
            String email = editTextEmailReg.getText().toString().trim();
            String password = editTextPasswordReg.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                Toast.makeText(RegActivity.this, "Поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("UserData").child("name").setValue(name);
                                reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("UserData").child("email").setValue(email);
                                reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("UserData").child("password").setValue(password);
                                Intent intent = new Intent(RegActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegActivity.this, "Ошибка! Проверьте данные.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        switchToLog_btn.setOnClickListener(view -> {
            Intent intent = new Intent(RegActivity.this, LogActivity.class);
            startActivity(intent);
        });
    }
}
