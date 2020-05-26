package com.example.substationadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

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
    FirebaseAuth mAuth;

    String [] arrayJabatan;
    String [] arrayWilayah;

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
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddUserActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.jabatan));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJabatan.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(AddUserActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.jenis_wilayah));
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWilayah.setAdapter(arrayAdapter2);

        etID.setEnabled(false);

        arrayJabatan = getResources().getStringArray(R.array.jabatan);
        arrayWilayah = getResources().getStringArray(R.array.jenis_wilayah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.createUserWithEmailAndPassword(etEmail.getText().toString().trim(), "123456")
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    HashMap<String,String> data = new HashMap<>();
                                    data.put("email", etEmail.getText().toString().trim());
                                    data.put("id", etID.getText().toString().trim());
                                    data.put("jabatan", arrayJabatan[jabatanIndex]);
                                    data.put("uid", mAuth.getCurrentUser().getUid());
                                    data.put("password", "123");
                                    data.put("wilayah", String.valueOf(wilayahIndex));

                                    rootRef.child("User").child(mAuth.getCurrentUser().getUid()).setValue(data);
                                    Toast.makeText(AddUserActivity.this, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                    SendUserToLoginActivity();
                                }
                                else{
                                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                    Toast.makeText(AddUserActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

        spJabatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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

    private void SendUserToLoginActivity() {
        Intent mainIntent = new Intent(AddUserActivity.this, LoginActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
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
