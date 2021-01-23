package com.example.hashcodedemo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class AlertActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    MediaRecorder recorder;
    String fileName = null;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    ProgressDialog progressBar;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecording();
            } else {
                //User denied Permission.
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        button = (Button) findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.textView);

        progressBar = new ProgressDialog(this);

        fileName = getExternalFilesDir(null).getAbsolutePath();
        fileName += "/recorded_audio.3gp";



        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("flag");

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    startRecording();

                    textView.setText("Started");

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    stopRecording();
                    textView.setText("Stopped");
                }
                return false;
            }
        });
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e("record_log", "prepare() failed");
            System.out.println("" + e);
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        uploadRecording();
    }

    public void uploadRecording(){
        //progressBar.setMessage("Uploading");
        //progressBar.show();
        StorageReference filepath = storageReference.child("Audio").child("new_audio.3gp");

        Uri uri = Uri.fromFile(new File(fileName));

        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                databaseReference.setValue("true");
                Toast.makeText(AlertActivity.this, "Voice Mail Sent!!", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
