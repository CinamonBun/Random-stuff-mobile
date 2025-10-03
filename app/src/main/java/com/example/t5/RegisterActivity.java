package com.example.t5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText etNama, etEmail, etAlamat, etPassword, etConfirmPassword;
    private MaterialButton btnRegister, btnToLogin;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        // Initialize views
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etAlamat = findViewById(R.id.etAlamat);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnToLogin = findViewById(R.id.btnToLogin);
        progressBar = findViewById(R.id.progressBar);

        // Button Register Click
        btnRegister.setOnClickListener(v -> {
            String nama = etNama.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String alamat = etAlamat.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            // Validasi input
            if (nama.isEmpty()) {
                etNama.setError("Nama tidak boleh kosong!");
                etNama.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                etEmail.setError("Email tidak boleh kosong!");
                etEmail.requestFocus();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Format email tidak valid!");
                etEmail.requestFocus();
                return;
            }

            if (alamat.isEmpty()) {
                etAlamat.setError("Alamat tidak boleh kosong!");
                etAlamat.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError("Password tidak boleh kosong!");
                etPassword.requestFocus();
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

            // Cek apakah email sudah terdaftar
            if (sharedPreferences.contains("registered_email")) {
                String registeredEmail = sharedPreferences.getString("registered_email", "");
                if (registeredEmail.equals(email)) {
                    Toast.makeText(this, "Email sudah terdaftar! Silakan login.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            // Tampilkan loading
            progressBar.setVisibility(View.VISIBLE);
            btnRegister.setEnabled(false);

            // Simulasi proses register
            new android.os.Handler().postDelayed(() -> {
                // Simpan data ke SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("registered_nama", nama);
                editor.putString("registered_email", email);
                editor.putString("registered_alamat", alamat);
                editor.putString("registered_password", password);
                editor.putBoolean("hasRegistered", true);
                editor.apply();

                // Sembunyikan loading
                progressBar.setVisibility(View.GONE);
                btnRegister.setEnabled(true);

                Toast.makeText(this, "Registrasi berhasil! Silakan login.", Toast.LENGTH_LONG).show();

                // Pindah ke Login
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }, 1500); // Delay 1.5 detik untuk simulasi
        });

        // Button ke Login
        btnToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}