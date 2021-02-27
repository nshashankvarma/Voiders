package com.example.voiders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class CopyTextActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText copyEditText;
    String text, lang;
    Button translateButton, openAppButton;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_text);

        copyEditText = (EditText)findViewById(R.id.copyEditText);
        translateButton = (Button)findViewById(R.id.translateButton);
        openAppButton = (Button)findViewById(R.id.openAppButton);
        spinner =(Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(CopyTextActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.languages));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) CopyTextActivity.this) ;


        Intent intent = getIntent();
        Bitmap bitmap = intent.getParcelableExtra("image");

        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

//        Python py = Python.getInstance();
//        PyObject pyobj = py.getModule("first");
//
//        PyObject obj=null;
//        obj = pyobj.callAttr("detect", R.drawable.p);//function , //parameters)
//        Log.i("Text", String.valueOf(obj));

        //copyEditText.setText(String.valueOf(obj));

        text = copyEditText.getText().toString();

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CopyTextActivity.this, TranslateActivity.class);
                i.putExtra("text", text);
                i.putExtra("lang", lang);
                startActivity(i);
            }
        });

        openAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"youremail@yahoo.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "subject");
                email.putExtra(Intent.EXTRA_TEXT, "message");
                email.setType("text/plain");
                startActivity(Intent.createChooser(email, "Open in "));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        lang = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}