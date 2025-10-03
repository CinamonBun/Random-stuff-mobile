package com.example.t5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin, btnRegister;
    private MaterialCheckBox cbRememberMe;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        // Cek apakah user sudah login (Remember Me)
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            // Langsung ke Dashboard
            Intent intent = new Intent(LoginActivity.this, t6.class);
            startActivity(intent);
            finish();
            return;
        }

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        progressBar = findViewById(R.id.progressBar);

        // Button Login Click
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validasi input
            if (email.isEmpty()) {
                etEmail.setError("Email tidak boleh kosong!");
                etEmail.requestFocus();
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

            // Cek apakah user sudah register
            if (!sharedPreferences.getBoolean("hasRegistered", false)) {
                Toast.makeText(this, "Anda belum terdaftar! Silakan register terlebih dahulu.", Toast.LENGTH_LONG).show();
                return;
            }

            // Tampilkan loading
            progressBar.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);

            // Simulasi proses login
            new android.os.Handler().postDelayed(() -> {
                // Ambil data yang sudah diregister
                String registeredEmail = sharedPreferences.getString("registered_email", "");
                String registeredPassword = sharedPreferences.getString("registered_password", "");
                String registeredNama = sharedPreferences.getString("registered_nama", "");
                String registeredAlamat = sharedPreferences.getString("registered_alamat", "");

                // Cek email dan password
                if (email.equals(registeredEmail) && password.equals(registeredPassword)) {
                    // Login berhasil
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("current_nama", registeredNama);
                    editor.putString("current_email", registeredEmail);
                    editor.putString("current_alamat", registeredAlamat);
                    editor.putBoolean("isLoggedIn", true); // Set TRUE
                    editor.commit(); // Gunakan commit() bukan apply()

                    // DEBUG: Cek apakah data tersimpan
                    boolean saved = sharedPreferences.getBoolean("isLoggedIn", false);
                    Toast.makeText(this, "DEBUG: isLoggedIn = " + saved, Toast.LENGTH_LONG).show();

                    // Sembunyikan loading
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);

                    Toast.makeText(this, "Login Berhasil! Nama: " + registeredNama, Toast.LENGTH_SHORT).show();

                    // Pindah ke Dashboard
                    Intent intent = new Intent(LoginActivity.this, t6.class);
                    startActivity(intent);
                    finish();
                }
            }, 1500);
        });

        // Button Register Click - KE HALAMAN REGISTER
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}