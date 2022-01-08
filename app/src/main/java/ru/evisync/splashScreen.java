package ru.evisync;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        ActivityCompat.requestPermissions(splashScreen.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(splashScreen.this, syncData.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Нету доступа к внешнему хранилищу, закрываюсь...", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            }
        }
    }
}