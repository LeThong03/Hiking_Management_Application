package com.example.m_hiking6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {
    EditText nameEdt, locationEdt, dateEdt, lengthEdt, parkingEdt, descriptionEdt;
    Spinner difficultySpinner;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameEdt = findViewById(R.id.nameEdt);
        locationEdt = findViewById(R.id.locationEdt);
        dateEdt = findViewById(R.id.dateEdt);
        lengthEdt = findViewById(R.id.lengthEdt);
        parkingEdt = findViewById(R.id.parkingEdt);
        descriptionEdt = findViewById(R.id.descriptionEdt);
        buttonSave = findViewById(R.id.buttonSave);

        // Populate the spinner with difficulty options (you can add more)
        difficultySpinner = findViewById(R.id.difficultySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addTrip(nameEdt.getText().toString().trim(),
                        locationEdt.getText().toString().trim(),
                        dateEdt.getText().toString().trim(),
                        parkingEdt.getText().toString().trim(),
                        lengthEdt.getText().toString().trim(),
                        difficultySpinner.getSelectedItem().toString().trim(),
                        descriptionEdt.getText().toString().trim()
                );
            }
        });

    }
}