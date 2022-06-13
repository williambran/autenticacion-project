package com.example.authentication.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.authentication.database.AppDatabase;
import com.example.authentication.database.dao.CredentialDAO;
import com.example.authentication.database.entity.Credential;
import com.example.authentication.repository.CredentialRepositoryImp;

import java.util.List;


public class ScannerViewModel extends ViewModel {

    public LiveData<Credential> credential ;

    public CredentialRepositoryImp credentialRepository;
    LiveData<List<Credential>> credentials ;
    AppDatabase db = AppDatabase.INSTANCE;
    CredentialDAO dao = db.credentialDAO();

    public ScannerViewModel(){
          credentialRepository = new CredentialRepositoryImp(dao);
    }

    public void insertCredential(List<Credential> credetials){
        credentialRepository.insertList(credetials);
    }

    public LiveData<Credential> findCredentialById(int credentialId){


        return credentialRepository.findCredential(credentialId);
    }

    public List<Credential> getAllCredentials() {
        return  credentialRepository.getAllCrdential();
    }

}
