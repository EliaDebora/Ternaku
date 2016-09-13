package com.fintech.ternaku;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fintech.ternaku.adapters.TernakListAdapter;
import com.fintech.ternaku.model.Ternak;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TernakListActivity extends AppCompatActivity {
    TernakListAdapter objAdapter;
    List<Ternak> ternakList = new ArrayList<Ternak>();
    ListView list;
    EditText edtsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ternak_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = (ListView) findViewById(R.id.list);
        edtsearch = (EditText)findViewById(R.id.edtSearch);
        String id_peternakan = getIntent().getExtras().getString("id_peternakan");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        edtsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    search(s.toString());
                }
            }
        });
        String urlParameters = "uid="+getSharedPreferences(getString(R.string.userpref), Context.MODE_PRIVATE).getString("keyIdPengguna",null)+"&idpeternakan="+id_peternakan;
        new GetTernakTask().execute("http://ternaku.com/index.php/C_Ternak/getDetailTernakByUserId",urlParameters);

        /*
        Ternak t = new Ternak("TRK1","Pansu",30,100,"Subur","Sehat","20/09/2016");
        Ternak t2 = new Ternak("TRK2","Adit",30,100,"Subur","Sehat","20/09/2016");
        ternakList.add(t);
        */

        //ternakList.add(t2);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class GetTernakTask extends AsyncTask<String, Integer, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(TernakListActivity.this);
            pDialog.setMessage("Harap tunggu...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            Connection c = new Connection();
            String json = c.GetJSONfromURL(urls[0], urls[1]);
            return json;
        }

        protected void onPostExecute(String result) {
            ShowTernak(result);
            pDialog.dismiss();
        }
    }

    private void ShowTernak(String result)
    {
        Log.d("Ternak2",result);

        try{
            JSONArray jArray = new JSONArray(result);
            Log.d("COUNT",String.valueOf(jArray.length()));
            for(int i=0;i<jArray.length();i++)
            {
                    JSONObject jObj = jArray.getJSONObject(i);
                    Ternak ter = new Ternak();
                    ter.setId_ternak(jObj.getString("id_ternak"));
                    ter.setNama_ternak(jObj.getString("nama_ternak"));
                    ter.setBerat_badan(jObj.getInt("berat_badan"));
                    ter.setTgl_lahir(jObj.getString("tgl_lahir"));
                    ter.setProduksi_susu(jObj.getInt("hari_produksi"));
                    ter.setMax_susu(jObj.getInt("max_susu"));
                    ter.setMin_susu(jObj.getInt("min_susu"));
                    ter.setAvg_susu(jObj.getLong("avg_susu"));
                    ter.setTotal_susu(jObj.getLong("total_susu"));
                    ter.setAktivitas(jObj.getString("aktivitas"));
                    ter.setTgl_subur(jObj.getString("tgl_perkiraan_inseminasi"));
                    ter.setStatus_kesuburan(jObj.getString("status_kesuburan"));
                    ter.setDiagnosis(jObj.getString("diagnosis"));
                    ter.setJml_susu(jObj.getLong("kapasitas"));
                    ter.setJenis(jObj.getString("jenis"));
                    ter.setBreed(jObj.getString("breed"));

                ternakList.add(ter);
            }
            objAdapter = new TernakListAdapter(TernakListActivity.this, R.layout.list_row, ternakList);
            list.setAdapter(objAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String id_ternak = ternakList.get(i).getId_ternak();
                    Bundle b = new Bundle();
                    b.putString("id_ternak",id_ternak);
                    Intent intent = new Intent(TernakListActivity.this,DetailTernakActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }
        catch (JSONException e){e.printStackTrace();}
    }

    public void search(String keyword)
    {
        Boolean diff = true;
        List<Ternak> tempTernak  = new ArrayList<Ternak>();;
        for(int i=0;i<ternakList.size();i++)
        {
            if(ternakList.get(i).getId_ternak().contains(keyword)||ternakList.get(i).getNama_ternak().contains(keyword)||ternakList.get(i).getJenis().contains(keyword)||ternakList.get(i).getBreed().contains(keyword))
            {
                tempTernak.add(ternakList.get(i));
            }
        }

            objAdapter = new TernakListAdapter(TernakListActivity.this, R.layout.list_row, tempTernak);
            list.setAdapter(objAdapter);
            //objAdapter.notifyDataSetChanged();

    }
}
