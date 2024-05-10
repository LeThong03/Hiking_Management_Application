package com.example.m_hiking6;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText nameEdt, locationEdt, dateEdt, parkingEdt, lengthEdt, difficultEdt, descriptionEdt;
    Button buttonUpdate, buttonDelete;
    String id, name, location, date, parking, length, difficult, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        nameEdt = findViewById(R.id.nameEdt2);
        locationEdt = findViewById(R.id.locationEdt2);
        dateEdt = findViewById(R.id.dateEdt2);
        parkingEdt = findViewById(R.id.parkingEdt2);
        lengthEdt = findViewById(R.id.lengthEdt2);
        difficultEdt = findViewById(R.id.difficultEdt2);
        descriptionEdt = findViewById(R.id.descriptionEdt2);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                name = nameEdt.getText().toString().trim();
                location = locationEdt.getText().toString().trim();
                date = dateEdt.getText().toString().trim();
                parking = parkingEdt.getText().toString().trim();
                length = lengthEdt.getText().toString().trim();
                difficult = difficultEdt.getText().toString().trim();
                description = descriptionEdt.getText().toString().trim();

                myDB.updateData(id,name,location,date,parking,length,difficult,description);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogConfirm();
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") ){
            //Get data from intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            location = getIntent().getStringExtra("location");
            date = getIntent().getStringExtra("date");
            parking = getIntent().getStringExtra("parking");
            length = getIntent().getStringExtra("length");
            difficult = getIntent().getStringExtra("difficult");
            description = getIntent().getStringExtra("description");

            //Setting intent data
            nameEdt.setText(name);
            locationEdt.setText(location);
            dateEdt.setText(date);
            parkingEdt.setText(parking);
            lengthEdt.setText(length);
            difficultEdt.setText(difficult);
            descriptionEdt.setText(description);

        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void dialogConfirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete" + name + "?");
        builder.setMessage("Delete this" + name + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}