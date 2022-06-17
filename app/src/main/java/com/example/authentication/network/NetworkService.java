package com.example.authentication.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.authentication.database.entity.Credential;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NetworkService {

    FirebaseFirestore db = FirebaseFirestore.getInstance(); ;

    public NetworkService(){
       // db = firebaseFirestore;
    }

    public List<Credential> getCredentialService(){

        List<Credential> crednetial =new ArrayList<>();

        db.collection("credencial")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                Credential credential =  document.toObject(Credential.class);
                                System.out.print("objestos"+credential.getNombre());
                               // tvResponse.setText(credential.getNombre());
                                crednetial.add(credential) ;


                            }
                           /* scannerViewModel.insertCredential(crednetial);
                            System.out.print("lista de objestos"+crednetial);
                            int tamano = crednetial.size();
                            tvResponse.setText( String.valueOf(tamano));*/
                        } else {
                   //         Log.w(TAG, "Error getting documents.", task.getException());
                        }


                    }
                });
        return crednetial;
    }
}
