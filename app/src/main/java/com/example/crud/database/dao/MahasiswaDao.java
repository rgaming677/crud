package com.example.crud.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crud.database.entitas.Mahasiswa;

import java.util.List;

@Dao
public interface MahasiswaDao {
    @Query("SELECT * FROM mahasiswa")
    List<Mahasiswa> getAll();

    @Insert
    void insertALl(Mahasiswa... mahasiswas);

    @Query("UPDATE mahasiswa SET full_name=:full_name, npm=:npm WHERE id=:id")
    void update(int id, String full_name, String npm);

    @Query("SELECT * FROM mahasiswa WHERE id=:id")
    Mahasiswa get(int id);

    @Delete
    void delete(Mahasiswa mahasiswa);
}
