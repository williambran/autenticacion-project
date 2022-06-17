package com.example.authentication.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authentication.R;
import com.example.authentication.database.entity.Credential;
import com.example.authentication.viewModel.AdminViewModel;
import com.example.authentication.viewModel.ScannerViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminFragment extends Fragment {

    AdminViewModel adminViewModel;
    View vista;
    TextView tvPrueba;
    Button btnGetDatos;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Credential> credentialList;
    private CredentialReciclerViewAdapter adapter;
    private RecyclerView recyclerView;





    public AdminFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminViewModel =  new ViewModelProvider(this).get(AdminViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
       // tvPrueba = vista.findViewById(R.id.tv_prueba);
       // btnGetDatos =vista.findViewById(R.id.btn_getCredentials);
        // Inflate the layout for this fragment

        if (view instanceof RecyclerView) {
            Context context =  view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            adapter = new CredentialReciclerViewAdapter(context, credentialList);
            recyclerView.setAdapter(adapter);
            loadDataLocal();
        }



        return view;
    }



    void loadData(){
        Toast.makeText(getActivity(), "Todo salio BIEN"  , Toast.LENGTH_SHORT).show();

        adminViewModel.getAllCredentialServices().observe(this, new Observer<List<Credential>>() {
            @Override
            public void onChanged(List<Credential> credentials) {
                credentialList = credentials;
                adapter.setData(credentialList);
            }

        });
       /* adapter = new CredentialReciclerViewAdapter(credentialList); // tweetList es la respuesta del servidor
        recyclerView.setAdapter(adapter);*/
    }


    public void  loadDataLocal(){
        adminViewModel.getCredentialLocal().observe(getViewLifecycleOwner(), new Observer<List<Credential>>() {
            @Override
            public void onChanged(List<Credential> credentials) {
                credentialList = credentials;
                for (Credential cre: credentialList) {
                    Log.d("TAG", String.valueOf(cre.getIdCuenta()));
                    Log.d("TAG", cre.getNombre() );
                }
                adapter.setData(credentialList);
            }
        });
    }


    void insertRegistro(){
        Map<String,Object> credential = new HashMap<>();
        credential.put("idCuenta",123456789);
        credential.put("activo", 1);
        credential.put("nombre", "Victor Escobar");
        credential.put("processing", 0);

        db.collection("credencial").add(credential).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.w("TAG", "Error getting documents." + documentReference.getId());

            }
        });
    }

    public void readDataFireStore(){
        db.collection("credencial")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Credential> crednetial =new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                Credential credential =  document.toObject(Credential.class);
                                System.out.print("objestos"+credential.getNombre());
                                tvPrueba.setText(credential.getNombre());
                                crednetial.add(credential) ;


                            }

                            adminViewModel.deleteAll();
                            adminViewModel.insertCredentials(crednetial);
                            //System.out.print("lista de objestos"+crednetial);
                            int tamano = crednetial.size();
                            tvPrueba.setText( String.valueOf(tamano));
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}