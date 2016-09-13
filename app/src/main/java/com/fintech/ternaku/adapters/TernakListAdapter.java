package com.fintech.ternaku.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fintech.ternaku.GaugeView;
import com.fintech.ternaku.InsertTernakActivity;
import com.fintech.ternaku.R;
import com.fintech.ternaku.TernakListActivity;
import com.fintech.ternaku.model.Ternak;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private GaugeView gaugeView;
    private float degree = -225;
    private float sweepAngleControl = 0;
    private float sweepAngleFirstChart = 1;
    private float sweepAngleSecondChart = 1;
    private float sweepAngleThirdChart = 1;
    private boolean isInProgress = false;
    private boolean resetMode = false;
    private boolean canReset = false;
    Activity act;

    public TernakListAdapter(Activity a, int layout, List<Ternak>items) {
        super(a, layout, items);
        this.activity = a;
        this.layout = layout;
        data = items;
        act = a;
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

        gaugeView = (GaugeView) view.findViewById(R.id.gaugeView);
        gaugeView.setRotateDegree(degree);


        holder.id = (TextView)view.findViewById(R.id.txtID);
        holder.nama = (TextView)view.findViewById(R.id.txtnama);
        holder.berat = (TextView)view.findViewById(R.id.txtberat);
        holder.umur = (TextView)view.findViewById(R.id.txtUmur);

        holder.production = (TextView)view.findViewById(R.id.txtProduction);
        holder.statusheat = (TextView)view.findViewById(R.id.txtStatusHeat);
        holder.tglheat = (TextView)view.findViewById(R.id.txtTglHeat);
        holder.produksisusu = (TextView)view.findViewById(R.id.txtProduksiSusu);

        holder.imgSapi = (ImageView) view.findViewById(R.id.list_image);
        holder.imgiconheat = (ImageView) view.findViewById(R.id.imgIconHeat);

        Ternak ternak = new Ternak();
        ternak = data.get(position);
        // Setting all values in listview


        holder.id.setText(ternak.getId_ternak());
        holder.nama.setText(ternak.getNama_ternak());

        //float total = ternak.getTotal_susu()/ternak.getProduksi_susu();
        float total = ternak.getJml_susu();
        startRunning(total);
        Log.d("rerata",String.valueOf(total));
        holder.berat.setText(String.valueOf(ternak.getBerat_badan())+" Kg");
        holder.umur.setText(getAge(ternak.getTgl_lahir()));
        holder.production.setText(ternak.getDiagnosis());
        holder.statusheat.setText(ternak.getStatus_kesuburan());
        holder.tglheat.setText(ternak.getTgl_subur());
        holder.produksisusu.setText(String.valueOf(ternak.getProduksi_susu()));

        return view;
    }

    public class ViewHolder {
        public TextView id, nama, berat, umur, production, statusheat, tglheat, produksisusu;
        public ImageView imgSapi, imgiconheat;
    }

    public static String getAge(String dateOfBirth) {

        final DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        final LocalDate dt = dtf.parseLocalDate(dateOfBirth);

        LocalDate now = new LocalDate();                    //Today's date
        Period period = new Period(dt, now, PeriodType.yearMonthDay());

        String umur="0";
        if(period.getYears()<=0)
        {
            umur = String.valueOf(period.getMonths() + " bulan " + period.getDays() + " hari");
        }
        else
        {
            umur = String.valueOf(period.getYears() + " tahun " + period.getMonths()+ " bulan " + period.getDays() + " hari");
        }

        return umur;
    }


    private void startRunning(final float total) {
        new Thread() {
            public void run() {
                for (int i = 0; i < 300; i++) {
                    try {
                        act.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                degree++;
                                sweepAngleControl++;

                                if (total <=6) {
                                    if(degree <= -202) {
                                        gaugeView.setRotateDegree(degree);
                                    }
                                }
                                else if (total <= 12) {
                                    if(degree <= -89) {
                                        gaugeView.setRotateDegree(degree);
                                    }
                                }
                                else if (total <= 18 ) {
                                    if(degree <= 0) {
                                        gaugeView.setRotateDegree(degree);
                                    }
                                }

                                if (sweepAngleControl <= 90) {
                                    sweepAngleFirstChart++;
                                    gaugeView.setSweepAngleFirstChart(sweepAngleFirstChart);

                                } else if (sweepAngleControl <= 180) {
                                    sweepAngleSecondChart++;
                                    gaugeView.setSweepAngleSecondChart(sweepAngleSecondChart);

                                } else if (sweepAngleControl <= 270) {
                                    sweepAngleThirdChart++;
                                    gaugeView.setSweepAngleThirdChart(sweepAngleThirdChart);
                                }

                            }
                        });
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (i == 299) {
                        isInProgress = false;
                        canReset = true;
                    }

                }
            }
        }.start();
    }

}
