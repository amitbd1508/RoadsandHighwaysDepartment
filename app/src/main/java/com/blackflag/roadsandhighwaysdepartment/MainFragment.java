package com.blackflag.roadsandhighwaysdepartment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private static int on;
    private static int off;

    private  SharedPreferences sharedPreferences;

    public MainFragment() {
        // Required empty public constructor
        on=Color.parseColor("#FF5722");
        off=Color.parseColor("#EF6C00");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        sharedPreferences=getActivity().getSharedPreferences("egp", Context.MODE_PRIVATE);
        view.findViewById(R.id.internatonalhighway).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check(view))
                    send("1000");
                else
                    send("1010");
            }
        });
        view.findViewById(R.id.asianhighway).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(check(view))
                   send("2000");
                else
                   send("2010");

            }
        });
        view.findViewById(R.id.district).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check(view))
                    send("3000");
                else
                    send("3010");
            }
        });
        view.findViewById(R.id.axleload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check(view))
                    send("4000");
                else
                    send("4010");
            }
        });
        view.findViewById(R.id.bordercorss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check(view))
                    send("5000");
                else
                    send("5010");
            }
        });

        view.findViewById(R.id.egp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check(view))
                {
                    setEGP(true);
                }
                else{
                    setEGP(false);
                }
            }
        });
        view.findViewById(R.id.seaport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check(view))
                    send("6000");
                else
                    send("6010");



            }
        });



        return view;
    }

    private void setEGP(boolean b) {

        if(b)
        {

            for (int i=0;i<9;i++)
            {
                String data=sharedPreferences.getString(String.valueOf(i),"11000");
                send(data);
                Log.v(getClass().getSimpleName(),i+"--------"+data);
            }



        }else {

            for (int i=0;i<9;i++)
            {
                String data=String.valueOf(11000+(i*10));
                send(data);
                Log.v(getClass().getSimpleName(),i+"--------"+data);
            }


        }
    }

    private boolean check(View view) {
        ColorDrawable color= (ColorDrawable) view.getBackground();
        if(color.getColor()==on)
        {
            view.setBackgroundColor(off);
            return false;
        }
        else
        {
            view.setBackgroundColor(on);
            return true;
        }
    }

    boolean send(String data)
    {


        return false;
    }

}
