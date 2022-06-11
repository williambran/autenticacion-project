package com.example.authentication.repository;

import com.example.authentication.database.dao.CredentialDAO;
import com.example.authentication.database.entity.Credential;

import java.util.List;

public class CredentialRepositoryImp implements CredentialRepository{
    CredentialDAO dao;

    public CredentialRepositoryImp(CredentialDAO dao) {
        this.dao = dao;
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
    public void insert(List<Credential> credential) {
        dao.insert(credential);
    }

    @Override
    public void delete(Credential credential) {
        dao.deleteAll(credential);
    }
}
