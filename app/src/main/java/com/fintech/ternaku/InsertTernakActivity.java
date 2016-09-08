package com.fintech.ternaku;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InsertTernakActivity extends AppCompatActivity {
    private TextView txtnama, txtbrt, txttgl;
    float counter=10;
    private int year;
    private int month;
    private int day;
    private DatePicker dpResult;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ternak);
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

        txtnama = (TextView)findViewById(R.id.nama_ternak);
        txtbrt = (TextView)findViewById(R.id.berat_ternak);
        txttgl = (TextView)findViewById(R.id.txtTgl);

        dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);

        txttgl.setText(dateFormatter.format(Calendar.getInstance().getTime()));

        txtnama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showinputnama();
            }
        });

        txtbrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showinputberat();
            }
        });
        setDateTimeField();

        txttgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDatePickerDialog.show();
            }
        });
    }

    protected void showinputnama() {

        // get prompts.xml view

        LayoutInflater layoutInflater = LayoutInflater.from(InsertTernakActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_name_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InsertTernakActivity.this);
        alertDialogBuilder.setView(promptView);
        int a=0;
        final EditText editText = (EditText) promptView.findViewById(R.id.edtnamaternak);

        final InputMethodManager imgr = (InputMethodManager) InsertTernakActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        int z=0;
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        txtnama.setText(editText.getText());
                        InputMethodManager imm = (InputMethodManager) getSystemService(
                                INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    }
                })
                .setNegativeButton("Batal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //imgr.toggleSoftInput(0,InputMethodManager.HIDE_IMPLICIT_ONLY);
                                InputMethodManager imm = (InputMethodManager) getSystemService(
                                        INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    protected void showinputberat() {

        // get prompts.xml view

        LayoutInflater layoutInflater = LayoutInflater.from(InsertTernakActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_berat_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InsertTernakActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText txtBerat = (EditText) promptView.findViewById(R.id.txtBerat);
        final Button btnAdd = (Button) promptView.findViewById(R.id.btnAdd);
        final Button btnMin = (Button) promptView.findViewById(R.id.btnMin);
        txtBerat.setGravity(Gravity.CENTER);
        final DecimalFormat df = new DecimalFormat("#.#");

        txtBerat.setText(String.valueOf(df.format(counter)));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter+=0.10;
                txtBerat.setGravity(Gravity.CENTER);
                txtBerat.setText(String.valueOf(df.format(counter)));
            }
        });

        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter-=0.10;
                txtBerat.setGravity(Gravity.CENTER);

                txtBerat.setText(String.valueOf(df.format(counter)));
            }
        });

        final InputMethodManager imgr = (InputMethodManager) InsertTernakActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        txtbrt.setText(txtBerat.getText());
                        /*InputMethodManager imm = (InputMethodManager) getSystemService(
                                INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(txtBerat.getWindowToken(), 0);*/
                    }
                })
                .setNegativeButton("Batal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //imgr.toggleSoftInput(0,InputMethodManager.HIDE_IMPLICIT_ONLY);
                                /*InputMethodManager imm = (InputMethodManager) getSystemService(
                                        INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);*/
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    protected void showinputtanggal()
    {

    }

    private void setDateTimeField() {
        //toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txttgl.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
