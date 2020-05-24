package com.example.substationadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.substationadmin.R;
import com.example.substationadmin.UserSerializable;

public class DetailUserActivity extends AppCompatActivity {
    EditText etID, etEmail, etJabatan, etWilayah;
    Button btnTombol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        final UserSerializable userSerializable = (UserSerializable) getIntent().getSerializableExtra("data");
        if(userSerializable != null){
            etID.setText(userSerializable.getId());
            etEmail.setText(userSerializable.getEmail());
            etJabatan.setText(userSerializable.getJabatan());
            etWilayah.setText(userSerializable.getWilayah());
        }

        etID = findViewById(R.id.etID);
        etEmail = findViewById(R.id.etEmail);
        etJabatan = findViewById(R.id.etJabatan);
        etWilayah = findViewById(R.id.etWilayah);

        btnTombol = findViewById(R.id.btnTombol);

        etID.setEnabled(false);
        etEmail.setEnabled(false);
        etJabatan.setEnabled(false);
        etWilayah.setEnabled(false);

        btnTombol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnTombol.getText().equals("UPDATE")){
                    etID.setEnabled(true);
                    etEmail.setEnabled(true);
                    etJabatan.setEnabled(true);
                    etWilayah.setEnabled(true);

                    btnTombol.setText("SELESAI");
                }
                else{
                    Toast.makeText(DetailUserActivity.this, "Blum bisa update yaa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
