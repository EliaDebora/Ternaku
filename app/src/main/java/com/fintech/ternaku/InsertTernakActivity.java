package com.fintech.ternaku;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InsertTernakActivity extends AppCompatActivity {
    private TextView txtNama, txtBrt, txtTgl;
    private Button btnTambah;
    float counter=200;
    private int flag_error_nama=0,flag_error_berat=0;
    private int year;
    private int month;
    private int day;
    private DatePicker dpResult;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private RadioGroup radioKelaminGroup;
    private RadioButton radioKelamin;
    private String generateId="";
    private String pId="PETERNAKAN-1";
    private String uId="USER-1";
    private String QrCodeKey="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ternak);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNama = (TextView)findViewById(R.id.nama_ternak);
        txtBrt = (TextView)findViewById(R.id.berat_ternak);
        txtTgl = (TextView)findViewById(R.id.txtTgl);

        dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);

        txtTgl.setText(dateFormatter.format(Calendar.getInstance().getTime()));

        txtNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showinputnama();
            }
        });

        txtBrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showinputberat();
            }
        });
        setDateTimeField();

        txtTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDatePickerDialog.show();
            }
        });

        radioKelaminGroup = (RadioGroup) findViewById(R.id.radioSex);
        btnTambah = (Button)findViewById(R.id.btnInsertTernak);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check input box---
                if(txtNama.getText().toString().equalsIgnoreCase("Isikan nama ternak disini")||
                        txtNama.getText().toString().equalsIgnoreCase("")) {
                    txtNama.setError("Nama Ternak Belum diisi!!");
                    flag_error_nama=1;
                }else{
                    flag_error_nama=0;
                    txtNama.setError(null);
                }
                if(txtBrt.getText().toString().equalsIgnoreCase("Isikan berat ternak disini")||
                        txtBrt.getText().toString().equalsIgnoreCase("")) {
                    txtBrt.setError("Berat Ternak Belum diisi!!");
                    flag_error_berat=1;
                }else{
                    flag_error_berat=0;
                    txtBrt.setError(null);
                }

                if(flag_error_nama==0&&flag_error_berat==0) {
                    //Generate Id Ternak--
                    String urlParameters_count = "";
                    new generateTernakTask().execute("http://ternaku.com/index.php/C_Ternak/countTernak", urlParameters_count);

                }
            }
        });
    }

    private void insertDB(){
        Log.d("TAG_IdTernak",generateId.toString());

        int selectedId=radioKelaminGroup.getCheckedRadioButtonId();
        radioKelamin=(RadioButton) findViewById(selectedId);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date testDate = null;
        try {
            testDate = sdf.parse(txtTgl.getText().toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String newFormat = formatter.format(testDate);
        //Insert Database--
        Log.d("TAG_INSERT",generateId.trim()+txtNama.getText().toString()+txtBrt.getText().toString()+
                newFormat+radioKelamin.getText().toString());
        String urlParameters = "idternak=" + generateId.trim()
                +"&idpeternakan=" + pId.trim()
                +"&namaternak=" + txtNama.getText().toString()
                +"&jeniskelamin=" + radioKelamin.getText().toString()
                +"&tanggallahirternak=" + newFormat;
        new insertTernakTask().execute("http://ternaku.com/index.php/C_Ternak/insertTernak", urlParameters);

    }

    private void GenerateQrCodeId() {
        //Qr Code Id Generator--
        String urlParameters_qrcode = "idternak="+generateId.trim()+"&idpeternakan=" + pId.trim();
        QrCodeKey ="http://ternaku.com/index.php/C_Ternak/getDetailTernakQrCde"+urlParameters_qrcode;
        Log.d("TAG_GenerateQrCode", QrCodeKey);

        //Insert Qr Code into Database
        String urlParameters_insert_barcode ="idternak=" + generateId.trim()
                +"&idpeternakan="+pId.trim()
                +"&qrcodekey="+QrCodeKey.trim();
        new generateQrCodeId().execute("http://ternaku.com/index.php/C_Ternak/insertKeyQrCode", urlParameters_insert_barcode);
    }

    private void GenerateQrCodePic() {
        //Qr Code Pic Generator--
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(QrCodeKey, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            Intent intent = new Intent(InsertTernakActivity.this, InsertSuccessTernakActivity.class);
            intent.putExtra("picQrCode",bitmap);
            startActivity(intent);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private class generateQrCodeId extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... set) {
            Connection c = new Connection();
            String json = c.GetJSONfromURL(set[0],set[1]);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("RES_QrCodeId",s);
            GenerateQrCodePic();
        }
    }

    private class generateTernakTask extends AsyncTask<String,Integer,String>{
        ProgressDialog progD;

        @Override
        protected void onPreExecute() {
            progD = new ProgressDialog(InsertTernakActivity.this);
            progD.setMessage("Generate Qr Code...");
            progD.show();
        }

        @Override
        protected String doInBackground(String... get) {
            Connection c = new Connection();
            String json = c.GetJSONfromURL(get[0],get[1]);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("RES_Count",s);
            generateId = "TRK" + s.toString();
            insertDB();
            progD.dismiss();
        }
    }

    private class insertTernakTask extends AsyncTask<String,Integer,String>{
        ProgressDialog progD;

        @Override
        protected void onPreExecute() {

            progD = new ProgressDialog(InsertTernakActivity.this);
            progD.setMessage("Generate Qr Code...");
            progD.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Connection c = new Connection();
            String json = c.GetJSONfromURL(params[0],params[1]);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("RES_Insert",result);
            GenerateQrCodeId();
            progD.dismiss();
        }
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


        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        txtNama.setText(editText.getText());
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
                counter+=1;
                txtBerat.setGravity(Gravity.CENTER);
                txtBerat.setText(String.valueOf(df.format(counter)));
            }
        });

        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter-=1;
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
                        txtBrt.setText(txtBerat.getText());
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
                txtTgl.setText(dateFormatter.format(newDate.getTime()));
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
