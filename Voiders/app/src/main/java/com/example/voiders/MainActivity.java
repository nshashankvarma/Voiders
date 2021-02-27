package com.example.voiders;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    CardView cardView1, cardView2;
    ImageView img;

    int IMG_PICK_CODE=1000, PERMISSION_CODE=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView1 = (CardView)findViewById(R.id.cardview1);
        cardView2 = (CardView)findViewById(R.id.cardview2);
        img = (ImageView)findViewById(R.id.imageView);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        chooseImage();

                    }else{
                            String[] permission = { Manifest.permission.READ_EXTERNAL_STORAGE};
                            requestPermissions(permission, PERMISSION_CODE);
                    }
                }else{

                }
            }
        });
    }

    private void chooseImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMG_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            Bitmap bitmap =(Bitmap)data.getExtras().get("data");
            img.setVisibility(View.VISIBLE);
           img.setImageBitmap(bitmap);
            Intent intent = new Intent(this, VerifyPhoto.class);
            intent.putExtra("image", bitmap);
            startActivity(intent);
        }else if(requestCode == PERMISSION_CODE){
            Bitmap bitmap =(Bitmap)data.getExtras().get("data");
            //img.setVisibility(View.VISIBLE);
            //img.setImageBitmap(bitmap);
            Intent intent = new Intent(this, VerifyPhoto.class);
            intent.putExtra("image", bitmap);
            startActivity(intent);
        }

    }

}