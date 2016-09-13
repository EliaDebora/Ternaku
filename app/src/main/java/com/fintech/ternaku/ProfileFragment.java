package com.fintech.ternaku;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.expandable.view.ExpandableView;
import com.fintech.ternaku.adapters.SectionedExpandableLayoutHelper;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    boolean isFertilityClick = false;
    ExpandableRelativeLayout fertilityLayout, productionlayout, healthlayout, generallayout;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button btnFertility=(Button)view.findViewById(R.id.btnFertility);
        fertilityLayout = (ExpandableRelativeLayout) view.findViewById(R.id.fertilityLayout);

        btnFertility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fertilityLayout.toggle();
            }
        });

        Button btnProduction=(Button)view.findViewById(R.id.btnProduction);
        productionlayout = (ExpandableRelativeLayout) view.findViewById(R.id.productionLayout);

        btnProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productionlayout.toggle();
            }
        });

        Button btnHealth=(Button)view.findViewById(R.id.btnHealth);
        healthlayout = (ExpandableRelativeLayout) view.findViewById(R.id.healthLayout);

        btnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                healthlayout.toggle();
            }
        });

        Button btnGeneral=(Button)view.findViewById(R.id.btnGeneral);
        generallayout = (ExpandableRelativeLayout) view.findViewById(R.id.generalLayout);

        btnGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generallayout.toggle();
            }
        });

        return view;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */



}
