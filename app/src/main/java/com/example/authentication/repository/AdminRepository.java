package com.example.authentication.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.authentication.database.dao.CredentialDAO;
import com.example.authentication.database.entity.Credential;
import com.example.authentication.network.NetworkService;

import java.util.List;

public class AdminRepository {

    CredentialDAO dao;
    NetworkService networkService = new NetworkService();
    MutableLiveData<List<Credential>> credentials;
    List<Credential> listCred;


    private LiveData<List<Credential>> listCredential;


    public AdminRepository(CredentialDAO dao) {
        this.dao = dao;

    }


    public LiveData<List<Credential>> getAllCredentials(){
        listCredential = dao.getAllCredentials();

        return  listCredential;
    }

    public MutableLiveData<List<Credential>> getAllCredentialServices(){
        Log.d("TAG",  "prueba1");

        if (credentials == null) {
            credentials = new MutableLiveData<>();
        }
        listCred = networkService.getCredentialService();
        credentials.setValue(listCred);

        return credentials;

    }


    public void insert(Credential credential) {

    }

    public void insertList(List<Credential> credentials) {
        dao.insertList(credentials);
    }

    public void deleteAll(){
        dao.deleteAll();
    }

}
