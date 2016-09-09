package com.fintech.ternaku.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fintech.ternaku.R;
import com.fintech.ternaku.model.Ternak;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YORIS on 9/9/16.
 */
public class TernakListAdapter extends ArrayAdapter<Ternak> {
    private Activity activity;
    private List<Ternak> ternak;
    private static LayoutInflater inflater=null;
    private int layout;
    private List<Ternak> data = new ArrayList<Ternak>();


    public TernakListAdapter(Activity a, int layout, List<Ternak>items) {
        super(a, layout, items);
        this.activity = a;
        this.layout = layout;
        data = items;

    }

    public int getCount() {
        return data.size();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        holder = new ViewHolder();
        view.setTag(holder);

        holder.berat = (TextView)view.findViewById(R.id.txtberat);
        holder.production = (TextView)view.findViewById(R.id.txtProduction);
        holder.statusheat = (TextView)view.findViewById(R.id.txtStatusHeat);
        holder.tglheat = (TextView)view.findViewById(R.id.txtTglHeat);
        holder.produksisusu = (TextView)view.findViewById(R.id.txtProduksiSusu);

        holder.imgSapi = (ImageView) view.findViewById(R.id.list_image);
        holder.imgiconheat = (ImageView) view.findViewById(R.id.imgIconHeat);

        Ternak ternak = new Ternak();
        ternak = data.get(position);
        // Setting all values in listview
        if(holder.produksisusu!=null) {
            Log.d("TES", String.valueOf(ternak.getBerat_badan()));
        }

        holder.berat.setText(String.valueOf(ternak.getBerat_badan()));
        holder.production.setText(ternak.getDiagnosis());
        holder.statusheat.setText(ternak.getStatus_kesuburan());
        holder.tglheat.setText(ternak.getTgl_subur());
        holder.produksisusu.setText(String.valueOf(ternak.getProduksi_susu()));

        return view;
    }

    public class ViewHolder {
        public TextView berat, production, statusheat, tglheat, produksisusu;
        public ImageView imgSapi, imgiconheat;
    }
}
