package com.example.substationadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.substationadmin.R;
import com.example.substationadmin.SaveSharedPreferences;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnPengajuan, btnPengaduan, btnGardu, btnKelolaUser;
    TextView tvNamaUser;
    SaveSharedPreferences saveSharedPreferences;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveSharedPreferences = new SaveSharedPreferences();

        mAuth = FirebaseAuth.getInstance();
        if(saveSharedPreferences.getEmail(MainActivity.this).length() == 0){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }else{

            tvNamaUser = findViewById(R.id.tvNamaUser);

            btnGardu = findViewById(R.id.btnGardu);
            btnGardu.setOnClickListener(this);

            btnPengaduan = findViewById(R.id.btnPengaduan);
            btnPengaduan.setOnClickListener(this);

            btnPengajuan = findViewById(R.id.btnPengajuan);
            btnPengajuan.setOnClickListener(this);

            btnKelolaUser = findViewById(R.id.btnKelolaUser);
            btnKelolaUser.setOnClickListener(this);

            tvNamaUser.setText(mAuth.getCurrentUser().getEmail());

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btnGardu) :
                Intent intent2 = new Intent(MainActivity.this, LihatGarduActivity.class);
                startActivity(intent2);
                break;
                
            case (R.id.btnPengaduan) :
                Intent intent = new Intent(MainActivity.this, PengaduanActivity.class);
                startActivity(intent);
                break;
            
            case (R.id.btnPengajuan) :
                Intent intent1 = new Intent(MainActivity.this, PengajuanGarduActivity.class);
                startActivity(intent1);
                break;
            case (R.id.btnKelolaUser) :
                Intent intent3 = new Intent(MainActivity.this, KelolaUserActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
