package com.example.heroic_properties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.heroic_properties.Fragments.All_properties;
import com.example.heroic_properties.Fragments.Home;
import com.example.heroic_properties.Fragments.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Host extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host);
        bottomNavigationView=findViewById(R.id.navigationic);

        getSupportFragmentManager().beginTransaction().replace(R.id.ffframelayout1, new Home()).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.Home:
                        replacefrag(new Home());
                        break;
                    case R.id.Prop:
                        replacefrag(new All_properties());
                        break;
                    case R.id.profile:
                        replacefrag(new Profile());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                return true;
            }
        });
    }

    private void replacefrag(@NonNull Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ffframelayout1,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void exitdialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Exit ");
        builder.setMessage("Exit now");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                     //                SharedPreferences sharedPreference = getSharedPreferences("log_info", MODE_PRIVATE);
//                SharedPreferences.Editor ditor = sharedPreference.edit();
//                ditor.remove("logid");
//                ditor.clear();
//                ditor.commit();
//                finishAffinity();
                finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemidd = item.getItemId();
        if (itemidd == R.id.Talk) {
            Toast.makeText(this, "talk to us", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (itemidd == R.id.PProfile) {

        }
        else if (itemidd == R.id.PProfile) {

        }
        else if (itemidd == R.id.PProfile) {

        }
        return true;
    }
}
