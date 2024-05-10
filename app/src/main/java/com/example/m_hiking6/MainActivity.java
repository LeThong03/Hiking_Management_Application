package com.example.m_hiking6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    MyDatabaseHelper myDB;
    ArrayList<String> trip_id, trip_name, trip_location, trip_date, trip_parking, trip_length, trip_difficult, trip_description;
    AdapterTrip adapterTrip;
    ImageView empty_imageview;
    TextView no_data;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        recyclerView = findViewById(R.id.recycleView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        trip_id = new ArrayList<>();
        trip_name = new ArrayList<>();
        trip_location = new ArrayList<>();
        trip_date = new ArrayList<>();
        trip_parking = new ArrayList<>();
        trip_length = new ArrayList<>();
        trip_difficult = new ArrayList<>();
        trip_description = new ArrayList<>();

        storeDataInArrays();

        adapterTrip = new AdapterTrip(MainActivity.this,this, trip_id, trip_name, trip_location, trip_date,
                trip_parking, trip_length, trip_difficult, trip_description);
        recyclerView.setAdapter(adapterTrip);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                trip_id.add(cursor.getString(0));
                trip_name.add(cursor.getString(1 ));
                trip_location.add(cursor.getString(2));
                trip_date.add(cursor.getString(3));
                trip_parking.add(cursor.getString(4));
                trip_length.add(cursor.getString(5));
                trip_difficult.add(cursor.getString(6));
                trip_description.add(cursor.getString(7));

            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            dialogConfirm();
        }
        return super.onOptionsItemSelected(item);
    }

    void dialogConfirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Delete this?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                //refresh
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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

    private void filterList(String searchText) {
        ArrayList<String> filteredTripId = new ArrayList<>();
        ArrayList<String> filteredTripName = new ArrayList<>();
        ArrayList<String> filteredTripLocation = new ArrayList<>();
        ArrayList<String> filteredTripDate = new ArrayList<>();
        ArrayList<String> filteredTripParking = new ArrayList<>();
        ArrayList<String> filteredTripLength = new ArrayList<>();
        ArrayList<String> filteredTripDifficult = new ArrayList<>();
        ArrayList<String> filteredTripDescription = new ArrayList<>();

        // Iterate through the original data and add matching items to filtered lists
        for (int i = 0; i < trip_name.size(); i++) {
            if (trip_name.get(i).toLowerCase().contains(searchText.toLowerCase())) {
                filteredTripId.add(trip_id.get(i));
                filteredTripName.add(trip_name.get(i));
                filteredTripLocation.add(trip_location.get(i));
                filteredTripDate.add(trip_date.get(i));
                filteredTripParking.add(trip_parking.get(i));
                filteredTripLength.add(trip_length.get(i));
                filteredTripDifficult.add(trip_difficult.get(i));
                filteredTripDescription.add(trip_description.get(i));
            }
        }

        // Create a new adapter with the filtered data and set it to the RecyclerView
        adapterTrip = new AdapterTrip(MainActivity.this, this, filteredTripId, filteredTripName, filteredTripLocation, filteredTripDate,
                filteredTripParking, filteredTripLength, filteredTripDifficult, filteredTripDescription);
        recyclerView.setAdapter(adapterTrip);
    }

}