package com.example.t5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ResetActivity extends AppCompatActivity {
    private TextView tvEmail;
    private TextInputEditText etPassword, etConfirmPassword;
    private MaterialButton btnOk, btnCancel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        // Initialize views
        tvEmail = findViewById(R.id.tvEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);

        // Tampilkan email (tidak bisa diedit)
        String email = sharedPreferences.getString("current_email", "-");
        tvEmail.setText("Email: " + email);

        // Button OK - update password dan kembali ke login
        btnOk.setOnClickListener(v -> {
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (password.isEmpty()) {
                etPassword.setError("Password tidak boleh kosong!");
                etPassword.requestFocus();
                return;
            }

            if (confirmPassword.isEmpty()) {
                etConfirmPassword.setError("Konfirmasi password tidak boleh kosong!");
                etConfirmPassword.requestFocus();
                return;
            }

            if (password.length() < 6) {
                etPassword.setError("Password minimal 6 karakter!");
                etPassword.requestFocus();
                return;
            }

            if (!password.equals(confirmPassword)) {
                etConfirmPassword.setError("Password tidak cocok!");
                etConfirmPassword.requestFocus();
                Toast.makeText(this, "Password tidak cocok!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update password di SharedPreferences (data registered)
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("registered_password", password);

            // Logout user setelah reset
            editor.putBoolean("isLoggedIn", false);
            editor.remove("current_nama");
            editor.remove("current_email");
            editor.remove("current_alamat");
            editor.apply();

            Toast.makeText(this, "Password berhasil direset!", Toast.LENGTH_SHORT).show();

            // Kembali ke login
            Intent intent = new Intent(ResetActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Button Cancel - kembali ke Dashboard
        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(ResetActivity.this, t6.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}