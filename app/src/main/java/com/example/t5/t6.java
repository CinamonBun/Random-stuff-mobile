package com.example.t5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;
import android.widget.TextClock;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class t6 extends AppCompatActivity {
    private TextClock textClock;
    private TextView tvWelcome, tvTanggal;
    private MaterialButton btnDetail, btnReset, btnLogout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t6);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        // Cek apakah user sudah login
        if (!sharedPreferences.getBoolean("isLoggedIn", false)) {
            // Jika belum login, kembali ke halaman login
            Intent intent = new Intent(t6.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Initialize views
        textClock = findViewById(R.id.textClock);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvTanggal = findViewById(R.id.tvTanggal);
        btnDetail = findViewById(R.id.btnDetail);
        btnReset = findViewById(R.id.btnReset);
        btnLogout = findViewById(R.id.btnLogout);

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

// DEBUG: Cek data
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        String nama = sharedPreferences.getString("current_nama", "KOSONG");
        Toast.makeText(this, "DEBUG: isLoggedIn=" + isLoggedIn + ", nama=" + nama, Toast.LENGTH_LONG).show();

// Cek apakah user sudah login
        if (!isLoggedIn) {
            Toast.makeText(this, "Belum login, kembali ke LoginActivity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(t6.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        tvWelcome.setText("Selamat datang, " + nama + "!");

        // Set tanggal saat ini
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MM-yyyy", new Locale("id", "ID"));
        String tanggal = sdf.format(new Date());
        tvTanggal.setText("Tanggal saat ini: " + tanggal);

        // Button Detail
        btnDetail.setOnClickListener(v -> {
            Intent intent = new Intent(t6.this, DetailActivity.class);
            startActivity(intent);
        });

        // Button Reset
        btnReset.setOnClickListener(v -> {
            Intent intent = new Intent(t6.this, ResetActivity.class);
            startActivity(intent);
        });

        // Button Logout
        btnLogout.setOnClickListener(v -> {
            // Hapus session login (tapi tetap simpan data registered)
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.remove("current_nama");
            editor.remove("current_email");
            editor.remove("current_alamat");
            editor.apply();

            Toast.makeText(this, "Logout Berhasil!", Toast.LENGTH_SHORT).show();

            // Kembali ke login
            Intent intent = new Intent(t6.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}