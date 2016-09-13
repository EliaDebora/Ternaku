package com.fintech.ternaku;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TwoLineListItem;

import com.fintech.ternaku.adapters.TernakListAdapter;
import com.fintech.ternaku.model.Peternakan;
import com.fintech.ternaku.model.Ternak;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowPeternakanActivity extends AppCompatActivity {
    List<Peternakan> peternakanList = new ArrayList<Peternakan>();
    ArrayAdapter adapter;
    ListView l ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_peternakan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pilih Peternakan");
        l = (ListView) findViewById(R.id.list_peternakan);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String urlParameters = "uid="+getSharedPreferences(getString(R.string.userpref), Context.MODE_PRIVATE).getString("keyIdPengguna",null);
        new GetPeternakan().execute("http://ternaku.com/index.php/C_Ternak/getPeternakanByUserID",urlParameters);

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

    private class GetPeternakan extends AsyncTask<String, Integer, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ShowPeternakanActivity.this);
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
            ShowPeternakan(result);
            pDialog.dismiss();
        }
    }

    private void ShowPeternakan(String result)
    {

        try{
            JSONArray jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++)
            {
                JSONObject jObj = jArray.getJSONObject(i);
                Peternakan pet = new Peternakan();
                pet.setId_peternakan(jObj.getString("ID_PETERNAKAN"));
                pet.setId_pengguna(jObj.getString("ID_PENGGUNA"));
                pet.setNama_peternakan(jObj.getString("NAMA"));
                pet.setAlamat(jObj.getString("ALAMAT"));
                pet.setTelpon(jObj.getString("TELPON"));
                pet.setLatitude(jObj.getString("LAT"));
                pet.setLongitude(jObj.getString("LONG"));
                pet.setJenis_ternak(jObj.getString("JENIS_TERNAK"));
                Log.d("RESP",jObj.getString("ID_PETERNAKAN"));

                peternakanList.add(pet);
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, peternakanList) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TwoLineListItem row;
                    if (convertView == null) {
                        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        row = (TwoLineListItem) inflater.inflate(android.R.layout.simple_list_item_2, null);
                    } else {
                        row = (TwoLineListItem) convertView;
                    }


                    row.getText1().setText(peternakanList.get(position).getNama_peternakan());
                    row.getText2().setText(peternakanList.get(position).getAlamat());
                    row.getText1().setTextColor(Color.parseColor("#000000"));
                    row.getText2().setTextColor(Color.parseColor("#455A64"));


                    return row;
                }
            };
            l.setAdapter(adapter);

            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String id_peternakan = peternakanList.get(i).getId_peternakan();
                    Log.d("idper",id_peternakan);
                    Bundle b = new Bundle();
                    b.putString("id_peternakan",id_peternakan);
                    Intent intent = new Intent(ShowPeternakanActivity.this,TernakListActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }
        catch (JSONException e){e.printStackTrace();}
    }
}
