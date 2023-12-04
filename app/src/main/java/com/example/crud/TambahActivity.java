package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.crud.database.AppDatabase;
import com.example.crud.database.entitas.Mahasiswa;

public class TambahActivity extends AppCompatActivity {

    EditText etNama, etNPM;
    Button btnSimpan;
    AppDatabase database;
    int id = 0;
    boolean isEdit = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.etNama);
        etNPM = findViewById(R.id.etNPM);
        btnSimpan = findViewById(R.id.btnSimpan);
        database = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        if (id > 0) {
            isEdit = true;
            Mahasiswa mahasiswa = database.mahasiswaDao().get(id);
            etNama.setText(mahasiswa.fullName);
            etNPM.setText(mahasiswa.npm);
        } else {
            isEdit =false;
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.fullName = etNama.getText().toString();
                mahasiswa.npm = etNPM.getText().toString();
                if (isEdit) {
                    database.mahasiswaDao().update(id, mahasiswa.fullName, mahasiswa.npm);
                } else {
                    database.mahasiswaDao().insertALl(mahasiswa);
                }
                finish();
            }
        });
    }
}