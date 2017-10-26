package com.example.milenaasic.phonecalltest;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.Manifest.permission.CALL_PHONE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int REQUEST_CALL_PHONE = 100;
    private View linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(this);

        linear=findViewById(R.id.mylayout);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:{
                Intent intentToCall=new Intent(Intent.ACTION_CALL);
                String telefon="tel:0113108888";
                intentToCall.setData(Uri.parse(telefon));
                if (mayRequestCallPhonePermission()) {
                    startActivity(intentToCall);
                    new Thread (new MyThread()).start();
                }else mayRequestCallPhonePermission();
                return;

            }
            default: return;
        }
    }




    private boolean mayRequestCallPhonePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(CALL_PHONE)) {
            Snackbar.make(linear, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{CALL_PHONE}, REQUEST_CALL_PHONE);
                        }
                    });
        } else {
            requestPermissions(new String[]{CALL_PHONE}, REQUEST_CALL_PHONE);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToCall=new Intent(Intent.ACTION_CALL);
                String telefon="tel:0113108888";
                intentToCall.setData(Uri.parse(telefon));
                startActivity(intentToCall);
                new Thread (new MyThread()).start();

            }
        }
    }


    class MyThread implements Runnable{

        @Override
        public void run() {

            try{
                Thread.sleep(5000);
                Log.v("Thread", "woke up");
                secondCall();
                Log.v("Thread", "made call");
            }catch(Exception e) {
                e.printStackTrace();
                Log.v("Thread", "exception");
            }



        }
    }

    private void secondCall() {
        Intent intentToCall=new Intent(Intent.ACTION_CALL);
        String telefon="tel:1";
        intentToCall.setData(Uri.parse(telefon));
        Log.v("second call", "sent");
        startActivity(intentToCall);

    }


}
