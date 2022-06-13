package com.example.authentication.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

public class AddUserFragment extends DialogFragment {

    EditText etNumcuenta, etName, etActive, etprocessing;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AdminViewModel viewModel;


    Button btnActived;

    // Contructor
    public AddUserFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_user,container, false);

        etNumcuenta = view.findViewById(R.id.et_numCuenta);
        etName = view.findViewById(R.id.et_name);
        etActive = view.findViewById(R.id.et_activo);
        etprocessing = view.findViewById(R.id.et_activo);
        btnActived = view.findViewById(R.id.btn_activar);


        btnActived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Limpiar antes de encviar al servidor

                //Enviar al servidor Firestore
                setDataFirebase(cleanInputs());

            }
        });


        return view;
    }

    Map<String, Object> cleanInputs(){
        Map<String, Object> alumno = new HashMap<>();

        int numCuenta = Integer.parseInt(etNumcuenta.getText().toString());
        String name = etName.getText().toString();
        int active = Integer.parseInt(etActive.getText().toString());
        int process = Integer.parseInt(etprocessing.getText().toString());

        alumno.put("idCuenta", numCuenta);
        alumno.put("nombre", name);
        alumno.put("active", active);
        alumno.put("processing", process);

        return alumno;
    }

    void setDataFirebase(Map<String, Object>  object){
        db.collection("credencial")
                .add(object)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                      Log.d("NUEVOS", "Se agregaron nuevos usuarios correctamente sa servidor");
                         viewModel =  new ViewModelProvider(getActivity()).get(AdminViewModel.class);

                        getDialog().dismiss();
                        readDataFireStore();

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
                                crednetial.add(credential) ;


                            }

                            viewModel.deleteAll();
                            viewModel.insertCredentials(crednetial);
                            //System.out.print("lista de objestos"+crednetial);
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}