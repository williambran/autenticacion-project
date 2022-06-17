package com.example.authentication.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.authentication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home extends AppCompatActivity {

    ScannerFragment scannerFragment = new ScannerFragment();
    AdminFragment adminFragment = new AdminFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    FloatingActionButton fabutton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fabutton = findViewById(R.id.fb_add);
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView); // el bootomnaviation ...el de abajo
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(scannerFragment);


        fabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Levanta un nuevo fragmento
           //     NuevoTweetDialogFragment dialog = new NuevoTweetDialogFragment();
           //     dialog.show(getSupportFragmentManager(), "NuevoTwieetDialogFragment");

                AddUserFragment dialog = new AddUserFragment();
                dialog.show(getSupportFragmentManager(), "nnuevoUsuario");

            }
        });
    }

    private  final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_home:
                    loadFragment(scannerFragment);
                    return true;
                case R.id.navigation_admin :
                    loadFragment(adminFragment);
                    return true;
                case R.id.navigation_profile :
                    loadFragment(profileFragment);
                    return true;
            }
            return false;
        }
    };


   public void loadFragment(Fragment fragment) {
       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
       transaction.replace(R.id.fragmentSelect,fragment);
       transaction.commit();
   }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}