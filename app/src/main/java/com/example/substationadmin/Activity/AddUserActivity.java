package com.example.substationadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.substationadmin.R;

public class AddUserActivity extends AppCompatActivity {
    EditText etID, etEmail;
    Button btnTambah;
    Spinner spJabatan, spWilayah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etID = findViewById(R.id.etID);
        etEmail = findViewById(R.id.etEmail);
        btnTambah = findViewById(R.id.btnTambah);

        spJabatan = findViewById(R.id.spJabatan);

        spWilayah = findViewById(R.id.spWilayah);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddUserActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.jabatan));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJabatan.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(AddUserActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.jenis_wilayah));
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWilayah.setAdapter(arrayAdapter2);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddUserActivity.this, "TESSS", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
