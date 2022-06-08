package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    ScannerFragment scannerFragment = new ScannerFragment();
    AdminFragment adminFragment = new AdminFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        BottomNavigationView navView = findViewById(R.id.bottomNavigationView); // el bootomnaviation ...el de abajo
         navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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




}