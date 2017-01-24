package com.blackflag.roadsandhighwaysdepartment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EGPTender extends Fragment {


    List<CheckBox> checkBoxes;
    SharedPreferences sharedpreferences;
    CheckBox dhaka;
    public EGPTender() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_egptender, container, false);
        sharedpreferences = getActivity().getSharedPreferences("egp", Context.MODE_PRIVATE);

        checkBoxes=new ArrayList<CheckBox>();
        checkBoxes.add((CheckBox) view.findViewById(R.id.egp_dhaka));
        checkBoxes.add((CheckBox) view.findViewById(R.id.egp_chittagong));
        checkBoxes.add((CheckBox) view.findViewById(R.id.egp_barisal));
        checkBoxes.add((CheckBox) view.findViewById(R.id.egp_khulna));
        checkBoxes.add((CheckBox) view.findViewById(R.id.egp_rajshahi));
        checkBoxes.add((CheckBox) view.findViewById(R.id.egp_shylet));
        checkBoxes.add((CheckBox) view.findViewById(R.id.egp_gopalgonj));
        checkBoxes.add((CheckBox) view.findViewById(R.id.egp_mymensingh));
        checkBoxes.add((CheckBox) view.findViewById(R.id.egp_rangpur));

        int i=0;
        int con=10000;
        for (CheckBox checkBox:checkBoxes)
        {
            String temp=sharedpreferences.getString(String.valueOf(i),"error");
            if(temp.equals(String.valueOf(con)))
            {
                checkBox.setChecked(true);

            }
            else checkBox.setChecked(false);
            Log.v(getClass().getSimpleName(),i+" +++++++++++ "+temp);
            i++;
            con+=10;
        }


        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                SharedPreferences.Editor editor = sharedpreferences.edit();



                int i=0;
                int onv=10000;
                int offv=11000;
                for (CheckBox check:checkBoxes)
                {
                    if(check.isChecked())
                    {
                        editor.putString(String.valueOf(i),String.valueOf(onv));
                        Log.v(getClass().getSimpleName(),i+" ===== "+onv);
                    }
                    else {

                        editor.putString(String.valueOf(i),String.valueOf(offv));
                        Log.v(getClass().getSimpleName(),i+" ===== "+offv);
                    }

                    onv+=10;
                    offv+=10;
                    i++;
                }
                editor.commit();
                Toast.makeText(getActivity().getApplicationContext(), "Saved Sucessfully", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
