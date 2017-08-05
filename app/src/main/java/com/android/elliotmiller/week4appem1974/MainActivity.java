package com.android.elliotmiller.week4appem1974;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.elliotmiller.week4appem1974.fragments.Details;
import com.android.elliotmiller.week4appem1974.fragments.Home;

public class MainActivity extends AppCompatActivity implements Home.HomeInterface, Details.DetailsInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_view, Home.newInstance("", ""));
        ft.commit();
    }

    @Override
    public void selectCustomer(long id) {

    }

    @Override
    public void addCustomer() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_view, Details.newInstance(Details.ACTION_ADD, -1));
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
