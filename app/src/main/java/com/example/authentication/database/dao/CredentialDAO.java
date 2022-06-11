package com.example.authentication.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.authentication.database.entity.Credential;

import java.util.List;

@Dao
public interface CredentialDAO {

    @Query("select * from Credential")
    List<Credential> getAll();


    @Query("SELECT * FROM Credential WHERE idCuenta =:numCredential")
    Credential findByIdcuenta(int numCredential);

    @Insert
    void insert(List<Credential> credential);

    @Delete
    void deleteAll(Credential crdential);
}
