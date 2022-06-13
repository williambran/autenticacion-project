package com.example.authentication.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.authentication.database.AppDatabase;
import com.example.authentication.database.dao.CredentialDAO;
import com.example.authentication.database.entity.Credential;
import com.example.authentication.repository.AdminRepository;

import java.util.List;

public class AdminViewModel extends ViewModel {


    public AdminRepository adminRepository;
    public LiveData<List<Credential>> credentialService ;
    AppDatabase db = AppDatabase.INSTANCE;
    CredentialDAO dao = db.credentialDAO();



    public AdminViewModel() {
        adminRepository = new AdminRepository(dao);
    }



    public void insertCredential(Credential credential) {
        adminRepository.insert(credential);
    }

    public void insertCredentials(List<Credential> credentials){
        adminRepository.insertList(credentials);
    }

    public LiveData<List<Credential>> getCredentialLocal(){

        return adminRepository.getAllCredentials();
    }


    public void delete(){
    }

    public void deleteAll(){
        adminRepository.deleteAll();
    }



    LiveData<List<Credential>> getAllCredentialsDB(){
       return adminRepository.getAllCredentials();
    }


    public LiveData<List<Credential>> getAllCredentialServices() {


        credentialService = adminRepository.getAllCredentialServices();
        Log.d("TAG",  credentialService.toString());
        return credentialService;
    }





}
