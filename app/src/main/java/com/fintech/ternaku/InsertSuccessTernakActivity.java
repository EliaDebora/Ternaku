package com.fintech.ternaku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class InsertSuccessTernakActivity extends AppCompatActivity {

    private ImageView qrCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_success_ternak);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInsertSuccess);
        setSupportActionBar(toolbar);

        qrCodeImage = (ImageView) this.findViewById(R.id.viewQrCode);
        Bitmap bitmap = getIntent().getParcelableExtra("picQrCode");
        qrCodeImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 600, 600, false));
    }
}
