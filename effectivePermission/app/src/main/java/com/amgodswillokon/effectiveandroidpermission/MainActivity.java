package com.amgodswillokon.effectiveandroidpermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

/*
This simple app demonstrate how to effectively handle a permission gracefully
by Godswill Okon

 */

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIfCameraPermissionIsGranted();
        }

    //this method is only called if the user had earlier click on "Deny" permission once
    //This will present the user with the dialogue explaining why the permission is needed
    public void cameraPermissionAsk(){
        builder.setTitle("Camera notice");
        builder.setMessage("Allow camera permission to enable take quality photos");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                 Manifest.permission.CAMERA},105);
                        }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Go to setting and turn on permission, thanks",
                Toast.LENGTH_LONG).show();
                dialogInterface.cancel();
            }
        });
        builder.show();
 }

 //once onResume is fired, this method will be called to check the status of permission in the application.
  //will also check if the user had earlier click on grant permission later
    public void checkIfCameraPermissionIsGranted() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)){
                cameraPermissionAsk();
            }else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                        Manifest.permission.CAMERA},105);
            }
        }else{
    Toast.makeText(getApplicationContext(), "Permission granted: Enjoy the feature", Toast.LENGTH_LONG).show();
        }
    }

    //this API method will be notified if the user had earlier click on "never show again" permission again
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       if (requestCode == 105){

        if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
          Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_LONG).show();
        } else{
        Toast.makeText(getApplicationContext(), "To use this feature, go to Setting and permits Camera", Toast.LENGTH_LONG).show();
                }
            }
        }
}