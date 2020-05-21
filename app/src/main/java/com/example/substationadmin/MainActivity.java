package com.example.substationadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnPengajuan, btnPengaduan, btnGardu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGardu = findViewById(R.id.btnGardu);
        btnGardu.setOnClickListener(this);

        btnPengaduan = findViewById(R.id.btnPengaduan);
        btnPengaduan.setOnClickListener(this);

        btnPengajuan = findViewById(R.id.btnPengajuan);
        btnPengajuan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btnGardu) :
                Toast.makeText(this, "Gardu", Toast.LENGTH_SHORT).show();
                break;
                
            case (R.id.btnPengaduan) :
                Intent intent = new Intent(MainActivity.this, PengaduanActivity.class);
                startActivity(intent);
                break;
            
            case (R.id.btnPengajuan) :
                Toast.makeText(this, "Pengajuan Gardu", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
