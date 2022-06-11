package com.example.authentication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authentication.R;
import com.example.authentication.database.AppDatabase;
import com.example.authentication.database.dao.CredentialDAO;
import com.example.authentication.database.entity.Credential;
import com.example.authentication.viewModel.ScannerViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;


public class ScannerFragment extends Fragment {

    Button btnScanner, btnGetCredential ;
    TextView tvResponse;
    View vista ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG ="";
    ScannerViewModel scannerViewModel;


    public ScannerFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerViewModel =  new ViewModelProvider(this).get(ScannerViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_scanner, container, false);
        btnGetCredential = vista.findViewById(R.id.getCredential);
        btnScanner=vista.findViewById(R.id.btn_scanner);
        tvResponse = vista.findViewById(R.id.tv_response);

        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("LECTOR -QR");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });
        btnGetCredential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Credential> credentials = scannerViewModel.getAllCredentials();
                for (Credential cre:credentials) {
                    Toast.makeText(getActivity(), "Todo salio BIEN" +  cre.getNombre() , Toast.LENGTH_SHORT).show();


                }
            }
        });
        // Inflate the layout for this fragment
        return vista;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
           IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            String resultNumCuenta = result.getContents();
            if (result != null){
               String numCuenta = "";
               for (int i= 0; i <= 8 ; i++){
                   numCuenta +=  resultNumCuenta.charAt(i);
               }
               Toast.makeText(getActivity(), "Todo salio BIEN" + numCuenta , Toast.LENGTH_SHORT).show();

               tvResponse.setText(numCuenta);
                readDataFireStore();
           }
        }
    }


    public void readDataFireStore(){
        db.collection("credencial")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Credential> crednetial =new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Credential credential =  document.toObject(Credential.class);
                                System.out.print("objestos"+credential.getNombre());
                                tvResponse.setText(credential.getNombre());
                                crednetial.add(credential) ;


                            }

                            scannerViewModel.insertCredential(crednetial);
                            System.out.print("lista de objestos"+crednetial);
                            int tamano = crednetial.size();
                            tvResponse.setText( String.valueOf(tamano));
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}