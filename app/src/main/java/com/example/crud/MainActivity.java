package com.example.crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.crud.adapter.MahasiswaAdapter;
import com.example.crud.database.AppDatabase;
import com.example.crud.database.entitas.Mahasiswa;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvMahasiswa;
    Button btnTambah;
    AppDatabase database;
    MahasiswaAdapter mahasiswaAdapter;
    List<Mahasiswa> list;
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMahasiswa = findViewById(R.id.rvMahasiswa);
        btnTambah = findViewById(R.id.btnTambah);
        database = AppDatabase.getInstance(getApplicationContext());
        mahasiswaAdapter = new MahasiswaAdapter(database.mahasiswaDao().getAll(), getApplicationContext());

        rvMahasiswa.setHasFixedSize(true);
        rvMahasiswa.setLayoutManager(new LinearLayoutManager(this));
        rvMahasiswa.setAdapter(mahasiswaAdapter);
        list = new ArrayList<>();
        list.clear();
        list.addAll(database.mahasiswaDao().getAll());
        mahasiswaAdapter = new MahasiswaAdapter(list, getApplicationContext());
        mahasiswaAdapter.setDialog(new MahasiswaAdapter.Dialog() {
            @Override
            public void onClick(int position) {
                final CharSequence[] dialogItem = {"edit", "Hapus"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                //Edit data Mahasiswa
                                Intent intent = new Intent(MainActivity.this, TambahActivity.class);
                                intent.putExtra("id", list.get(position).id);
                                startActivity(intent);
                                break;
                            case 1:
                                //Hapus data mahasiswa
                                Mahasiswa mahasiswa = list.get(position);
                                database.mahasiswaDao().delete(mahasiswa);
                                onStart();
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TambahActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        list.addAll(database.mahasiswaDao().getAll());
        mahasiswaAdapter.notifyDataSetChanged();
    }
}