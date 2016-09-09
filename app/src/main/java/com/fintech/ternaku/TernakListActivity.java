package com.fintech.ternaku;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.fintech.ternaku.adapters.TernakListAdapter;
import com.fintech.ternaku.model.Ternak;

import java.util.ArrayList;
import java.util.List;

public class TernakListActivity extends AppCompatActivity {
    TernakListAdapter objAdapter;
    List<Ternak> ternakList = new ArrayList<Ternak>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ternak_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Ternak t = new Ternak("TRK1","Pansu",30,100,"Subur","Sehat","20/09/2016");
        ternakList.add(t);
        ListView list = (ListView) findViewById(R.id.list);
        objAdapter = new TernakListAdapter(TernakListActivity.this, R.layout.list_row, ternakList);
        list.setAdapter(objAdapter);
    }

}
