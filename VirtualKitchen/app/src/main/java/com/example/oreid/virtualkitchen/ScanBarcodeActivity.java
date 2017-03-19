package com.example.oreid.virtualkitchen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

/**
 * ScanBarcodeActivity
 *
 * For adding items by scanning barcodes
 * At the moment only a static image is available.
 */

public class ScanBarcodeActivity extends AppCompatActivity {

    private Button scanButton;
    private TextView textMsg;
    private ImageView barcodeImageview;
    private BarcodeDetector barcodeDetector;

    private boolean error = false;
    private Bitmap barcodeBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);

        initialiseComponents();
    }

    private void initialiseComponents() {
        scanButton = (Button) findViewById(R.id.scanButton);
        addScanListener(scanButton);
        barcodeImageview = (ImageView) findViewById(R.id.barcodeImageview);
        setupImage(barcodeImageview);
        textMsg = (TextView) findViewById(R.id.barcodeMsg);

        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.EAN_13)
                        .build();
        if(!barcodeDetector.isOperational()){
            textMsg.setText("Could not set up the detector!");
            error = true;
        }

    }

    private void addScanListener(Button scan) {
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (error) {
                    Toast.makeText(ScanBarcodeActivity.this, "Sorry, the barcode scanner is not operational, please try restarting the app.", Toast.LENGTH_SHORT);
                }
                String code = detectBarcode();
                if (code != null) {
                    Intent returnIntent = new Intent(ScanBarcodeActivity.this, AddItem.class);
                    returnIntent.putExtra("BARCODE", code);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });

    }

    private String detectBarcode() {
        Frame frame = new Frame.Builder().setBitmap(barcodeBitmap).build();
        SparseArray<Barcode> barcodes = barcodeDetector.detect(frame);

        if (barcodes.size() == 1) {
            Barcode theCode = barcodes.valueAt(0); // TODO account for 0 or more than 1 codes (error)
            return theCode.rawValue;
        } else {
            textMsg.setText("Error, " + barcodes.size() + " barcodes detected.");
            return null;
        }



    }

    private void setupImage(ImageView v) {
        barcodeBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.sample_barcode);
        barcodeImageview.setImageBitmap(barcodeBitmap);

    }

}
