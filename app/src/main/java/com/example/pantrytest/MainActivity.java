package com.example.pantrytest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    EditText eItem, eSize, eDate;

    Button btn;
    DatePickerDialog datePickerDialog;
    RadioButton rbtn1,rbtn2;

    DatabaseReference myRef;
    Model model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additems);

        eItem = (EditText)findViewById(R.id.editTextTextName);
        eSize = (EditText)findViewById(R.id.editTextTextPersonName2);
        eDate = (EditText)findViewById(R.id.editTextDate);
        rbtn1 = (RadioButton)findViewById(R.id.radioButton2);
        rbtn2 = (RadioButton)findViewById(R.id.radioButton3);


        btn = (Button)findViewById(R.id.button2);



        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        eDate.setText(year + "/" + (month+1) + "/"+ day);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        model = new Model();
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myRef = FirebaseDatabase.getInstance().getReference().child("Model");

                try {
                    if (TextUtils.isEmpty(eItem.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter an item", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(eSize.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter size of the item", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(eDate.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter expire date", Toast.LENGTH_SHORT).show();

                    else {


                        String notificationTime = "";
                        model.setItem(eItem.getText().toString().trim());
                        model.setSize(eSize.getText().toString().trim());
                       model.setDate(eDate.getText().toString().trim());




                        if (rbtn1.isChecked()) {
                            notificationTime = "before 2 weeks";
                            model.setNotificationTime(rbtn1.getText().toString().trim());
                        }
                        if (rbtn2.isChecked()) {
                            notificationTime = "before month";
                            model.setNotificationTime(rbtn2.getText().toString().trim());
                        }

                        myRef.push().setValue(model);
                        Toast.makeText(getApplicationContext(), "data enter succesfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, DisplayPantry.class);
                        startActivity(intent);

                    }












                }catch (NumberFormatException e ) {
                    Toast.makeText(getApplicationContext(), "Invalid contact numbers", Toast.LENGTH_SHORT).show();
                }

                }


        });
    }
}