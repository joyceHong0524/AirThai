package com.junga.airthai;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DrawerFragment extends BottomSheetDialogFragment {


    View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        view = View.inflate(context,R.layout.fragment_bottomsheet,null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottomsheet,container,false);
        NavigationView navView = (NavigationView) view.findViewById(R.id.navigationView);
        Bundle bundle = getArguments();
        ArrayList<String> cityList = bundle.getStringArrayList("city");

        Menu menu = navView.getMenu();
        menu.clear();
        for(String name : cityList){
            menu.add(R.id.group1,Menu.NONE,Menu.NONE,name);
        }



        return view;
    }

    public void addItem(String name){
        NavigationView navView = (NavigationView) view.findViewById(R.id.navigationView);

        Menu menu = navView.getMenu();
        menu.add(R.id.group1,Menu.NONE,Menu.NONE,name);
    }



}
