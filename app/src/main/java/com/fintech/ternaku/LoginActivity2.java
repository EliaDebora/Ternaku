package com.fintech.ternaku;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity2 extends AppCompatActivity {
    private EditText edtUsername,edtPass;
    private Button btnLogin;
    private int attempt = 0;

    private SharedPreferences shr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shr = getSharedPreferences(getString(R.string.userpref), Context.MODE_PRIVATE);
        if(!shr.contains("keyUsername") || shr.getString("keyUsername",null)==null) {
            setContentView(R.layout.activity_login2);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            edtUsername = (EditText) findViewById(R.id.username);
            edtPass = (EditText) findViewById(R.id.password);
            btnLogin = (Button) findViewById(R.id.btnLogin);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*Intent i = new Intent(LoginActivity2.this,MainActivity.class);
                startActivity(i);
                finish();*/
                    try {
                        String password = get_SHA_512_SecurePassword(edtPass.getText().toString(),edtUsername.getText().toString());
                        String urlParameters = "username=" + URLEncoder.encode(edtUsername.getText().toString(), "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
                        new LoginTask().execute("http://ternaku.com/index.php/C_Pengguna/cekLogin", urlParameters);
                        Log.d("HASH",get_SHA_512_SecurePassword(edtPass.getText().toString(),edtUsername.getText().toString()));

                    } catch (UnsupportedEncodingException u) {
                        u.printStackTrace();
                    }
                }
            });
        }
        else{
            Intent i = new Intent(LoginActivity2.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
    private class LoginTask extends AsyncTask<String, Integer, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(LoginActivity2.this);
            pDialog.setMessage("Harap tunggu...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            Connection c = new Connection();
            String json = c.GetJSONfromURL(urls[0],urls[1]);
            return json;
        }

        protected void onPostExecute(String result) {
            Log.d("RES",result);
            if (result.trim().equals("1")) {
                if(attempt < 3)
                {
                    Toast.makeText(getApplication(),"Password yang anda masukkan salah",Toast.LENGTH_LONG).show();
                    attempt++;
                    pDialog.dismiss();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity2.this);
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.setTitle("Peringatan");
                    dialog.setMessage("Apakah anda ingin menggunakan fitur lupa password?");
                    pDialog.dismiss();
                    dialog.show();
                    attempt = 0;
                }
            }
            else if(result.trim().equals("2")){
                Toast.makeText(getApplication(),"Username dan password belum terdaftar",Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
            else{
                Intent i = new Intent(LoginActivity2.this,MainActivity.class);
                startActivity(i);
                savetoLocal(result);
                pDialog.dismiss();
                finish();
            }
        }

    }

    private void savetoLocal(String user)
    {
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(getString(R.string.userpref), Context.MODE_PRIVATE);
        try {
            JSONArray jArray = new JSONArray(user);
            JSONObject jObj = jArray.getJSONObject(0);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("keyNama",jObj.get("NAMA").toString());
            editor.putString("keyRole",jObj.get("ID_ROLE").toString());
            editor.putString("keyNamaRole",jObj.get("NAMA_ROLE").toString());
            editor.putString("keyIdPengguna",jObj.get("ID_PENGGUNA").toString());
            editor.putString("keyAlamat",jObj.get("ALAMAT").toString());
            editor.putString("keyTelpon",jObj.get("TELPON").toString());
            editor.putString("keyUsername",jObj.get("USERNAME").toString());
            editor.apply();
        }
        catch (JSONException e){e.printStackTrace();}
    }

    public String get_SHA_512_SecurePassword(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
