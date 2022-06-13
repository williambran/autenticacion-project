package com.example.authentication.ui;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.authentication.R;

import java.util.HashMap;
import java.util.Map;

public class AddUserFragment extends DialogFragment {

    EditText etNumcuenta, etName, etActive, etprocessing;
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
                cleanInputs();
                //Enviar al servidor Firestore
                setDataFirebase();

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

    void setDataFirebase(){
        //db.collection()
    }

}