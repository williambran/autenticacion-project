package com.example.authentication.repository;


import com.example.authentication.database.entity.Credential;

import java.util.List;

public interface CredentialRepository {

    List<Credential> getAllCrdential();
    Credential findCredentialById(int idCredential);
    void insertList(List<Credential> credential);
    void delete(Credential credential);
}
