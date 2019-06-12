package com.junga.airthai;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class DrawerFragment extends BottomSheetDialogFragment {


    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottomsheet,container,false);
        NavigationView navView = (NavigationView) view.findViewById(R.id.navigationView);
        Bundle bundle = getArguments();
        ArrayList<String> cityList = bundle.getStringArrayList("city");

        Menu menu = navView.getMenu();
        menu.clear();
        for(final String name : cityList){

            MenuItem menuItem1 = menu.add(R.id.group1,Menu.NONE,Menu.NONE,name);
            menuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    String urlName = mainActivity.urlString(name);
                    mainActivity.cityName = name;
                    mainActivity.httpConnection.requestWebServer(urlName,mainActivity.requestCallback);



                    getActivity().getSupportFragmentManager().beginTransaction().remove(DrawerFragment.this).commit();
                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }
            });
        }





        return view;
    }

    public void addItem(String name){
        NavigationView navView = (NavigationView) view.findViewById(R.id.navigationView);

        Menu menu = navView.getMenu();
        menu.add(R.id.group1,Menu.NONE,Menu.NONE,name);
    }



}
