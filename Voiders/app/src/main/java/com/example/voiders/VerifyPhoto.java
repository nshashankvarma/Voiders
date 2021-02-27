package com.example.voiders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class VerifyPhoto extends AppCompatActivity {

    Button cancelButton, verifyButton;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_photo);

        cancelButton = (Button)findViewById(R.id.cancelButton);
        verifyButton = (Button)findViewById(R.id.verifyButton);
        img = (ImageView)findViewById(R.id.imageView4);

        Intent intent = getIntent();
        final Bitmap bitmap = intent.getParcelableExtra("image");
        img.setImageBitmap(bitmap);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerifyPhoto.this, MainActivity.class);
                startActivity(i);
            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerifyPhoto.this, CopyTextActivity.class);
                i.putExtra("image",bitmap);
                startActivity(i);
            }
        });
    }
}