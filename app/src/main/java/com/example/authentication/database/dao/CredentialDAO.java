package com.example.authentication.database.dao;

import androidx.lifecycle.LiveData;
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

    @Query("SELECT * FROM Credential")
    LiveData<List<Credential>> getAllCredentials();


    @Query("SELECT * FROM Credential WHERE idCuenta =:numCredential")
    Credential findByIdcuenta(int numCredential);

    @Query("SELECT * FROM Credential WHERE idCuenta =:numCredential")
    LiveData<Credential> findCredential(int numCredential);

    @Insert
    void insertList(List<Credential> credential);

    @Insert
    void insert(Credential credential);

    @Delete
    void delete(Credential crdential);

    @Query("DELETE from Credential")
    void deleteAll();
}
