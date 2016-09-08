package com.fintech.ternaku;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUsername,edtPass,edtTelpon,edtAlamat,edtNamaLengkap;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNamaLengkap = (EditText)findViewById(R.id.nama_panjang_register);
        edtUsername = (EditText)findViewById(R.id.username_register);
        edtAlamat = (EditText)findViewById(R.id.alamat_pengguna);
        edtTelpon = (EditText)findViewById(R.id.nomor_telepon_pengguna);
        edtPass = (EditText)findViewById(R.id.password_register);

        btnRegister = (Button)findViewById(R.id.btnRegister) ;

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG",edtUsername.getText().toString()+edtAlamat.getText().toString()+
                        edtTelpon.getText().toString()+edtNamaLengkap.getText().toString());
                String urlParameters = "namalengkap=" + edtNamaLengkap.getText().toString()
                        +"&alamat=" + edtAlamat.getText().toString()
                        +"&telepon=" + edtTelpon.getText().toString()
                        +"&username=" + edtUsername.getText().toString()
                        +"&password=" + edtPass.getText().toString();
                new RegisterTask().execute("http://ternaku.com/index.php/C_Pengguna/insertRegister", urlParameters);
            }
        });

    }

    private class RegisterTask extends AsyncTask<String,Integer,String>{
        ProgressDialog progDialog;

        @Override
        protected void onPreExecute() {
            progDialog = new ProgressDialog(RegisterActivity.this);
            progDialog.setMessage("Tunggu Sebentar...");
            progDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Connection c = new Connection();
            String json = c.GetJSONfromURL(params[0],params[1]);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("RES",result);
            if (result.trim().equals("1")){
                Toast.makeText(getApplication(),"Username Telah Digunakan!!!",Toast.LENGTH_LONG).show();
                progDialog.dismiss();
            }
            else {
                Toast.makeText(getApplication(),"Selamat Anda Berhasil Mendaftar!!!",Toast.LENGTH_LONG).show();
                progDialog.dismiss();
            }
        }
    }
}
