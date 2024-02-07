package com.ramanora.plastfocus.PlastFocus_Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.ramanora.plastfocus.R;

import java.io.IOException;

public class ScannerQrCode extends Activity {

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    public BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();

    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
   // Button btnAction;
    String intentData = "";
    boolean isEmail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_barcode);
        initViews();
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
      //  btnAction = findViewById(R.id.btnAction);
//        btnAction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (intentData.length() > 0) {
//                    if (isEmail)
//                        startActivity(new Intent(ScannedBarcodeActivity.this, EmailActivity.class).putExtra("email_address", intentData));
//                    else {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
//                    }
//                }
//            }
//        });
    }

    private void initialiseDetectorsAndSources() {

       // Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)//you should add this feature
//                .setFacing(10)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannerQrCode.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannerQrCode.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
               // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).email.address;
                                Log.e("intentData","intentData "+intentData);
                                txtBarcodeValue.setText(intentData);
                                isEmail = true;
                                // btnAction.setText("ADD CONTENT TO THE MAIL");
                            } else {
                                isEmail = false;
                              //  btnAction.setText("LAUNCH URL");
                                intentData = barcodes.valueAt(0).displayValue;
                               // txtBarcodeValue.setText(intentData);
                                Log.e("intentData","intentData1 "+intentData);
                                // txtBarcodeValue.setText(intentData);
                                Intent intent = new Intent();
                                intent.putExtra("intentData",intentData);
                                setResult(12,intent);
                                finish();

                               /* Intent intent = new Intent(ScannedBarcodeActivity.this,EmailActivity.class);
                                intent.putExtra("intentData",intentData);
                                startActivity(intent);*/

                              /*  btnAction.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(ScannedBarcodeActivity.this,EmailActivity.class);
                                        intent.putExtra("intentData",intentData);
                                        startActivity(intent);
                                    }
                                });*/

                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }


}
