package com.example.substationadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.substationadmin.Model.User;
import com.example.substationadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddUserActivity extends AppCompatActivity {
    EditText etID, etEmail;
    Button btnTambah;
    Spinner spJabatan, spWilayah;
    String ID = "";
    String jabatan = "";
    String wilayah = "";
    int wilayahIndex = 0;
    int jabatanIndex = 0;

    DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etID = findViewById(R.id.etID);
        etEmail = findViewById(R.id.etEmail);
        btnTambah = findViewById(R.id.btnTambah);

        spJabatan = findViewById(R.id.spJabatan);

        spWilayah = findViewById(R.id.spWilayah);

        rootRef = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddUserActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.jabatan));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJabatan.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(AddUserActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.jenis_wilayah));
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWilayah.setAdapter(arrayAdapter2);

        etID.setEnabled(false);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddUserActivity.this, "TESSS", Toast.LENGTH_SHORT).show();
            }
        });

        spJabatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String [] arrayJabatan = getResources().getStringArray(R.array.jabatan);
                jabatan = arrayJabatan[i];
                jabatanIndex = i;
                rootRef.child("User").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int counter = 1;
                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            User user = child.getValue(User.class);
                            if(user.getJabatan().equals(jabatan) && user.getWilayah().equals(String.valueOf(wilayahIndex))){
                                counter++;
                            }
                        }
                        String convertCounter = ConvertCounter(counter);
                        etID.setText(jabatan.substring(0,1) + wilayah + convertCounter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spWilayah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String [] arrayWilayah = getResources().getStringArray(R.array.jenis_wilayah);
                wilayah = arrayWilayah[i];
                wilayahIndex = i;
                rootRef.child("User").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int counter = 1;
                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            User user = child.getValue(User.class);
                            if(user.getJabatan().equals(jabatan) && user.getWilayah().equals(String.valueOf(wilayahIndex))){
                                counter++;
                            }
//                            Toast.makeText(AddUserActivity.this, user.getJabatan() + user.getJabatan().length() + " " + jabatan + jabatan.length(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(AddUserActivity.this, user.getWilayah() + user.getWilayah().length() + " " + wilayahIndex + wilayah.length(), Toast.LENGTH_SHORT).show();
                        }
                        String convertCounter = ConvertCounter(counter);
                        wilayah = wilayah.substring(wilayah.length()-4, wilayah.length()-1);
                        etID.setText(jabatan.substring(0,1) + wilayah + convertCounter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private String ConvertCounter(int counter) {
        String hasil = "";
        if(counter < 9){
            hasil = "0000" + counter;
        }
        else if(counter < 99){
            hasil = "000" + counter;
        }
        else if(counter < 999){
            hasil = "00" + counter;
        }
        else if(counter < 9999){
            hasil = "0" + counter;
        }
        else{
            hasil = String.valueOf(counter);
        }
        return hasil;
    }
}
