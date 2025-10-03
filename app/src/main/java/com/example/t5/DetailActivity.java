package com.example.t5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class DetailActivity extends AppCompatActivity {
    private TextView tvNama, tvEmail, tvAlamat;
    private MaterialButton btnOk, btnReset;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        // Initialize views
        tvNama = findViewById(R.id.tvNama);
        tvEmail = findViewById(R.id.tvEmail);
        tvAlamat = findViewById(R.id.tvAlamat);
        btnOk = findViewById(R.id.btnOk);
        btnReset = findViewById(R.id.btnReset);

        // Ambil data dari SharedPreferences (data user yang sedang login)
        String nama = sharedPreferences.getString("current_nama", "-");
        String email = sharedPreferences.getString("current_email", "-");
        String alamat = sharedPreferences.getString("current_alamat", "-");

        // Tampilkan data
        tvNama.setText("Nama: " + nama);
        tvEmail.setText("Email: " + email);
        tvAlamat.setText("Alamat: " + alamat);

        // Button OK - kembali ke Dashboard
        btnOk.setOnClickListener(v -> finish());

        // Button Reset - ke Reset Page
        btnReset.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, ResetActivity.class);
            startActivity(intent);
        });
    }
}