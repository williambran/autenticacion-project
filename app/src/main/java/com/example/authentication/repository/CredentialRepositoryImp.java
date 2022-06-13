package com.example.authentication.repository;

import androidx.lifecycle.LiveData;

import com.example.authentication.database.dao.CredentialDAO;
import com.example.authentication.database.entity.Credential;

import java.util.List;

public class CredentialRepositoryImp implements CredentialRepository{
    CredentialDAO dao;
    private LiveData<Credential> credential;
    private LiveData<List<Credential>> listCredential;


    public CredentialRepositoryImp(CredentialDAO dao) {
        this.dao = dao;

    }

    public LiveData<Credential> findCredential(int idCredential){


        credential = dao.findCredential(idCredential);

        return credential;
    }



    @Override
    public List<Credential> getAllCrdential() {
        return dao.getAll();
    }

    @Override
    public Credential findCredentialById(int idCredential) {
        return dao.findByIdcuenta(idCredential);
    }

    @Override
    public void insertList(List<Credential> credential) {
        dao.insertList(credential);
    }

    @Override
    public void delete(Credential credential) {
        dao.delete(credential);
    }

    public void deleteAll(List<Credential> credentials){
        dao.deleteAll();
    }
}
